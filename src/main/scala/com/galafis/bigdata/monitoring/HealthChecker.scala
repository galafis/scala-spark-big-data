package com.galafis.bigdata.monitoring

import org.apache.spark.sql.SparkSession

/**
 * 🇧🇷 Verificador de saúde do cluster Spark e do pipeline
 * 🇺🇸 Spark cluster and pipeline health checker
 */
object HealthChecker {

  def isClusterHealthy(spark: SparkSession): Boolean = {
    spark.sparkContext.statusTracker.getExecutorInfos.length > 0
  }

  def checkDataPath(path: String): Boolean = {
    val file = new java.io.File(path)
    file.exists() && file.canRead
  }
}
