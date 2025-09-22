package com.galafis.bigdata.storage

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 🇧🇷 Gerenciador de arquivos distribuídos em HDFS, leitura e escrita eficiente
 * 🇺🇸 Distributed file manager for HDFS, efficient read and write
 */
object HDFSManager {

  def readHDFS(spark: SparkSession, path: String, format: String = "parquet"): DataFrame = {
    spark.read.format(format).load(path)
  }

  def writeHDFS(df: DataFrame, path: String, format: String = "parquet"): Unit = {
    df.write.format(format).mode("overwrite").save(path)
  }
}
