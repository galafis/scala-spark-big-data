package com.galafis.bigdata.streaming

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{StreamingQuery, Trigger}
import org.apache.spark.sql.types._

/**
 * 🇺🇸 KinesisStreaming: helpers to read from/write to Amazon Kinesis with Spark Structured Streaming
 * 🇧🇷 KinesisStreaming: helpers para ler/escrever do Amazon Kinesis com Spark Structured Streaming
 */
object KinesisStreaming {

  // NOTE: Requires spark-sql-kinesis connector on the classpath for your Spark version
  // Observação: Requer o conector spark-sql-kinesis compatível na classpath do Spark

  /**
   * 🇺🇸 Create a Kinesis source DataFrame using the Kinesis connector
   * 🇧🇷 Cria um DataFrame de fonte Kinesis usando o conector
   */
  def read(spark: SparkSession,
           streamName: String,
           region: String,
           initialPosition: String = "LATEST", // TRIM_HORIZON | LATEST | AT_TIMESTAMP
           endpointUrl: Option[String] = None,
           awsAccessKey: Option[String] = None,
           awsSecretKey: Option[String] = None,
           awsSessionToken: Option[String] = None): DataFrame = {

    val reader = spark.readStream
      .format("kinesis")
      .option("streamName", streamName)
      .option("region", region)
      .option("initialPosition", initialPosition)

    val withEndpoint = endpointUrl.map(url => reader.option("endpointUrl", url)).getOrElse(reader)
    val withAccess = awsAccessKey.map(k => withEndpoint.option("awsAccessKeyId", k)).getOrElse(withEndpoint)
    val withSecret = awsSecretKey.map(s => withAccess.option("awsSecretKey", s)).getOrElse(withAccess)
    val withToken  = awsSessionToken.map(t => withSecret.option("awsSessionToken", t)).getOrElse(withSecret)

    withToken.load()
      .select(
        col("sequenceNumber"),
        col("partitionKey").as("key"),
        col("data").cast("string").as("value"),
        col("approximateArrivalTimestamp").as("timestamp")
      )
  }

  /**
   * 🇺🇸 Parse JSON payload into a structured schema
   * 🇧🇷 Faz parse do payload JSON para um schema estruturado
   */
  def parseJson(df: DataFrame, schema: StructType, valueCol: String = "value"): DataFrame = {
    df.withColumn("parsed", from_json(col(valueCol), schema))
      .select(col("key"), col("timestamp"), col("parsed.*"))
  }

  /**
   * 🇺🇸 Write to Kinesis via Kinesis Data Streams sink (through connector)
   * 🇧🇷 Escreve para o Kinesis via sink do conector
   */
  def write(df: DataFrame,
            streamName: String,
            region: String,
            checkpointLocation: String,
            endpointUrl: Option[String] = None,
            triggerMs: Long = 10000L): StreamingQuery = {

    val writer = df
      .select(col("key").cast("string").as("partitionKey"), to_json(struct(df.columns.map(col): _*)).as("data"))
      .writeStream
      .format("kinesis")
      .option("streamName", streamName)
      .option("region", region)
      .option("checkpointLocation", checkpointLocation)

    val withEndpoint = endpointUrl.map(url => writer.option("endpointUrl", url)).getOrElse(writer)

    withEndpoint
      .trigger(Trigger.ProcessingTime(triggerMs))
      .start()
  }
}
