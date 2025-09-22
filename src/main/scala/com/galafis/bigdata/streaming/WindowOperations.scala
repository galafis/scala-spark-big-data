package com.galafis.bigdata.streaming

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

/**
 * 🇧🇷 Operações de janelas para agregações temporais em streaming
 * 🇺🇸 Window operations for temporal aggregations in streaming
 */
object WindowOperations {

  def minuteWindowAgg(df: DataFrame): DataFrame = {
    df
      .withWatermark("timestamp", "10 minutes")
      .groupBy(window(col("timestamp"), "1 minute"))
      .agg(
        count("*").as("transaction_count"),
        sum("amount").as("total_volume"),
        avg("amount").as("avg_volume")
      )
      .select(
        col("window.start").as("window_start"),
        col("window.end").as("window_end"),
        col("transaction_count"),
        col("total_volume"),
        col("avg_volume")
      )
  }

  def slidingWindowAgg(df: DataFrame, windowDuration: String, slideDuration: String): DataFrame = {
    df
      .groupBy(window(col("timestamp"), windowDuration, slideDuration))
      .agg(
        count("*").as("event_count"),
        sum("amount").as("sum_amount")
      )
      .orderBy("window.start")
  }
}
