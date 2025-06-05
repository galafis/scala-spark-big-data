package com.galafis.bigdata.etl

import com.galafis.bigdata.config.AppConfig
import com.galafis.bigdata.models.DataModels._
import com.galafis.bigdata.utils.SparkUtils
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.slf4j.{Logger, LoggerFactory}

/**
 * Main data processor for ETL operations
 */
class DataProcessor(spark: SparkSession) {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  
  import spark.implicits._
  
  /**
   * Loads transaction data
   * 
   * @param path Path to transaction data
   * @return Dataset of transactions
   */
  def loadTransactions(path: String): Dataset[Transaction] = {
    logger.info(s"Loading transactions from $path")
    
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
    
    val df = SparkUtils.readData(
      spark,
      path,
      AppConfig.DataConfig.format,
      Map("schema" -> transactionSchema.json)
    )
    
    // Convert to Dataset
    df.select(
      col("id"),
      col("timestamp"),
      col("customer_id").as("customerId"),
      col("amount"),
      col("category"),
      col("merchant_id").as("merchantId"),
      col("location"),
      col("device_type").as("deviceType"),
      col("is_fraud").as("isFraud")
    ).as[Transaction]
  }
  
  /**
   * Loads customer data
   * 
   * @param path Path to customer data
   * @return Dataset of customers
   */
  def loadCustomers(path: String): Dataset[Customer] = {
    logger.info(s"Loading customers from $path")
    
    val customerSchema = StructType(Array(
      StructField("id", StringType, nullable = false),
      StructField("name", StringType, nullable = false),
      StructField("email", StringType, nullable = false),
      StructField("registration_date", TimestampType, nullable = false),
      StructField("segment", StringType, nullable = true),
      StructField("age", IntegerType, nullable = true),
      StructField("gender", StringType, nullable = true),
      StructField("location", StringType, nullable = true)
    ))
    
    val df = SparkUtils.readData(
      spark,
      path,
      AppConfig.DataConfig.format,
      Map("schema" -> customerSchema.json)
    )
    
    // Convert to Dataset
    df.select(
      col("id"),
      col("name"),
      col("email"),
      col("registration_date").as("registrationDate"),
      col("segment"),
      col("age"),
      col("gender"),
      col("location")
    ).as[Customer]
  }
  
  /**
   * Loads merchant data
   * 
   * @param path Path to merchant data
   * @return Dataset of merchants
   */
  def loadMerchants(path: String): Dataset[Merchant] = {
    logger.info(s"Loading merchants from $path")
    
    val merchantSchema = StructType(Array(
      StructField("id", StringType, nullable = false),
      StructField("name", StringType, nullable = false),
      StructField("category", StringType, nullable = false),
      StructField("location", StringType, nullable = true),
      StructField("rating", DoubleType, nullable = true)
    ))
    
    val df = SparkUtils.readData(
      spark,
      path,
      AppConfig.DataConfig.format,
      Map("schema" -> merchantSchema.json)
    )
    
    // Convert to Dataset
    df.as[Merchant]
  }
  
  /**
   * Processes transactions to generate summaries
   * 
   * @param transactions Dataset of transactions
   * @return Dataset of transaction summaries
   */
  def processTransactions(transactions: Dataset[Transaction]): Dataset[TransactionSummary] = {
    logger.info("Processing transactions to generate summaries")
    
    // Cache transactions if configured
    val cachedTransactions = if (AppConfig.AppParams.enableCaching) {
      SparkUtils.cacheDataFrame(transactions.toDF).as[Transaction]
    } else {
      transactions
    }
    
    // Generate transaction summaries
    val summaries = cachedTransactions
      .groupBy(col("customerId"))
      .agg(
        sum("amount").as("totalAmount"),
        count("*").as("transactionCount"),
        avg("amount").as("avgAmount"),
        max("amount").as("maxAmount"),
        min("amount").as("minAmount"),
        first(col("category")).as("topCategory"),
        sum(when(col("isFraud"), 1).otherwise(0)).as("fraudCount")
      )
      .as[TransactionSummary]
    
    logger.info(s"Generated ${summaries.count()} transaction summaries")
    summaries
  }
  
  /**
   * Detects potentially fraudulent transactions
   * 
   * @param transactions Dataset of transactions
   * @param customers Dataset of customers
   * @return Dataset of fraud detection results
   */
  def detectFraud(
    transactions: Dataset[Transaction],
    customers: Dataset[Customer]
  ): Dataset[FraudDetectionResult] = {
    logger.info("Detecting potentially fraudulent transactions")
    
    // Join transactions with customers
    val joinedData = transactions.join(
      customers,
      transactions("customerId") === customers("id"),
      "inner"
    )
    
    // Calculate risk score based on various factors
    val withRiskScore = joinedData.withColumn(
      "riskScore",
      // Example risk scoring logic
      when(col("amount") > 1000, 0.3).otherwise(0.0) +
      when(col("isFraud"), 0.5).otherwise(0.0) +
      when(col("age") < 25, 0.2).otherwise(0.0)
    )
    
    // Determine risk factors
    val withRiskFactors = withRiskScore.withColumn(
      "riskFactors",
      array_remove(
        array(
          when(col("amount") > 1000, lit("high_amount")).otherwise(null),
          when(col("isFraud"), lit("known_fraud")).otherwise(null),
          when(col("age") < 25, lit("young_customer")).otherwise(null)
        ),
        null
      )
    )
    
    // Calculate fraud probability
    val withFraudProbability = withRiskFactors.withColumn(
      "fraudProbability",
      col("riskScore") / 1.0 // Normalize to [0,1]
    )
    
    // Create fraud detection results
    withFraudProbability.select(
      col("id").as("transactionId"),
      col("customerId"),
      col("timestamp"),
      col("amount"),
      col("fraudProbability"),
      col("isFraud"),
      col("riskScore"),
      col("riskFactors")
    ).as[FraudDetectionResult]
  }
  
  /**
   * Saves data to the specified path
   * 
   * @param data Data to save
   * @param path Path to save to
   * @param partitionBy Columns to partition by
   */
  def saveData[T](
    data: Dataset[T],
    path: String,
    partitionBy: Seq[String] = Seq.empty
  ): Unit = {
    logger.info(s"Saving data to $path")
    
    SparkUtils.writeData(
      data.toDF,
      path,
      AppConfig.DataConfig.format,
      "overwrite",
      partitionBy
    )
    
    logger.info(s"Successfully saved data to $path")
  }
}

