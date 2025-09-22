package com.galafis.bigdata.streaming

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{StreamingQuery, Trigger}
import org.apache.spark.sql.types._

/**
 * 🇺🇸 KafkaStreaming: helper to build Kafka read/write streams with sensible defaults
 * 🇧🇷 KafkaStreaming: helper para construir streams de leitura/escrita Kafka com padrões adequados
 */
object KafkaStreaming {

  // ---- Reader | Leitor -----------------------------------------------------
  /**
   * 🇺🇸 Create a Kafka source as DataFrame (key/value as strings, with metadata)
   * 🇧🇷 Cria uma fonte Kafka como DataFrame (key/value em string, com metadados)
   */
  def read(spark: SparkSession,
           brokers: String,
           topics: Seq[String],
           startingOffsets: String = "latest",
           failOnDataLoss: Boolean = false): DataFrame = {
    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("subscribe", topics.mkString(","))
      .option("startingOffsets", startingOffsets)
      .option("failOnDataLoss", failOnDataLoss.toString)
      .load()
      .select(
        col("key").cast("string").as("key"),
        col("value").cast("string").as("value"),
        col("topic"), col("partition"), col("offset"), col("timestamp")
      )
  }

  // ---- Parser | Parser -----------------------------------------------------
  /**
   * 🇺🇸 Parse JSON value column to a structured schema
   * 🇧🇷 Faz o parse da coluna value (JSON) para um schema estruturado
   */
  def parseJson(df: DataFrame, schema: StructType, valueCol: String = "value"): DataFrame = {
    df.withColumn("parsed", from_json(col(valueCol), schema))
      .select(col("key"), col("topic"), col("partition"), col("offset"), col("timestamp"), col("parsed.*"))
  }

  // ---- Writer | Escritor ---------------------------------------------------
  /**
   * 🇺🇸 Write a stream back to Kafka (expects key and value as strings)
   * 🇧🇷 Escreve um stream de volta para o Kafka (key e value como strings)
   */
  def write(df: DataFrame,
            brokers: String,
            topic: String,
            checkpointLocation: String,
            triggerMs: Long = 10000L): StreamingQuery = {
    df.select(col("key").cast("string").as("key"), to_json(struct(df.columns.map(col): _*)).as("value"))
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("topic", topic)
      .option("checkpointLocation", checkpointLocation)
      .trigger(Trigger.ProcessingTime(triggerMs))
      .start()
  }
}
