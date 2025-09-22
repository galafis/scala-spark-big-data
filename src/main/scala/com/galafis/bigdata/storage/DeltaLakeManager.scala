package com.galafis.bigdata.storage

import org.apache.spark.sql.{DataFrame, SparkSession}
import io.delta.tables.DeltaTable

/**
 * 🇧🇷 Gerenciador Delta Lake para leitura, escrita e manutenção ACID
 * 🇺🇸 Delta Lake manager for ACID read/write and maintenance
 */
object DeltaLakeManager {

  def writeDelta(df: DataFrame, path: String, partitionCols: Seq[String] = Seq.empty): Unit = {
    val writer = df.write.format("delta").mode("append")
    if (partitionCols.nonEmpty) writer.partitionBy(partitionCols: _*)
    writer.save(path)
  }

  def readDelta(spark: SparkSession, path: String): DataFrame = {
    spark.read.format("delta").load(path)
  }

  def vacuumTable(spark: SparkSession, path: String, retentionHours: Int = 168): Unit = {
    DeltaTable.forPath(spark, path).vacuum(retentionHours)
  }
}
