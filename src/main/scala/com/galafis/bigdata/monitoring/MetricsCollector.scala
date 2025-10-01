package com.galafis.bigdata.monitoring

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/**
 * 🇧🇷 Coleta de métricas operacionais e de pipelines com Spark
 * 🇺🇸 Collection of operational and pipeline metrics with Spark
 */
object MetricsCollector {

  def collectJobMetrics(spark: SparkSession): Map[String, Any] = {
    val status = spark.sparkContext.statusTracker
    val activeJobs = status.getActiveJobIds().length
    val completedJobs = status.getJobIdsForGroup("default").length
    Map("activeJobs" -> activeJobs, "completedJobs" -> completedJobs)
  }

  def collectDataFrameMetrics(df: DataFrame): Map[String, Double] = {
    Map(
      "recordCount" -> df.count().toDouble,
      "numColumns" -> df.columns.length.toDouble
    )
  }

  def recordSuccess(appName: String): Unit = {
    println(s"METRIC - $appName: Success")
    // Implementar lógica de registro de sucesso (ex: Prometheus, InfluxDB)
  }

  def recordFailure(appName: String, exception: Throwable): Unit = {
    println(s"METRIC - $appName: Failure - ${exception.getMessage}")
    // Implementar lógica de registro de falha (ex: Prometheus, InfluxDB)
  }
}

