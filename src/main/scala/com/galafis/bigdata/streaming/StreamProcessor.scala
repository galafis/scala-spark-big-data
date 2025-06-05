package com.galafis.bigdata.streaming

import com.galafis.bigdata.config.AppConfig
import com.galafis.bigdata.models.DataModels._
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.types._
import org.slf4j.{Logger, LoggerFactory}

import java.sql.Timestamp
import java.util.concurrent.TimeUnit

/**
 * Processor for streaming data
 */
class StreamProcessor(spark: SparkSession) {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  
  import spark.implicits._
  
  /**
   * Creates a streaming source for transactions
   * 
   * @param path Path to streaming source
   * @return Streaming DataFrame of transactions
   */
  def createTransactionStream(path: String): DataFrame = {
    logger.info(s"Creating transaction stream from $path")
    
    val transactionSchema = StructType(Array(
      StructField("id", StringType, nullable = false),
      StructField("timestamp", TimestampType, nullable = false),
      StructField("customer_id", StringType, nullable = false),
      StructField("amount", DoubleType, nullable = false),
      StructField("category", StringType, nullable = true),
      StructField("merchant_id", StringType, nullable = true),
      StructField("location", StringType, nullable = true),
      StructField("device_type", StringType, nullable = true),
      StructField("is_fraud", BooleanType, nullable = false)
    ))
    
    spark.readStream
      .schema(transactionSchema)
      .format(AppConfig.DataConfig.format)
      .option("maxFilesPerTrigger", 1)
      .load(path)
  }
  
  /**
   * Processes streaming transactions
   * 
   * @param transactions Streaming DataFrame of transactions
   * @return Processed streaming DataFrame
   */
  def processTransactionStream(transactions: DataFrame): DataFrame = {
    logger.info("Processing transaction stream")
    
    // Add watermark to handle late data
    val withWatermark = transactions
      .withWatermark("timestamp", "1 hour")
      .as("t")
    
    // Enrich with additional information
    val enriched = withWatermark
      .withColumn("processing_time", current_timestamp())
      .withColumn("year", year(col("timestamp")))
      .withColumn("month", month(col("timestamp")))
      .withColumn("day", dayofmonth(col("timestamp")))
      .withColumn("hour", hour(col("timestamp")))
    
    enriched
  }
  
  /**
   * Aggregates streaming transactions by time window
   * 
   * @param transactions Streaming DataFrame of transactions
   * @return Aggregated streaming DataFrame
   */
  def aggregateTransactionsByWindow(transactions: DataFrame): DataFrame = {
    logger.info("Aggregating transactions by time window")
    
    // Define windows
    val minuteWindow = window(col("timestamp"), "1 minute")
    val hourWindow = window(col("timestamp"), "1 hour")
    
    // Aggregate by minute
    val minuteAggregates = transactions
      .withWatermark("timestamp", "10 minutes")
      .groupBy(minuteWindow)
      .agg(
        count("*").as("transaction_count"),
        sum("amount").as("total_amount"),
        avg("amount").as("avg_amount"),
        sum(when(col("is_fraud"), 1).otherwise(0)).as("fraud_count")
      )
    
    minuteAggregates
  }
  
  /**
   * Detects anomalies in streaming transactions
   * 
   * @param transactions Streaming DataFrame of transactions
   * @return Streaming DataFrame with anomaly detection
   */
  def detectAnomalies(transactions: DataFrame): DataFrame = {
    logger.info("Detecting anomalies in transaction stream")
    
    // Define anomaly thresholds
    val highAmountThreshold = 10000.0
    val suspiciousLocations = Array("Unknown", "Anonymous Proxy")
    
    // Detect anomalies
    val anomalies = transactions
      .withColumn("is_high_amount", col("amount") > highAmountThreshold)
      .withColumn("is_suspicious_location", array_contains(lit(suspiciousLocations), col("location")))
      .withColumn("is_anomaly", 
        col("is_high_amount") || 
        col("is_suspicious_location") || 
        col("is_fraud")
      )
      .filter(col("is_anomaly"))
    
    anomalies
  }
  
  /**
   * Starts a streaming query to process transactions
   * 
   * @param transactions Streaming DataFrame of transactions
   * @param checkpointDir Directory for checkpointing
   * @param outputPath Path to write output
   * @return Streaming query
   */
  def startTransactionProcessingQuery(
    transactions: DataFrame,
    checkpointDir: String,
    outputPath: String
  ): StreamingQuery = {
    logger.info(s"Starting transaction processing query, writing to $outputPath")
    
    // Process transactions
    val processedTransactions = processTransactionStream(transactions)
    
    // Write stream
    processedTransactions
      .writeStream
      .format(AppConfig.DataConfig.format)
      .outputMode(OutputMode.Append)
      .option("checkpointLocation", s"$checkpointDir/transactions")
      .option("path", outputPath)
      .partitionBy("year", "month", "day", "hour")
      .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
      .start()
  }
  
  /**
   * Starts a streaming query to detect anomalies
   * 
   * @param transactions Streaming DataFrame of transactions
   * @param checkpointDir Directory for checkpointing
   * @param outputPath Path to write output
   * @return Streaming query
   */
  def startAnomalyDetectionQuery(
    transactions: DataFrame,
    checkpointDir: String,
    outputPath: String
  ): StreamingQuery = {
    logger.info(s"Starting anomaly detection query, writing to $outputPath")
    
    // Detect anomalies
    val anomalies = detectAnomalies(transactions)
    
    // Write stream
    anomalies
      .writeStream
      .format(AppConfig.DataConfig.format)
      .outputMode(OutputMode.Append)
      .option("checkpointLocation", s"$checkpointDir/anomalies")
      .option("path", outputPath)
      .trigger(Trigger.ProcessingTime(30, TimeUnit.SECONDS))
      .start()
  }
  
  /**
   * Starts a streaming query to aggregate transactions
   * 
   * @param transactions Streaming DataFrame of transactions
   * @param checkpointDir Directory for checkpointing
   * @param outputPath Path to write output
   * @return Streaming query
   */
  def startAggregationQuery(
    transactions: DataFrame,
    checkpointDir: String,
    outputPath: String
  ): StreamingQuery = {
    logger.info(s"Starting aggregation query, writing to $outputPath")
    
    // Aggregate transactions
    val aggregates = aggregateTransactionsByWindow(transactions)
    
    // Write stream
    aggregates
      .writeStream
      .format(AppConfig.DataConfig.format)
      .outputMode(OutputMode.Append)
      .option("checkpointLocation", s"$checkpointDir/aggregates")
      .option("path", outputPath)
      .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
      .start()
  }
}

