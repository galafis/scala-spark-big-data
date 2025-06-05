package com.galafis.bigdata.analytics

import com.galafis.bigdata.models.DataModels._
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{LogisticRegression, RandomForestClassifier}
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer, VectorAssembler}
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.slf4j.{Logger, LoggerFactory}

/**
 * Analytics engine for advanced data analysis
 */
class AnalyticsEngine(spark: SparkSession) {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  
  import spark.implicits._
  
  /**
   * Builds a fraud detection model
   * 
   * @param transactions Dataset of transactions
   * @param customers Dataset of customers
   * @return Trained model and feature data
   */
  def buildFraudDetectionModel(
    transactions: Dataset[Transaction],
    customers: Dataset[Customer]
  ): (Pipeline, DataFrame) = {
    logger.info("Building fraud detection model")
    
    // Join transactions with customers
    val joinedData = transactions.join(
      customers,
      transactions("customerId") === customers("id"),
      "inner"
    )
    
    // Prepare features
    val featureData = joinedData.select(
      col("id").as("transactionId"),
      col("amount"),
      col("category"),
      col("merchantId"),
      col("location"),
      col("deviceType"),
      col("age"),
      col("gender"),
      col("segment"),
      col("isFraud").as("label")
    )
    
    // Handle categorical features
    val categoryIndexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")
      .setHandleInvalid("keep")
    
    val merchantIndexer = new StringIndexer()
      .setInputCol("merchantId")
      .setOutputCol("merchantIndex")
      .setHandleInvalid("keep")
    
    val locationIndexer = new StringIndexer()
      .setInputCol("location")
      .setOutputCol("locationIndex")
      .setHandleInvalid("keep")
    
    val deviceIndexer = new StringIndexer()
      .setInputCol("deviceType")
      .setOutputCol("deviceIndex")
      .setHandleInvalid("keep")
    
    val genderIndexer = new StringIndexer()
      .setInputCol("gender")
      .setOutputCol("genderIndex")
      .setHandleInvalid("keep")
    
    val segmentIndexer = new StringIndexer()
      .setInputCol("segment")
      .setOutputCol("segmentIndex")
      .setHandleInvalid("keep")
    
    // One-hot encode categorical features
    val categoryEncoder = new OneHotEncoder()
      .setInputCol("categoryIndex")
      .setOutputCol("categoryVec")
    
    val merchantEncoder = new OneHotEncoder()
      .setInputCol("merchantIndex")
      .setOutputCol("merchantVec")
    
    val locationEncoder = new OneHotEncoder()
      .setInputCol("locationIndex")
      .setOutputCol("locationVec")
    
    val deviceEncoder = new OneHotEncoder()
      .setInputCol("deviceIndex")
      .setOutputCol("deviceVec")
    
    val genderEncoder = new OneHotEncoder()
      .setInputCol("genderIndex")
      .setOutputCol("genderVec")
    
    val segmentEncoder = new OneHotEncoder()
      .setInputCol("segmentIndex")
      .setOutputCol("segmentVec")
    
    // Assemble features
    val assembler = new VectorAssembler()
      .setInputCols(Array(
        "amount",
        "categoryVec",
        "merchantVec",
        "locationVec",
        "deviceVec",
        "age",
        "genderVec",
        "segmentVec"
      ))
      .setOutputCol("features")
    
    // Create classifier
    val classifier = new RandomForestClassifier()
      .setLabelCol("label")
      .setFeaturesCol("features")
      .setPredictionCol("prediction")
      .setProbabilityCol("probability")
      .setRawPredictionCol("rawPrediction")
      .setMaxDepth(10)
      .setNumTrees(100)
      .setMinInstancesPerNode(5)
    
    // Create pipeline
    val pipeline = new Pipeline()
      .setStages(Array(
        categoryIndexer,
        merchantIndexer,
        locationIndexer,
        deviceIndexer,
        genderIndexer,
        segmentIndexer,
        categoryEncoder,
        merchantEncoder,
        locationEncoder,
        deviceEncoder,
        genderEncoder,
        segmentEncoder,
        assembler,
        classifier
      ))
    
    // Split data into training and test sets
    val Array(trainingData, testData) = featureData.randomSplit(Array(0.8, 0.2), seed = 42)
    
    // Train model
    logger.info("Training fraud detection model")
    val model = pipeline.fit(trainingData)
    
    // Evaluate model
    logger.info("Evaluating fraud detection model")
    val predictions = model.transform(testData)
    
    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("label")
      .setRawPredictionCol("rawPrediction")
      .setMetricName("areaUnderROC")
    
    val auc = evaluator.evaluate(predictions)
    logger.info(s"Model AUC: $auc")
    
    // Return model and feature data
    (pipeline, featureData)
  }
  
