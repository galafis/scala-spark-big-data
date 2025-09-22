package com.galafis.bigdata.storage

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 🇧🇷 Gerenciamento de leitura e escrita em buckets S3 (AWS ou compatível)
 * 🇺🇸 S3 bucket read/write management (AWS or compatible)
 */
object S3Manager {

  def writeToS3(df: DataFrame, path: String, format: String = "parquet"): Unit = {
    df.write.format(format).mode("overwrite").save(path)
  }

  def readFromS3(spark: SparkSession, path: String, format: String = "parquet"): DataFrame = {
    spark.read.format(format).load(path)
  }
}
