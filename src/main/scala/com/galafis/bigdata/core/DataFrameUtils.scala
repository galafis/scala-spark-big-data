package com.galafis.bigdata.core

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.storage.StorageLevel

/**
 * Utilitários para DataFrames com otimizações avançadas
 */
object DataFrameUtils {
  
  /**
   * Cache inteligente baseado no tamanho dos dados
   */
  def smartCache(df: DataFrame): DataFrame = {
    val spark = df.sparkSession
    
    // Estimar tamanho do DataFrame
    val estimatedSize = estimateDataFrameSize(df)
    val availableMemory = getAvailableMemory(spark)
    
    if (estimatedSize < availableMemory * 0.3) {
      // Cache em memória se cabe confortavelmente
      df.cache()
    } else if (estimatedSize < availableMemory * 0.8) {
      // Cache com serialização se é grande mas ainda cabe
      df.persist(StorageLevel.MEMORY_AND_DISK_SER)
    } else {
      // Apenas em disco se é muito grande
      df.persist(StorageLevel.DISK_ONLY)
    }
  }
  
  /**
   * Estimar tamanho de um DataFrame
   */
  private def estimateDataFrameSize(df: DataFrame): Long = {
    val numRows = df.count()
    val avgRowSize = df.schema.fields.map(estimateFieldSize).sum
    numRows * avgRowSize
  }
  
  /**
   * Estimar tamanho de um campo
   */
  private def estimateFieldSize(field: StructField): Long = {
    field.dataType match {
      case IntegerType => 4
      case LongType => 8
      case DoubleType => 8
      case FloatType => 4
      case BooleanType => 1
      case StringType => 50 // Estimativa média
      case TimestampType => 8
      case DateType => 4
      case _ => 20 // Estimativa para tipos complexos
    }
  }
  
  /**
   * Obter memória disponível
   */
  private def getAvailableMemory(spark: SparkSession): Long = {
    val executorMemory = spark.conf.get("spark.executor.memory", "1g")
    val numExecutors = spark.conf.get("spark.executor.instances", "1").toInt
    
    // Converter string de memória para bytes (simplificado)
    val memoryInGB = executorMemory.replace("g", "").replace("G", "").toDouble
    (memoryInGB * 1024 * 1024 * 1024 * numExecutors * 0.6).toLong // 60% da memória disponível
  }
  
  /**
   * Otimizar particionamento baseado no tamanho dos dados
   */
  def optimizePartitioning(df: DataFrame, targetPartitionSize: Long = 128 * 1024 * 1024): DataFrame = {
    val currentSize = estimateDataFrameSize(df)
    val optimalPartitions = Math.max(1, (currentSize / targetPartitionSize).toInt)
    
    if (df.rdd.getNumPartitions > optimalPartitions * 2) {
      // Muitas partições pequenas - coalescer
      df.coalesce(optimalPartitions)
    } else if (df.rdd.getNumPartitions < optimalPartitions / 2) {
      // Poucas partições grandes - reparticionar
      df.repartition(optimalPartitions)
    } else {
      // Particionamento já está bom
      df
    }
  }
  
  /**
   * Análise de qualidade de dados
   */
  def analyzeDataQuality(df: DataFrame): Map[String, Any] = {
    val totalRows = df.count()
    
    val qualityMetrics = df.schema.fields.map { field =>
      val colName = field.name
      val nullCount = df.filter(col(colName).isNull).count()
      val nullPercentage = (nullCount.toDouble / totalRows) * 100
      val distinctCount = df.select(colName).distinct().count()
      val uniquenessPercentage = (distinctCount.toDouble / totalRows) * 100
      
      colName -> Map(
        "null_count" -> nullCount,
        "null_percentage" -> nullPercentage,
        "distinct_count" -> distinctCount,
        "uniqueness_percentage" -> uniquenessPercentage
      )
    }.toMap
    
    Map(
      "total_rows" -> totalRows,
      "column_metrics" -> qualityMetrics
    )
  }
  
  /**
   * Detectar e tratar outliers usando IQR
   */
  def detectOutliers(df: DataFrame, numericColumns: Seq[String]): DataFrame = {
    var result = df
    
    numericColumns.foreach { colName =>
      val quantiles = df.stat.approxQuantile(colName, Array(0.25, 0.75), 0.05)
      val q1 = quantiles(0)
      val q3 = quantiles(1)
      val iqr = q3 - q1
      val lowerBound = q1 - 1.5 * iqr
      val upperBound = q3 + 1.5 * iqr
      
      result = result.withColumn(
        s"${colName}_outlier",
        when(col(colName) < lowerBound || col(colName) > upperBound, true).otherwise(false)
      )
    }
    
    result
  }
  
