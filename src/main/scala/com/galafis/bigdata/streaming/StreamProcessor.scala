package com.galafis.bigdata.streaming

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{OutputMode, Trigger, StreamingQuery}
import org.apache.spark.sql.types._
import org.slf4j.{Logger, LoggerFactory}
import java.util.concurrent.TimeUnit

/**
 * 🇺🇸 StreamProcessor: utilities to build, process and write Spark Structured Streaming pipelines
 * 🇧🇷 StreamProcessor: utilitários para construir, processar e gravar pipelines de Spark Structured Streaming
 */
class StreamProcessor(spark: SparkSession) {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  import spark.implicits._

  // ---- Sources | Fontes ----------------------------------------------------
  /**
   * 🇺🇸 Create a file source stream with an explicit schema (JSON/CSV/Parquet)
   * 🇧🇷 Cria um stream de arquivos com schema explícito (JSON/CSV/Parquet)
   */
  def createTransactionStream(path: String, format: String = "json"): DataFrame = {
    logger.info(s"Creating transaction stream from $path (format=$format)")

    val transactionSchema = StructType(Seq(
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
      .format(format)
      .schema(transactionSchema)
      .option("maxFilesPerTrigger", 1)
      .load(path)
  }

  // ---- Transformations | Transformações -----------------------------------
  /**
   * 🇺🇸 Basic enrichment and watermarking for late data handling
   * 🇧🇷 Enriquecimento básico e watermark para lidar com eventos atrasados
   */
  def processTransactionStream(transactions: DataFrame): DataFrame = {
    logger.info("Processing transaction stream")

    transactions
      .withWatermark("timestamp", "1 hour")
      .withColumn("processing_time", current_timestamp())
      .withColumn("year", year(col("timestamp")))
      .withColumn("month", month(col("timestamp")))
      .withColumn("day", dayofmonth(col("timestamp")))
      .withColumn("hour", hour(col("timestamp")))
  }

  /**
   * 🇺🇸 Window aggregations (tumbling 1 minute window) with watermark
   * 🇧🇷 Agregações em janelas (janela fixa de 1 minuto) com watermark
   */
  def aggregateTransactionsByWindow(transactions: DataFrame): DataFrame = {
    logger.info("Aggregating transactions by time window")

    transactions
      .withWatermark("timestamp", "10 minutes")
      .groupBy(window(col("timestamp"), "1 minute")).agg(
        count(lit(1)).as("transaction_count"),
        sum(col("amount")).as("total_amount"),
        avg(col("amount")).as("avg_amount"),
        sum(when(col("is_fraud"), 1).otherwise(0)).as("fraud_count")
      )
      .select(
        col("window.start").as("window_start"),
        col("window.end").as("window_end"),
        col("transaction_count"),
        col("total_amount"),
        col("avg_amount"),
        col("fraud_count")
      )
  }

  /**
   * 🇺🇸 Simple rule-based anomaly detection
   * 🇧🇷 Detecção de anomalias baseada em regras simples
   */
  def detectAnomalies(transactions: DataFrame,
                      highAmountThreshold: Double = 10000.0,
                      suspiciousLocations: Seq[String] = Seq("Unknown", "Anonymous Proxy")): DataFrame = {
    logger.info("Detecting anomalies in transaction stream")

    transactions
      .withColumn("is_high_amount", col("amount") > lit(highAmountThreshold))
      .withColumn("is_suspicious_location", col("location").isin(suspiciousLocations: _*))
      .withColumn("is_anomaly",
        col("is_high_amount") || col("is_suspicious_location") || col("is_fraud")
      )
      .filter(col("is_anomaly"))
  }

  // ---- Sinks | Destinos ----------------------------------------------------
  /**
   * 🇺🇸 Write processed transactions to files (append) partitioned by date hierarchy
   * 🇧🇷 Escreve transações processadas em arquivos (append) particionando por data
   */
  def startTransactionProcessingQuery(transactions: DataFrame,
                                      checkpointDir: String,
                                      outputPath: String,
                                      format: String = "parquet"): StreamingQuery = {
    val processed = processTransactionStream(transactions)

    processed.writeStream
      .format(format)
      .outputMode(OutputMode.Append)
      .option("checkpointLocation", s"$checkpointDir/transactions")
      .option("path", outputPath)
      .partitionBy("year", "month", "day", "hour")
      .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
      .start()
  }

  /**
   * 🇺🇸 Write detected anomalies to files (append)
   * 🇧🇷 Escreve anomalias detectadas em arquivos (append)
   */
  def startAnomalyDetectionQuery(transactions: DataFrame,
                                checkpointDir: String,
                                outputPath: String,
                                format: String = "parquet"): StreamingQuery = {
    val anomalies = detectAnomalies(transactions)

    anomalies.writeStream
      .format(format)
      .outputMode(OutputMode.Append)
      .option("checkpointLocation", s"$checkpointDir/anomalies")
      .option("path", outputPath)
      .trigger(Trigger.ProcessingTime(30, TimeUnit.SECONDS))
      .start()
  }

  /**
   * 🇺🇸 Write windowed aggregates to files (append)
   * 🇧🇷 Escreve agregações por janela em arquivos (append)
   */
  def startAggregationQuery(transactions: DataFrame,
                            checkpointDir: String,
                            outputPath: String,
                            format: String = "parquet"): StreamingQuery = {
    val aggregates = aggregateTransactionsByWindow(transactions)

    aggregates.writeStream
      .format(format)
      .outputMode(OutputMode.Append)
      .option("checkpointLocation", s"$checkpointDir/aggregates")
      .option("path", outputPath)
      .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
      .start()
  }
}
