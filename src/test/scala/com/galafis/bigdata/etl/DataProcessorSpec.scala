package com.galafis.bigdata.etl

import com.galafis.bigdata.models.DataModels._
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.sql.Timestamp
import java.time.Instant

class DataProcessorSpec extends AnyFlatSpec with Matchers with BeforeAndAfterAll {
  
  lazy val spark: SparkSession = SparkSession.builder()
      .appName("DataProcessorSpec")
      .master("local[*]")
      .getOrCreate()

  import spark.implicits._

  lazy val dataProcessor = new DataProcessor(spark)

  override def afterAll(): Unit = {
    if (spark != null) {
      spark.stop()
    }
    super.afterAll()
  }
  
  "DataProcessor" should "process transactions correctly" in {
    
    // Create test data
    val transactions = Seq(
      Transaction("1", Timestamp.from(Instant.now()), "customer1", 100.0, "category1", "merchant1", "location1", "mobile", false),
      Transaction("2", Timestamp.from(Instant.now()), "customer1", 200.0, "category2", "merchant2", "location2", "web", false),
      Transaction("3", Timestamp.from(Instant.now()), "customer2", 300.0, "category1", "merchant1", "location1", "mobile", false),
      Transaction("4", Timestamp.from(Instant.now()), "customer2", 400.0, "category3", "merchant3", "location3", "mobile", true)
    ).toDS()
    
    // Process transactions
    val summaries = dataProcessor.processTransactions(transactions)
    
    // Verify results
    summaries.count() should be(2)
    
    val customer1Summary = summaries.filter($"customerId" === "customer1").first()
    customer1Summary.totalAmount should be(300.0)
    customer1Summary.transactionCount should be(2)
    customer1Summary.avgAmount should be(150.0)
    customer1Summary.maxAmount should be(200.0)
    customer1Summary.minAmount should be(100.0)
    customer1Summary.fraudCount should be(0)
    
    val customer2Summary = summaries.filter($"customerId" === "customer2").first()
    customer2Summary.totalAmount should be(700.0)
    customer2Summary.transactionCount should be(2)
    customer2Summary.avgAmount should be(350.0)
    customer2Summary.maxAmount should be(400.0)
    customer2Summary.minAmount should be(300.0)
    customer2Summary.fraudCount should be(1)
  }
  
  it should "detect fraud correctly" in {
    
    // Create test data
    val transactions = Seq(
      Transaction("1", Timestamp.from(Instant.now()), "customer1", 100.0, "category1", "merchant1", "location1", "mobile", false),
      Transaction("2", Timestamp.from(Instant.now()), "customer1", 2000.0, "category2", "merchant2", "location2", "web", false),
      Transaction("3", Timestamp.from(Instant.now()), "customer2", 300.0, "category1", "merchant1", "location1", "mobile", false),
      Transaction("4", Timestamp.from(Instant.now()), "customer2", 400.0, "category3", "merchant3", "location3", "mobile", true)
    ).toDS()
    
    val customers = Seq(
      Customer("customer1", "John Doe", "john@example.com", Timestamp.from(Instant.now()), "premium", 35, "male", "location1"),
      Customer("customer2", "Jane Smith", "jane@example.com", Timestamp.from(Instant.now()), "standard", 22, "female", "location2")
    ).toDS()
    
    // Detect fraud
    val fraudResults = dataProcessor.detectFraud(transactions, customers)
    
    // Verify results
    fraudResults.count() should be(4)
    
    val highAmountTransaction = fraudResults.filter($"transactionId" === "2").first()
    highAmountTransaction.riskScore should be > 0.0
    
    val knownFraudTransaction = fraudResults.filter($"transactionId" === "4").first()
    knownFraudTransaction.fraudProbability should be > highAmountTransaction.fraudProbability
    knownFraudTransaction.isFraud should be(true)
  }
}