  /**
   * Performs customer segmentation
   * 
   * @param transactions Dataset of transactions
   * @param customers Dataset of customers
   * @return DataFrame with customer segments
   */
  def performCustomerSegmentation(
    transactions: Dataset[Transaction],
    customers: Dataset[Customer]
  ): DataFrame = {
    logger.info("Performing customer segmentation")
    
    // Calculate customer metrics
    val customerMetrics = transactions
      .groupBy(col("customerId"))
      .agg(
        count("*").as("transactionCount"),
        sum("amount").as("totalSpend"),
        avg("amount").as("avgTransactionAmount"),
        max("amount").as("maxTransactionAmount"),
        min("amount").as("minTransactionAmount"),
        stddev("amount").as("stdDevAmount"),
        countDistinct("category").as("categoryCount"),
        countDistinct("merchantId").as("merchantCount"),
        sum(when(col("isFraud"), 1).otherwise(0)).as("fraudCount")
      )
    
    // Join with customer data
    val customerData = customerMetrics.join(
      customers.toDF(),
      customerMetrics("customerId") === customers("id"),
      "inner"
    )
    
    // Calculate RFM (Recency, Frequency, Monetary) metrics
    val rfmData = customerData.withColumn(
      "recencyScore",
      when(datediff(current_date(), max(col("timestamp"))).over(Window.partitionBy("customerId")) <= 30, 3)
        .when(datediff(current_date(), max(col("timestamp"))).over(Window.partitionBy("customerId")) <= 90, 2)
        .otherwise(1)
    ).withColumn(
      "frequencyScore",
      when(col("transactionCount") >= 10, 3)
        .when(col("transactionCount") >= 5, 2)
        .otherwise(1)
    ).withColumn(
      "monetaryScore",
      when(col("totalSpend") >= 1000, 3)
        .when(col("totalSpend") >= 500, 2)
        .otherwise(1)
    ).withColumn(
      "rfmScore",
      col("recencyScore") + col("frequencyScore") + col("monetaryScore")
    )
    
    // Assign segments based on RFM score
    val segmentedData = rfmData.withColumn(
      "customerSegment",
      when(col("rfmScore") >= 8, "High Value")
        .when(col("rfmScore") >= 5, "Medium Value")
        .otherwise("Low Value")
    )
    
    logger.info("Customer segmentation completed")
    segmentedData
  }
  
  /**
   * Performs time series analysis on transaction data
   * 
   * @param transactions Dataset of transactions
   * @return DataFrame with time series analysis results
   */
  def performTimeSeriesAnalysis(transactions: Dataset[Transaction]): DataFrame = {
    logger.info("Performing time series analysis")
    
    // Extract date components
    val timeData = transactions.withColumn("date", to_date(col("timestamp")))
      .withColumn("year", year(col("date")))
      .withColumn("month", month(col("date")))
      .withColumn("day", dayofmonth(col("date")))
      .withColumn("dayOfWeek", dayofweek(col("date")))
      .withColumn("hour", hour(col("timestamp")))
    
    // Aggregate by time periods
    val dailyAgg = timeData.groupBy("date")
      .agg(
        count("*").as("transactionCount"),
        sum("amount").as("totalAmount"),
        avg("amount").as("avgAmount"),
        sum(when(col("isFraud"), 1).otherwise(0)).as("fraudCount")
      )
      .orderBy("date")
    
    // Calculate moving averages
    val windowSpec7d = Window.orderBy("date").rowsBetween(-6, 0)
    val windowSpec30d = Window.orderBy("date").rowsBetween(-29, 0)
    
    val withMovingAvg = dailyAgg
      .withColumn("ma7d_count", avg(col("transactionCount")).over(windowSpec7d))
      .withColumn("ma7d_amount", avg(col("totalAmount")).over(windowSpec7d))
      .withColumn("ma30d_count", avg(col("transactionCount")).over(windowSpec30d))
      .withColumn("ma30d_amount", avg(col("totalAmount")).over(windowSpec30d))
    
    // Calculate growth rates
    val withGrowthRates = withMovingAvg
      .withColumn("prev_day_amount", lag(col("totalAmount"), 1).over(Window.orderBy("date")))
      .withColumn("prev_day_count", lag(col("transactionCount"), 1).over(Window.orderBy("date")))
      .withColumn("day_over_day_amount_growth", 
        when(col("prev_day_amount").isNotNull, 
          (col("totalAmount") - col("prev_day_amount")) / col("prev_day_amount")
        ).otherwise(0)
      )
      .withColumn("day_over_day_count_growth", 
        when(col("prev_day_count").isNotNull, 
          (col("transactionCount") - col("prev_day_count")) / col("prev_day_count")
        ).otherwise(0)
      )
    
    logger.info("Time series analysis completed")
    withGrowthRates
  }
  
  /**
   * Performs market basket analysis on transaction data
   * 
   * @param transactions Dataset of transactions
   * @return DataFrame with association rules
   */
  def performMarketBasketAnalysis(transactions: Dataset[Transaction]): DataFrame = {
    logger.info("Performing market basket analysis")
    
    // Prepare transactions for FP-Growth
    val itemsPerTransaction = transactions
      .select("id", "category")
      .filter(col("category").isNotNull)
      .groupBy("id")
      .agg(collect_list("category").as("items"))
    
    // Create FP-Growth model
    val fpGrowth = new FPGrowth()
      .setItemsCol("items")
      .setMinSupport(0.01)
      .setMinConfidence(0.5)
    
    val model = fpGrowth.fit(itemsPerTransaction)
    
    // Get frequent itemsets
    val frequentItemsets = model.freqItemsets
    
    // Get association rules
    val associationRules = model.associationRules
    
    logger.info("Market basket analysis completed")
    associationRules
  }
}

