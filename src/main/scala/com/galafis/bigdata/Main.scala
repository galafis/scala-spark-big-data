package com.galafis.bigdata

import com.galafis.bigdata.config.AppConfig
import com.galafis.bigdata.etl.DataProcessor
import com.galafis.bigdata.analytics.AnalyticsEngine
import com.galafis.bigdata.streaming.StreamProcessor
import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

/**
 * Main entry point for the Scala Spark Big Data application
 */
object Main {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  
  def main(args: Array[String]): Unit = {
    logger.info("Starting Scala Spark Big Data application")
    
    // Parse command line arguments
    val mode = if (args.length > 0) args(0) else "batch"
    
    // Create Spark session
    val spark = AppConfig.createSparkSession()
    
    try {
      // Execute the appropriate mode
      mode match {
        case "batch" => executeBatchMode(spark)
        case "streaming" => executeStreamingMode(spark)
        case "analytics" => executeAnalyticsMode(spark)
        case _ => 
          logger.error(s"Unknown mode: $mode")
          println(s"Unknown mode: $mode. Valid modes are: batch, streaming, analytics")
          sys.exit(1)
      }
    } catch {
      case e: Exception =>
        logger.error(s"Error executing application: ${e.getMessage}", e)
        throw e
    } finally {
      logger.info("Stopping Spark session")
      spark.stop()
    }
    
    logger.info("Application completed successfully")
  }
  
  /**
   * Executes the application in batch mode
   * 
   * @param spark SparkSession
   */
  def executeBatchMode(spark: SparkSession): Unit = {
    logger.info("Executing in batch mode")
    
    // Create data processor
    val dataProcessor = new DataProcessor(spark)
    
    // Define paths
    val transactionsPath = s"${AppConfig.DataConfig.inputPath}/transactions"
    val customersPath = s"${AppConfig.DataConfig.inputPath}/customers"
    val merchantsPath = s"${AppConfig.DataConfig.inputPath}/merchants"
    val summariesPath = s"${AppConfig.DataConfig.outputPath}/summaries"
    val fraudResultsPath = s"${AppConfig.DataConfig.outputPath}/fraud_results"
    
    // Load data
    logger.info("Loading data")
    val transactions = dataProcessor.loadTransactions(transactionsPath)
    val customers = dataProcessor.loadCustomers(customersPath)
    val merchants = dataProcessor.loadMerchants(merchantsPath)
    
    // Process transactions
    logger.info("Processing transactions")
    val summaries = dataProcessor.processTransactions(transactions)
    
    // Detect fraud
    logger.info("Detecting fraud")
    val fraudResults = dataProcessor.detectFraud(transactions, customers)
    
    // Save results
    logger.info("Saving results")
    dataProcessor.saveData(summaries, summariesPath, Seq("customerId"))
    dataProcessor.saveData(fraudResults, fraudResultsPath, Seq())
    
    logger.info("Batch processing completed")
  }
  
  /**
   * Executes the application in streaming mode
   * 
   * @param spark SparkSession
   */
  def executeStreamingMode(spark: SparkSession): Unit = {
    logger.info("Executing in streaming mode")
    
    // Create stream processor
    val streamProcessor = new StreamProcessor(spark)
    
    // Define paths
    val streamingInputPath = s"${AppConfig.DataConfig.inputPath}/streaming"
    val streamingOutputPath = s"${AppConfig.DataConfig.outputPath}/streaming"
    val checkpointDir = AppConfig.SparkConfig.checkpointDir
    
    // Create transaction stream
    val transactionStream = streamProcessor.createTransactionStream(streamingInputPath)
    
    // Start streaming queries
    val processingQuery = streamProcessor.startTransactionProcessingQuery(
      transactionStream,
      checkpointDir,
      s"$streamingOutputPath/processed"
    )
    
    val anomalyQuery = streamProcessor.startAnomalyDetectionQuery(
      transactionStream,
      checkpointDir,
      s"$streamingOutputPath/anomalies"
    )
    
    val aggregationQuery = streamProcessor.startAggregationQuery(
      transactionStream,
      checkpointDir,
      s"$streamingOutputPath/aggregates"
    )
    
    // Wait for termination
    logger.info("Waiting for streaming queries to terminate")
    spark.streams.awaitAnyTermination()
  }
  
  /**
   * Executes the application in analytics mode
   * 
   * @param spark SparkSession
   */
  def executeAnalyticsMode(spark: SparkSession): Unit = {
    logger.info("Executing in analytics mode")
    
    // Create data processor and analytics engine
    val dataProcessor = new DataProcessor(spark)
    val analyticsEngine = new AnalyticsEngine(spark)
    
    // Define paths
    val transactionsPath = s"${AppConfig.DataConfig.inputPath}/transactions"
    val customersPath = s"${AppConfig.DataConfig.inputPath}/customers"
    val merchantsPath = s"${AppConfig.DataConfig.inputPath}/merchants"
    val modelOutputPath = s"${AppConfig.DataConfig.outputPath}/models"
    val segmentationOutputPath = s"${AppConfig.DataConfig.outputPath}/segmentation"
    val timeSeriesOutputPath = s"${AppConfig.DataConfig.outputPath}/time_series"
    val basketAnalysisOutputPath = s"${AppConfig.DataConfig.outputPath}/basket_analysis"
    
    // Load data
    logger.info("Loading data")
    val transactions = dataProcessor.loadTransactions(transactionsPath)
    val customers = dataProcessor.loadCustomers(customersPath)
    val merchants = dataProcessor.loadMerchants(merchantsPath)
    
    // Build fraud detection model
    logger.info("Building fraud detection model")
    val (model, featureData) = analyticsEngine.buildFraudDetectionModel(transactions, customers)
    
    // Perform customer segmentation
    logger.info("Performing customer segmentation")
    val segmentation = analyticsEngine.performCustomerSegmentation(transactions, customers)
    
    // Perform time series analysis
    logger.info("Performing time series analysis")
    val timeSeriesAnalysis = analyticsEngine.performTimeSeriesAnalysis(transactions)
    
    // Perform market basket analysis
    logger.info("Performing market basket analysis")
    val basketAnalysis = analyticsEngine.performMarketBasketAnalysis(transactions)
    
    // Save results
    logger.info("Saving results")
    model.write.overwrite().save(s"$modelOutputPath/fraud_detection_model")
    segmentation.write.format(AppConfig.DataConfig.format).mode("overwrite").save(segmentationOutputPath)
    timeSeriesAnalysis.write.format(AppConfig.DataConfig.format).mode("overwrite").save(timeSeriesOutputPath)
    basketAnalysis.write.format(AppConfig.DataConfig.format).mode("overwrite").save(basketAnalysisOutputPath)
    
    logger.info("Analytics processing completed")
  }
}

