package com.galafis.bigdata.storage

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 🇧🇷 Gerenciador Apache Iceberg para tabelas analíticas com snapshots
 * 🇺🇸 Apache Iceberg manager for analytics tables with snapshots
 */
object IcebergManager {

  def writeIceberg(df: DataFrame, table: String): Unit = {
    df.writeTo(table).using("iceberg").append()
  }

  def readIceberg(spark: SparkSession, table: String): DataFrame = {
    spark.read.format("iceberg").load(table)
  }

  def snapshotTable(spark: SparkSession, table: String): DataFrame = {
    spark.read.format("iceberg").option("snapshot-id", "latest").load(table)
  }
}
