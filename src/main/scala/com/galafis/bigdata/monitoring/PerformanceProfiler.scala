package com.galafis.bigdata.monitoring

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

/**
 * 🇧🇷 Profiler de performance para DataFrames e jobs Spark
 * 🇺🇸 Performance profiler for Spark DataFrames and jobs
 */
object PerformanceProfiler {

  def profileDataFrame(df: DataFrame): Map[String, Double] = {
    val recordCount = df.count()
    val t0 = System.nanoTime()
    df.cache().count()
    val t1 = System.nanoTime()
    val cacheDuration = (t1 - t0).toDouble / 1e9
    Map("recordCount" -> recordCount, "cacheSeconds" -> cacheDuration)
  }
}
