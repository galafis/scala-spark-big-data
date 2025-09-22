package com.galafis.bigdata.core

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark.serializer.KryoSerializer
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenObjectFactoryMode
import com.typesafe.config.{Config, ConfigFactory}
import scala.util.{Try, Success, Failure}

/**
 * Gerenciador centralizado de sessões Spark com otimizações avançadas
 */
object SparkSessionManager {
  
  private var sparkSession: Option[SparkSession] = None
  private val config: Config = ConfigFactory.load()
  
  /**
   * Criar ou obter sessão Spark otimizada
   */
  def getOrCreateSession(appName: String = "BigDataPlatform"): SparkSession = {
    sparkSession.getOrElse {
      val session = createOptimizedSession(appName)
      sparkSession = Some(session)
      
      // Configurar hooks de shutdown
      sys.addShutdownHook {
        stopSession()
      }
      
      session
    }
  }
  
  /**
   * Criar sessão Spark com configurações otimizadas
   */
  private def createOptimizedSession(appName: String): SparkSession = {
    val conf = new SparkConf()
      .setAppName(appName)
      .set("spark.serializer", classOf[KryoSerializer].getName)
      .set("spark.sql.adaptive.enabled", "true")
      .set("spark.sql.adaptive.coalescePartitions.enabled", "true")
      .set("spark.sql.adaptive.skewJoin.enabled", "true")
      .set("spark.sql.codegen.wholeStage", "true")
      .set("spark.sql.codegen.factoryMode", CodegenObjectFactoryMode.CODEGEN_ONLY.toString)
      
    // Configurações de memória otimizadas
    conf.set("spark.executor.memory", config.getString("spark.executor.memory"))
        .set("spark.executor.cores", config.getString("spark.executor.cores"))
        .set("spark.executor.instances", config.getString("spark.executor.instances"))
        .set("spark.driver.memory", config.getString("spark.driver.memory"))
        .set("spark.driver.maxResultSize", config.getString("spark.driver.maxResultSize"))
        
    // Configurações de shuffle otimizadas
    conf.set("spark.sql.shuffle.partitions", config.getString("spark.sql.shuffle.partitions"))
        .set("spark.shuffle.service.enabled", "true")
        .set("spark.shuffle.compress", "true")
        .set("spark.shuffle.spill.compress", "true")
        
    // Configurações de cache
    conf.set("spark.sql.inMemoryColumnarStorage.compressed", "true")
        .set("spark.sql.inMemoryColumnarStorage.batchSize", "20000")
        
    // Configurações de Delta Lake se habilitado
    if (config.getBoolean("delta.enabled")) {
      conf.set("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
          .set("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
    }
    
    // Configurações de Iceberg se habilitado
    if (config.getBoolean("iceberg.enabled")) {
      conf.set("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
          .set("spark.sql.catalog.spark_catalog", "org.apache.iceberg.spark.SparkSessionCatalog")
          .set("spark.sql.catalog.spark_catalog.type", "hive")
    }
    
    // Configurações específicas do ambiente
    val environment = config.getString("environment")
    environment match {
      case "local" => configureLocalMode(conf)
      case "cluster" => configureClusterMode(conf)
      case "cloud" => configureCloudMode(conf)
    }
    
    SparkSession.builder()
      .config(conf)
      .enableHiveSupport()
      .getOrCreate()
  }
  
  /**
   * Configurações para modo local
   */
  private def configureLocalMode(conf: SparkConf): Unit = {
    conf.setMaster("local[*]")
        .set("spark.sql.warehouse.dir", "/tmp/spark-warehouse")
        .set("spark.driver.bindAddress", "127.0.0.1")
  }
  
  /**
   * Configurações para modo cluster
   */
  private def configureClusterMode(conf: SparkConf): Unit = {
    conf.setMaster(config.getString("spark.master"))
        .set("spark.submit.deployMode", "cluster")
        .set("spark.kubernetes.container.image", config.getString("spark.kubernetes.image"))
        .set("spark.kubernetes.authenticate.driver.serviceAccountName", "spark")
  }
  
  /**
   * Configurações para modo cloud
   */
  private def configureCloudMode(conf: SparkConf): Unit = {
    val cloudProvider = config.getString("cloud.provider")
    
    cloudProvider match {
      case "aws" => configureAWS(conf)
      case "gcp" => configureGCP(conf)
      case "azure" => configureAzure(conf)
    }
  }
  
  /**
   * Configurações específicas para AWS
   */
  private def configureAWS(conf: SparkConf): Unit = {
    conf.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
        .set("spark.hadoop.fs.s3a.aws.credentials.provider", 
             "com.amazonaws.auth.DefaultAWSCredentialsProviderChain")
        .set("spark.hadoop.fs.s3a.fast.upload", "true")
        .set("spark.hadoop.fs.s3a.block.size", "134217728") // 128MB
        .set("spark.hadoop.fs.s3a.multipart.size", "67108864") // 64MB
  }
  
  /**
   * Configurações específicas para GCP
   */
  private def configureGCP(conf: SparkConf): Unit = {
    conf.set("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
        .set("spark.hadoop.fs.AbstractFileSystem.gs.impl", 
             "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
        .set("spark.hadoop.google.cloud.auth.service.account.enable", "true")
  }
  
  /**
   * Configurações específicas para Azure
   */
  private def configureAzure(conf: SparkConf): Unit = {
    conf.set("spark.hadoop.fs.azure.account.auth.type", "OAuth")
        .set("spark.hadoop.fs.azure.account.oauth.provider.type", 
             "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider")
  }
  
  /**
   * Parar sessão Spark
   */
  def stopSession(): Unit = {
    sparkSession.foreach { session =>
      Try {
        session.stop()
      } match {
        case Success(_) => println("Spark session stopped successfully")
        case Failure(exception) => println(s"Error stopping Spark session: ${exception.getMessage}")
      }
    }
    sparkSession = None
  }
  
  /**
   * Obter sessão atual
   */
  def getCurrentSession: Option[SparkSession] = sparkSession
  
  /**
   * Verificar se sessão está ativa
   */
  def isSessionActive: Boolean = sparkSession.exists(!_.sparkContext.isStopped)
  
  /**
   * Recriar sessão com novas configurações
   */
  def recreateSession(appName: String = "BigDataPlatform"): SparkSession = {
    stopSession()
    getOrCreateSession(appName)
  }
}