  /**
   * Perfil estatístico de colunas numéricas
   */
  def profileNumericColumns(df: DataFrame, numericColumns: Seq[String]): Map[String, Map[String, Double]] = {
    numericColumns.map { colName =>
      val stats = df.select(
        mean(col(colName)).as("mean"),
        stddev(col(colName)).as("stddev"),
        min(col(colName)).as("min"),
        max(col(colName)).as("max"),
        skewness(col(colName)).as("skewness"),
        kurtosis(col(colName)).as("kurtosis")
      ).collect()(0)
      
      colName -> Map(
        "mean" -> stats.getAs[Double]("mean"),
        "stddev" -> stats.getAs[Double]("stddev"),
        "min" -> stats.getAs[Double]("min"),
        "max" -> stats.getAs[Double]("max"),
        "skewness" -> stats.getAs[Double]("skewness"),
        "kurtosis" -> stats.getAs[Double]("kurtosis")
      )
    }.toMap
  }
  
  /**
   * Validação de esquema
   */
  def validateSchema(df: DataFrame, expectedSchema: StructType): Boolean = {
    val actualSchema = df.schema
    
    if (actualSchema.fields.length != expectedSchema.fields.length) {
      return false
    }
    
    actualSchema.fields.zip(expectedSchema.fields).forall { case (actual, expected) =>
      actual.name == expected.name && 
      actual.dataType == expected.dataType &&
      actual.nullable == expected.nullable
    }
  }
  
  /**
   * Limpeza automática de dados
   */
  def cleanData(df: DataFrame, 
                dropNullThreshold: Double = 0.5,
                fillNullStrategy: String = "mean"): DataFrame = {
    var cleanedDF = df
    
    // Remover colunas com muitos valores nulos
    val totalRows = df.count()
    val columnsToKeep = df.schema.fields.filter { field =>
      val nullCount = df.filter(col(field.name).isNull).count()
      val nullPercentage = nullCount.toDouble / totalRows
      nullPercentage <= dropNullThreshold
    }.map(_.name)
    
    cleanedDF = cleanedDF.select(columnsToKeep.map(col): _*)
    
    // Preencher valores nulos restantes
    val numericColumns = cleanedDF.schema.fields
      .filter(field => field.dataType.isInstanceOf[NumericType])
      .map(_.name)
    
    val stringColumns = cleanedDF.schema.fields
      .filter(_.dataType == StringType)
      .map(_.name)
    
    fillNullStrategy match {
      case "mean" =>
        numericColumns.foreach { col_name =>
          val meanValue = cleanedDF.select(mean(col(col_name))).collect()(0).getDouble(0)
          cleanedDF = cleanedDF.na.fill(meanValue, Seq(col_name))
        }
        cleanedDF = cleanedDF.na.fill("UNKNOWN", stringColumns)
        
      case "median" =>
        numericColumns.foreach { col_name =>
          val medianValue = cleanedDF.stat.approxQuantile(col_name, Array(0.5), 0.01)(0)
          cleanedDF = cleanedDF.na.fill(medianValue, Seq(col_name))
        }
        cleanedDF = cleanedDF.na.fill("UNKNOWN", stringColumns)
        
      case "drop" =>
        cleanedDF = cleanedDF.na.drop()
    }
    
    cleanedDF
  }
  
  /**
   * Amostragem estratificada
   */
  def stratifiedSample(df: DataFrame, 
                       stratifyColumn: String, 
                       fractions: Map[Any, Double],
                       seed: Long = 42): DataFrame = {
    df.stat.sampleBy(stratifyColumn, fractions, seed)
  }
  
  /**
   * Comparação de esquemas
   */
  def compareSchemas(schema1: StructType, schema2: StructType): Map[String, Any] = {
    val fields1 = schema1.fields.map(f => f.name -> f).toMap
    val fields2 = schema2.fields.map(f => f.name -> f).toMap
    
    val commonFields = fields1.keys.toSet.intersect(fields2.keys.toSet)
    val onlyInSchema1 = fields1.keys.toSet -- fields2.keys.toSet
    val onlyInSchema2 = fields2.keys.toSet -- fields1.keys.toSet
    
    val typeChanges = commonFields.filter { fieldName =>
      fields1(fieldName).dataType != fields2(fieldName).dataType
    }.map { fieldName =>
      fieldName -> Map(
        "schema1_type" -> fields1(fieldName).dataType.toString,
        "schema2_type" -> fields2(fieldName).dataType.toString
      )
    }.toMap
    
    Map(
      "common_fields" -> commonFields.size,
      "only_in_schema1" -> onlyInSchema1,
      "only_in_schema2" -> onlyInSchema2,
      "type_changes" -> typeChanges,
      "schemas_identical" -> (onlyInSchema1.isEmpty && onlyInSchema2.isEmpty && typeChanges.isEmpty)
    )
  }
}
