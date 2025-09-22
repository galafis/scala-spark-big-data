package com.galafis.bigdata.storage

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 🇧🇷 Integração com Hive Metastore para catalogação e queries SQL distribuídas
 * 🇺🇸 Hive Metastore integration for catalog and distributed SQL queries
 */
object HiveMetastore {

  def queryTable(spark: SparkSession, table: String): DataFrame = {
    spark.sql(s"SELECT * FROM $table")
  }

  def listTables(spark: SparkSession, database: String): Seq[String] = {
    spark.catalog.listTables(database).collect().map(_.name)
  }
}
