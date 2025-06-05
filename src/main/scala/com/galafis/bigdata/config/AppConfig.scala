package com.galafis.bigdata.config

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession

/**
 * Application configuration loader
 */
object AppConfig {
  // Load configuration from application.conf
  private val config: Config = ConfigFactory.load()

  // Spark configuration
  object SparkConfig {
    private val sparkConfig = config.getConfig("spark")
    
    val appName: String = sparkConfig.getString("app-name")
    val master: String = sparkConfig.getString("master")
    val logLevel: String = sparkConfig.getString("log-level")
    val enableHive: Boolean = sparkConfig.getBoolean("enable-hive")
    val warehouseDir: String = sparkConfig.getString("warehouse-dir")
    val checkpointDir: String = sparkConfig.getString("checkpoint-dir")
    val shufflePartitions: Int = sparkConfig.getInt("shuffle-partitions")
    val defaultParallelism: Int = sparkConfig.getInt("default-parallelism")
    
    // AWS S3 configuration if available
    val awsAccessKey: Option[String] = if (sparkConfig.hasPath("aws.access-key")) Some(sparkConfig.getString("aws.access-key")) else None
    val awsSecretKey: Option[String] = if (sparkConfig.hasPath("aws.secret-key")) Some(sparkConfig.getString("aws.secret-key")) else None
    val s3Endpoint: Option[String] = if (sparkConfig.hasPath("aws.s3-endpoint")) Some(sparkConfig.getString("aws.s3-endpoint")) else None
  }
  
  // Data sources configuration
  object DataConfig {
    private val dataConfig = config.getConfig("data")
    
    val inputPath: String = dataConfig.getString("input-path")
    val outputPath: String = dataConfig.getString("output-path")
    val tempPath: String = dataConfig.getString("temp-path")
    val format: String = dataConfig.getString("format")
    val partitionColumns: List[String] = dataConfig.getStringList("partition-columns").toArray.toList.map(_.toString)
  }
  
  // Application parameters
  object AppParams {
    private val appConfig = config.getConfig("app")
    
    val batchSize: Int = appConfig.getInt("batch-size")
    val processingMode: String = appConfig.getString("processing-mode")
    val enableCaching: Boolean = appConfig.getBoolean("enable-caching")
    val numRetries: Int = appConfig.getInt("num-retries")
    val retryInterval: Int = appConfig.getInt("retry-interval")
  }
  
  /**
   * Creates a SparkSession based on configuration
   * @return Configured SparkSession
   */
  def createSparkSession(): SparkSession = {
    val builder = SparkSession.builder()
      .appName(SparkConfig.appName)
      .master(SparkConfig.master)
      .config("spark.sql.shuffle.partitions", SparkConfig.shufflePartitions)
      .config("spark.default.parallelism", SparkConfig.defaultParallelism)
      .config("spark.sql.warehouse.dir", SparkConfig.warehouseDir)
      .config("spark.checkpoint.dir", SparkConfig.checkpointDir)
    
    // Configure AWS credentials if available
    (SparkConfig.awsAccessKey, SparkConfig.awsSecretKey) match {
      case (Some(accessKey), Some(secretKey)) =>
        builder
          .config("spark.hadoop.fs.s3a.access.key", accessKey)
          .config("spark.hadoop.fs.s3a.secret.key", secretKey)
        
        // Set S3 endpoint if available
        SparkConfig.s3Endpoint.foreach { endpoint =>
          builder.config("spark.hadoop.fs.s3a.endpoint", endpoint)
        }
      case _ => // No AWS credentials provided
    }
    
    // Enable Hive support if configured
    val sessionBuilder = if (SparkConfig.enableHive) {
      builder.enableHiveSupport()
    } else {
      builder
    }
    
    val spark = sessionBuilder.getOrCreate()
    
    // Set log level
    spark.sparkContext.setLogLevel(SparkConfig.logLevel)
    
    spark
  }
}

