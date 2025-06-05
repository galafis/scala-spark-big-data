package com.galafis.bigdata.utils

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.storage.StorageLevel
import org.slf4j.{Logger, LoggerFactory}

import scala.util.{Failure, Success, Try}

/**
 * Utility functions for Spark operations
 */
object SparkUtils {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  
  /**
   * Reads data from a source
   * 
   * @param spark SparkSession
   * @param path Path to read from
   * @param format Format of the data (e.g., "parquet", "csv", "json")
   * @param options Options for reading
   * @return DataFrame with the read data
   */
  def readData(
    spark: SparkSession,
    path: String,
    format: String,
    options: Map[String, String] = Map.empty
  ): DataFrame = {
    logger.info(s"Reading $format data from $path")
    
    val reader = spark.read.format(format)
    
    // Apply options if provided
    if (options.nonEmpty) {
      reader.options(options)
    }
    
    // Handle specific formats
    format.toLowerCase match {
      case "csv" =>
        reader
          .option("header", options.getOrElse("header", "true"))
          .option("inferSchema", options.getOrElse("inferSchema", "true"))
      case "json" =>
        reader
          .option("multiLine", options.getOrElse("multiLine", "false"))
      case _ => reader
    }
    
    // Read the data
    val df = reader.load(path)
    logger.info(s"Successfully read data with schema: ${df.schema.treeString}")
    df
  }
  
  /**
   * Writes data to a destination
   * 
   * @param df DataFrame to write
   * @param path Path to write to
   * @param format Format to write as (e.g., "parquet", "csv", "json")
   * @param mode Write mode (e.g., "overwrite", "append")
   * @param partitionBy Columns to partition by
   * @param options Options for writing
   */
  def writeData(
    df: DataFrame,
    path: String,
    format: String,
    mode: String = "overwrite",
    partitionBy: Seq[String] = Seq.empty,
    options: Map[String, String] = Map.empty
  ): Unit = {
    logger.info(s"Writing data to $path in $format format with mode $mode")
    
    val writer = df.write
      .format(format)
      .mode(mode)
    
    // Apply options if provided
    if (options.nonEmpty) {
      writer.options(options)
    }
    
    // Apply partitioning if specified
    if (partitionBy.nonEmpty) {
      writer.partitionBy(partitionBy: _*)
    }
    
    // Write the data
    writer.save(path)
    logger.info(s"Successfully wrote data to $path")
  }
  
  /**
   * Caches a DataFrame with the specified storage level
   * 
   * @param df DataFrame to cache
   * @param storageLevel Storage level to use
   * @return Cached DataFrame
   */
  def cacheDataFrame(
    df: DataFrame,
    storageLevel: StorageLevel = StorageLevel.MEMORY_AND_DISK
  ): DataFrame = {
    logger.info(s"Caching DataFrame with storage level $storageLevel")
    df.persist(storageLevel)
  }
  
  /**
   * Executes an operation with retry logic
   * 
   * @param operation Operation to execute
   * @param maxRetries Maximum number of retries
   * @param retryInterval Interval between retries in milliseconds
   * @tparam T Return type of the operation
   * @return Result of the operation
   */
  def withRetry[T](
    operation: => T,
    maxRetries: Int = 3,
    retryInterval: Long = 1000
  ): T = {
    var attempts = 0
    var result: Option[T] = None
    var lastException: Throwable = null
    
    while (attempts < maxRetries && result.isEmpty) {
      attempts += 1
      try {
        result = Some(operation)
      } catch {
        case e: Throwable =>
          lastException = e
          logger.warn(s"Attempt $attempts failed with error: ${e.getMessage}")
          if (attempts < maxRetries) {
            logger.info(s"Retrying in $retryInterval ms...")
            Thread.sleep(retryInterval)
          }
      }
    }
    
    result.getOrElse(throw new RuntimeException(s"Operation failed after $maxRetries attempts", lastException))
  }
  
  /**
   * Validates a DataFrame against a schema
   * 
   * @param df DataFrame to validate
   * @param schema Expected schema
   * @return Validation result
   */
  def validateSchema(df: DataFrame, schema: StructType): Boolean = {
    val currentSchema = df.schema
    val missingFields = schema.fields.filterNot(f => currentSchema.fieldNames.contains(f.name))
    val typeMismatches = schema.fields
      .filter(f => currentSchema.fieldNames.contains(f.name))
      .filter(f => {
        val currentField = currentSchema(f.name)
        currentField.dataType != f.dataType
      })
    
    if (missingFields.nonEmpty) {
      logger.error(s"Missing fields: ${missingFields.map(_.name).mkString(", ")}")
    }
    
    if (typeMismatches.nonEmpty) {
      logger.error(s"Type mismatches: ${typeMismatches.map(f => s"${f.name}: expected ${f.dataType}, got ${currentSchema(f.name).dataType}").mkString(", ")}")
    }
    
    missingFields.isEmpty && typeMismatches.isEmpty
  }
  
  /**
   * Adds audit columns to a DataFrame
   * 
   * @param df DataFrame to add audit columns to
   * @return DataFrame with audit columns
   */
  def addAuditColumns(df: DataFrame): DataFrame = {
    df.withColumn("processing_time", current_timestamp())
      .withColumn("processing_date", current_date())
      .withColumn("batch_id", lit(java.util.UUID.randomUUID().toString))
  }
}

