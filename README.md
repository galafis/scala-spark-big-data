# 🇧🇷 Big Data com Scala e Apache Spark | 🇺🇸 Big Data with Scala and Apache Spark

<div align="center">

![Scala](https://img.shields.io/badge/Scala-DC322F?style=for-the-badge&logo=scala&logoColor=white)
![Apache Spark](https://img.shields.io/badge/Apache%20Spark-E25A1C?style=for-the-badge&logo=apachespark&logoColor=white)
![Hadoop](https://img.shields.io/badge/Apache%20Hadoop-66CCFF?style=for-the-badge&logo=apachehadoop&logoColor=black)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Delta Lake](https://img.shields.io/badge/Delta%20Lake-00ADD8?style=for-the-badge&logoColor=white)

**Plataforma completa de Big Data com Scala e Apache Spark para processamento distribuído, streaming em tempo real e analytics avançado**

[⚡ Processamento](#-processamento-distribuído) • [🌊 Streaming](#-streaming-em-tempo-real) • [📊 Analytics](#-analytics-avançado) • [🚀 Setup](#-setup-rápido)

</div>

---

## 🇧🇷 Português

### ⚡ Visão Geral

Plataforma abrangente de **Big Data** desenvolvida com Scala e Apache Spark para processamento distribuído de grande escala:

- 🔥 **Apache Spark**: Processamento distribuído em memória para batch e streaming
- 📊 **Analytics Avançado**: Machine Learning, Graph Analytics, SQL distribuído
- 🌊 **Streaming Real-time**: Processamento de streams com Kafka e Kinesis
- 🗄️ **Data Lake**: Arquitetura moderna com Delta Lake e Iceberg
- 🔄 **ETL Escalável**: Pipelines de dados distribuídos e tolerantes a falhas
- 📈 **Monitoramento**: Observabilidade completa com métricas e alertas

### 🎯 Objetivos da Plataforma

- **Processar petabytes** de dados com performance linear
- **Implementar analytics** em tempo real para decisões instantâneas
- **Construir data lakes** modernos e governados
- **Automatizar pipelines** de dados complexos e críticos
- **Democratizar big data** com APIs simples e documentação clara

### 🛠️ Stack Tecnológico

#### Core Big Data
- **Apache Spark 3.5+**: Engine principal para processamento distribuído
- **Scala 2.13**: Linguagem principal para desenvolvimento
- **Apache Hadoop**: Sistema de arquivos distribuído (HDFS)
- **Apache Kafka**: Streaming de dados em tempo real

#### Storage e Data Lake
- **Delta Lake**: Transações ACID em data lakes
- **Apache Iceberg**: Table format para analytics
- **Apache Parquet**: Formato colunar otimizado
- **Apache Avro**: Serialização de dados

#### Cluster e Orquestração
- **Kubernetes**: Orquestração de containers
- **Apache Airflow**: Workflow orchestration
- **YARN**: Resource manager para Hadoop
- **Mesos**: Cluster resource manager

#### Streaming e Messaging
- **Kafka Streams**: Processamento de streams
- **Apache Pulsar**: Sistema de messaging distribuído
- **Amazon Kinesis**: Streaming na AWS
- **Apache Flink**: Stream processing alternativo

#### Machine Learning e Analytics
- **Spark MLlib**: Machine learning distribuído
- **GraphX**: Processamento de grafos
- **Spark SQL**: SQL distribuído
- **Apache Zeppelin**: Notebooks interativos

#### Monitoramento e Observabilidade
- **Prometheus**: Coleta de métricas
- **Grafana**: Visualização de métricas
- **ELK Stack**: Logs centralizados
- **Jaeger**: Distributed tracing

### 📋 Estrutura da Plataforma

```
scala-spark-big-data/
├── 📁 src/                        # Código fonte principal
│   ├── 📁 main/                   # Código principal
│   │   ├── 📁 scala/              # Código Scala
│   │   │   ├── 📁 com/galafis/bigdata/
│   │   │   │   ├── 📁 core/       # Componentes core
│   │   │   │   │   ├── 📄 SparkSessionManager.scala # Gerenciador de sessões
│   │   │   │   │   ├── 📄 ConfigManager.scala # Gerenciamento de configuração
│   │   │   │   │   ├── 📄 DataFrameUtils.scala # Utilitários DataFrame
│   │   │   │   │   └── 📄 PerformanceMonitor.scala # Monitor de performance
│   │   │   │   ├── 📁 etl/        # ETL pipelines
│   │   │   │   │   ├── 📄 DataIngestion.scala # Ingestão de dados
│   │   │   │   │   ├── 📄 DataTransformation.scala # Transformações
│   │   │   │   │   ├── 📄 DataValidation.scala # Validação de dados
│   │   │   │   │   ├── 📄 DataQuality.scala # Qualidade de dados
│   │   │   │   │   └── 📄 DataLineage.scala # Linhagem de dados
│   │   │   │   ├── 📁 streaming/  # Streaming real-time
│   │   │   │   │   ├── 📄 KafkaStreaming.scala # Streaming Kafka
│   │   │   │   │   ├── 📄 KinesisStreaming.scala # Streaming Kinesis
│   │   │   │   │   ├── 📄 StreamProcessor.scala # Processador de streams
│   │   │   │   │   ├── 📄 WindowOperations.scala # Operações de janela
│   │   │   │   │   └── 📄 StateManagement.scala # Gerenciamento de estado
│   │   │   │   ├── 📁 analytics/  # Analytics avançado
│   │   │   │   │   ├── 📄 MLPipelines.scala # Pipelines ML
│   │   │   │   │   ├── 📄 GraphAnalytics.scala # Analytics de grafos
│   │   │   │   │   ├── 📄 TimeSeriesAnalysis.scala # Análise temporal
│   │   │   │   │   ├── 📄 StatisticalAnalysis.scala # Análise estatística
│   │   │   │   │   └── 📄 RecommendationEngine.scala # Motor de recomendação
│   │   │   │   ├── 📁 storage/    # Camada de storage
│   │   │   │   │   ├── 📄 DeltaLakeManager.scala # Gerenciador Delta Lake
│   │   │   │   │   ├── 📄 IcebergManager.scala # Gerenciador Iceberg
│   │   │   │   │   ├── 📄 HiveMetastore.scala # Integração Hive
│   │   │   │   │   ├── 📄 S3Manager.scala # Gerenciador S3
│   │   │   │   │   └── 📄 HDFSManager.scala # Gerenciador HDFS
│   │   │   │   ├── 📁 optimization/ # Otimizações
│   │   │   │   │   ├── 📄 CostBasedOptimizer.scala # Otimizador baseado em custo
│   │   │   │   │   ├── 📄 PartitionOptimizer.scala # Otimizador de partições
│   │   │   │   │   ├── 📄 CacheManager.scala # Gerenciador de cache
│   │   │   │   │   └── 📄 ResourceOptimizer.scala # Otimizador de recursos
│   │   │   │   ├── 📁 security/   # Segurança
│   │   │   │   │   ├── 📄 KerberosAuth.scala # Autenticação Kerberos
│   │   │   │   │   ├── 📄 DataEncryption.scala # Criptografia de dados
│   │   │   │   │   ├── 📄 AccessControl.scala # Controle de acesso
│   │   │   │   │   └── 📄 AuditLogger.scala # Logger de auditoria
│   │   │   │   ├── 📁 monitoring/ # Monitoramento
│   │   │   │   │   ├── 📄 MetricsCollector.scala # Coletor de métricas
│   │   │   │   │   ├── 📄 AlertManager.scala # Gerenciador de alertas
│   │   │   │   │   ├── 📄 HealthChecker.scala # Verificador de saúde
│   │   │   │   │   └── 📄 PerformanceProfiler.scala # Profiler de performance
│   │   │   │   └── 📁 utils/      # Utilitários
│   │   │   │       ├── 📄 DateTimeUtils.scala # Utilitários de data/hora
│   │   │   │       ├── 📄 StringUtils.scala # Utilitários de string
│   │   │   │       ├── 📄 JsonUtils.scala # Utilitários JSON
│   │   │   │       ├── 📄 FileUtils.scala # Utilitários de arquivo
│   │   │   │       └── 📄 NetworkUtils.scala # Utilitários de rede
│   │   │   └── 📁 apps/           # Aplicações específicas
│   │   │       ├── 📄 DataLakeBuilder.scala # Construtor de data lake
│   │   │       ├── 📄 RealtimeAnalytics.scala # Analytics tempo real
│   │   │       ├── 📄 BatchProcessor.scala # Processador batch
│   │   │       ├── 📄 MLTrainingPipeline.scala # Pipeline treinamento ML
│   │   │       └── 📄 DataMigration.scala # Migração de dados
│   │   └── 📁 resources/          # Recursos
│   │       ├── 📄 application.conf # Configuração principal
│   │       ├── 📄 spark-defaults.conf # Configurações Spark
│   │       ├── 📄 log4j2.xml      # Configuração de logs
│   │       └── 📄 reference.conf  # Configurações de referência
│   └── 📁 test/                   # Testes
│       ├── 📁 scala/              # Testes Scala
│       │   ├── 📁 unit/           # Testes unitários
│       │   ├── 📁 integration/    # Testes de integração
│       │   └── 📁 performance/    # Testes de performance
│       └── 📁 resources/          # Recursos de teste
│           ├── 📄 test-data/      # Dados de teste
│           └── 📄 test.conf       # Configurações de teste
├── 📁 scripts/                    # Scripts de deployment
│   ├── 📁 deployment/             # Scripts de deploy
│   │   ├── 📄 deploy-cluster.sh   # Deploy do cluster
│   │   ├── 📄 setup-kafka.sh      # Setup Kafka
│   │   ├── 📄 setup-hadoop.sh     # Setup Hadoop
│   │   └── 📄 setup-monitoring.sh # Setup monitoramento
│   ├── 📁 data-generation/        # Geração de dados
│   │   ├── 📄 generate-sample-data.scala # Gerar dados de exemplo
│   │   ├── 📄 kafka-producer.scala # Produtor Kafka
│   │   └── 📄 data-simulator.scala # Simulador de dados
│   ├── 📁 maintenance/            # Manutenção
│   │   ├── 📄 cleanup-old-data.sh # Limpeza de dados antigos
│   │   ├── 📄 optimize-tables.sh  # Otimização de tabelas
│   │   └── 📄 backup-metadata.sh  # Backup de metadados
│   └── 📁 monitoring/             # Scripts de monitoramento
│       ├── 📄 health-check.sh     # Verificação de saúde
│       ├── 📄 performance-report.sh # Relatório de performance
│       └── 📄 alert-setup.sh      # Setup de alertas
├── 📁 docker/                     # Containers Docker
│   ├── 📄 Dockerfile.spark        # Container Spark
│   ├── 📄 Dockerfile.kafka        # Container Kafka
│   ├── 📄 Dockerfile.hadoop       # Container Hadoop
│   ├── 📄 docker-compose.yml      # Orquestração local
│   └── 📄 docker-compose.prod.yml # Orquestração produção
├── 📁 kubernetes/                 # Manifests Kubernetes
│   ├── 📁 spark/                  # Manifests Spark
│   │   ├── 📄 spark-master.yaml   # Spark master
│   │   ├── 📄 spark-worker.yaml   # Spark workers
│   │   └── 📄 spark-history.yaml  # Spark history server
│   ├── 📁 kafka/                  # Manifests Kafka
│   │   ├── 📄 kafka-cluster.yaml  # Cluster Kafka
│   │   ├── 📄 zookeeper.yaml      # Zookeeper
│   │   └── 📄 kafka-connect.yaml  # Kafka Connect
│   ├── 📁 monitoring/             # Manifests monitoramento
│   │   ├── 📄 prometheus.yaml     # Prometheus
│   │   ├── 📄 grafana.yaml        # Grafana
│   │   └── 📄 alertmanager.yaml   # Alert Manager
│   └── 📁 storage/                # Manifests storage
│       ├── 📄 hdfs-namenode.yaml  # HDFS NameNode
│       ├── 📄 hdfs-datanode.yaml  # HDFS DataNode
│       └── 📄 minio.yaml          # MinIO (S3 compatible)
├── 📁 notebooks/                  # Jupyter/Zeppelin notebooks
│   ├── 📄 01_data_exploration.ipynb # Exploração de dados
│   ├── 📄 02_etl_pipeline.ipynb   # Pipeline ETL
│   ├── 📄 03_streaming_analytics.ipynb # Analytics streaming
│   ├── 📄 04_machine_learning.ipynb # Machine learning
│   ├── 📄 05_graph_analytics.ipynb # Analytics de grafos
│   └── 📄 06_performance_tuning.ipynb # Tuning de performance
├── 📁 data/                       # Dados de exemplo
│   ├── 📁 sample/                 # Dados de exemplo
│   │   ├── 📄 sales_data.parquet  # Dados de vendas
│   │   ├── 📄 customer_data.json  # Dados de clientes
│   │   ├── 📄 product_catalog.csv # Catálogo de produtos
│   │   └── 📄 transaction_log.avro # Log de transações
│   ├── 📁 schemas/                # Esquemas de dados
│   │   ├── 📄 sales_schema.json   # Schema vendas
│   │   ├── 📄 customer_schema.avsc # Schema clientes
│   │   └── 📄 product_schema.sql  # Schema produtos
│   └── 📁 reference/              # Dados de referência
│       ├── 📄 country_codes.csv   # Códigos de país
│       ├── 📄 currency_rates.json # Taxas de câmbio
│       └── 📄 time_zones.parquet  # Fusos horários
├── 📁 docs/                       # Documentação
│   ├── 📁 architecture/           # Documentação de arquitetura
│   │   ├── 📄 system-design.md    # Design do sistema
│   │   ├── 📄 data-flow.md        # Fluxo de dados
│   │   ├── 📄 scalability.md      # Escalabilidade
│   │   └── 📄 security.md         # Segurança
│   ├── 📁 deployment/             # Documentação de deploy
│   │   ├── 📄 cluster-setup.md    # Setup do cluster
│   │   ├── 📄 configuration.md    # Configuração
│   │   ├── 📄 monitoring.md       # Monitoramento
│   │   └── 📄 troubleshooting.md  # Solução de problemas
│   ├── 📁 development/            # Documentação de desenvolvimento
│   │   ├── 📄 coding-standards.md # Padrões de código
│   │   ├── 📄 testing.md          # Testes
│   │   ├── 📄 performance.md      # Performance
│   │   └── 📄 best-practices.md   # Melhores práticas
│   └── 📁 api/                    # Documentação da API
│       ├── 📄 core-api.md         # API core
│       ├── 📄 streaming-api.md    # API streaming
│       └── 📄 analytics-api.md    # API analytics
├── 📁 terraform/                  # Infrastructure as Code
│   ├── 📁 aws/                    # Infraestrutura AWS
│   │   ├── 📄 main.tf             # Configuração principal
│   │   ├── 📄 emr-cluster.tf      # Cluster EMR
│   │   ├── 📄 s3-buckets.tf       # Buckets S3
│   │   └── 📄 kinesis-streams.tf  # Streams Kinesis
│   ├── 📁 gcp/                    # Infraestrutura GCP
│   │   ├── 📄 main.tf             # Configuração principal
│   │   ├── 📄 dataproc-cluster.tf # Cluster Dataproc
│   │   └── 📄 bigquery-datasets.tf # Datasets BigQuery
│   └── 📁 azure/                  # Infraestrutura Azure
│       ├── 📄 main.tf             # Configuração principal
│       ├── 📄 hdinsight-cluster.tf # Cluster HDInsight
│       └── 📄 storage-accounts.tf # Contas de storage
├── 📄 build.sbt                   # Build configuration
├── 📄 project/                    # Configuração do projeto
│   ├── 📄 build.properties        # Versão do SBT
│   ├── 📄 plugins.sbt             # Plugins SBT
│   └── 📄 Dependencies.scala      # Dependências
├── 📄 README.md                   # Este arquivo
├── 📄 LICENSE                     # Licença Apache 2.0
└── 📄 .gitignore                 # Arquivos ignorados
```

### ⚡ Processamento Distribuído

#### 1. 🔥 Core Spark Framework

**Gerenciador de Sessões Spark Otimizado**
```scala
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

/**
 * Utilitários para DataFrames com otimizações avançadas
 */
object DataFrameUtils {
  
  import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
  import org.apache.spark.sql.functions._
  import org.apache.spark.sql.types._
  import org.apache.spark.storage.StorageLevel
  
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
}
```

#### 2. 🌊 Streaming em Tempo Real

**Processador de Streams Kafka Avançado**
```scala
package com.galafis.bigdata.streaming

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming.{StreamingQuery, Trigger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import com.galafis.bigdata.core.SparkSessionManager
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

/**
 * Processador avançado de streams Kafka com gerenciamento de estado e tolerância a falhas
 */
class KafkaStreamProcessor(
  brokers: String,
  topics: Seq[String],
  checkpointLocation: String
) {
  
  private val spark: SparkSession = SparkSessionManager.getOrCreateSession("KafkaStreamProcessor")
  private val config = ConfigFactory.load()
  
  import spark.implicits._
  
  /**
   * Schema para mensagens Kafka
   */
  private val messageSchema = StructType(Seq(
    StructField("timestamp", TimestampType, nullable = false),
    StructField("user_id", StringType, nullable = false),
    StructField("event_type", StringType, nullable = false),
    StructField("properties", MapType(StringType, StringType), nullable = true),
    StructField("session_id", StringType, nullable = true),
    StructField("device_type", StringType, nullable = true)
  ))
  
  /**
   * Criar stream reader para Kafka
   */
  def createKafkaStream(): DataFrame = {
    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("subscribe", topics.mkString(","))
      .option("startingOffsets", "latest")
      .option("failOnDataLoss", "false")
      .option("kafka.session.timeout.ms", "30000")
      .option("kafka.request.timeout.ms", "40000")
      .option("kafka.max.poll.records", "1000")
      .option("kafka.fetch.max.wait.ms", "500")
      .load()
  }
  
  /**
   * Processar mensagens com parsing e validação
   */
  def processMessages(rawStream: DataFrame): DataFrame = {
    rawStream
      .select(
        col("key").cast("string").as("message_key"),
        col("value").cast("string").as("message_value"),
        col("topic"),
        col("partition"),
        col("offset"),
        col("timestamp").as("kafka_timestamp")
      )
      .filter(col("message_value").isNotNull)
      .withColumn("parsed_message", from_json(col("message_value"), messageSchema))
      .select(
        col("message_key"),
        col("topic"),
        col("partition"),
        col("offset"),
        col("kafka_timestamp"),
        col("parsed_message.*")
      )
      .filter(col("timestamp").isNotNull && col("user_id").isNotNull)
  }
  
  /**
   * Agregações em janelas de tempo
   */
  def createWindowedAggregations(processedStream: DataFrame): DataFrame = {
    processedStream
      .withWatermark("timestamp", "10 minutes")
      .groupBy(
        window(col("timestamp"), "5 minutes", "1 minute"),
        col("event_type"),
        col("device_type")
      )
      .agg(
        count("*").as("event_count"),
        countDistinct("user_id").as("unique_users"),
        countDistinct("session_id").as("unique_sessions"),
        avg(size(col("properties"))).as("avg_properties_count")
      )
      .select(
        col("window.start").as("window_start"),
        col("window.end").as("window_end"),
        col("event_type"),
        col("device_type"),
        col("event_count"),
        col("unique_users"),
        col("unique_sessions"),
        col("avg_properties_count")
      )
  }
  
  /**
   * Detecção de anomalias em tempo real
   */
  def detectAnomalies(aggregatedStream: DataFrame): DataFrame = {
    // Usar estatísticas móveis para detectar anomalias
    aggregatedStream
      .withColumn("event_count_zscore", 
        (col("event_count") - avg("event_count").over(
          org.apache.spark.sql.expressions.Window
            .partitionBy("event_type", "device_type")
            .orderBy("window_start")
            .rowsBetween(-10, 0)
        )) / stddev("event_count").over(
          org.apache.spark.sql.expressions.Window
            .partitionBy("event_type", "device_type")
            .orderBy("window_start")
            .rowsBetween(-10, 0)
        )
      )
      .withColumn("is_anomaly", 
        when(abs(col("event_count_zscore")) > 3.0, true).otherwise(false)
      )
      .filter(col("is_anomaly") === true)
  }
  
  /**
   * Processamento de sessões de usuário
   */
  def processUserSessions(processedStream: DataFrame): DataFrame = {
    processedStream
      .withWatermark("timestamp", "30 minutes")
      .groupBy(
        col("user_id"),
        col("session_id"),
        window(col("timestamp"), "30 minutes")
      )
      .agg(
        min("timestamp").as("session_start"),
        max("timestamp").as("session_end"),
        count("*").as("events_in_session"),
        countDistinct("event_type").as("unique_event_types"),
        collect_list("event_type").as("event_sequence")
      )
      .withColumn("session_duration_minutes",
        (unix_timestamp(col("session_end")) - unix_timestamp(col("session_start"))) / 60
      )
      .filter(col("session_duration_minutes") > 0)
  }
  
  /**
   * Sink para Delta Lake com particionamento otimizado
   */
  def writeToDeltaLake(
    stream: DataFrame,
    outputPath: String,
    partitionColumns: Seq[String] = Seq("event_type")
  ): StreamingQuery = {
    
    val writer = stream.writeStream
      .format("delta")
      .outputMode("append")
      .option("checkpointLocation", s"$checkpointLocation/delta")
      .option("path", outputPath)
      .trigger(Trigger.ProcessingTime(30.seconds))
    
    if (partitionColumns.nonEmpty) {
      writer.partitionBy(partitionColumns: _*)
    }
    
    writer.start()
  }
  
  /**
   * Sink para Kafka (para downstream processing)
   */
  def writeToKafka(
    stream: DataFrame,
    outputTopic: String,
    keyColumn: String = "user_id"
  ): StreamingQuery = {
    
    stream
      .select(
        col(keyColumn).as("key"),
        to_json(struct(col("*"))).as("value")
      )
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("topic", outputTopic)
      .option("checkpointLocation", s"$checkpointLocation/kafka")
      .trigger(Trigger.ProcessingTime(10.seconds))
      .start()
  }
  
  /**
   * Sink para console (para debugging)
   */
  def writeToConsole(stream: DataFrame, numRows: Int = 20): StreamingQuery = {
    stream.writeStream
      .format("console")
      .option("numRows", numRows)
      .option("truncate", false)
      .trigger(Trigger.ProcessingTime(30.seconds))
      .start()
  }
  
  /**
   * Pipeline completo de processamento
   */
  def runCompleteProcessingPipeline(): Map[String, StreamingQuery] = {
    // Stream principal
    val rawStream = createKafkaStream()
    val processedStream = processMessages(rawStream)
    
    // Agregações em janelas
    val windowedAggregations = createWindowedAggregations(processedStream)
    
    // Detecção de anomalias
    val anomalies = detectAnomalies(windowedAggregations)
    
    // Sessões de usuário
    val userSessions = processUserSessions(processedStream)
    
    // Múltiplos sinks
    val queries = Map(
      "raw_events" -> writeToDeltaLake(
        processedStream, 
        config.getString("output.raw_events_path"),
        Seq("event_type", "device_type")
      ),
      "aggregations" -> writeToDeltaLake(
        windowedAggregations,
        config.getString("output.aggregations_path"),
        Seq("event_type")
      ),
      "anomalies" -> writeToKafka(anomalies, "anomaly-alerts", "event_type"),
      "user_sessions" -> writeToDeltaLake(
        userSessions,
        config.getString("output.sessions_path"),
        Seq("user_id")
      )
    )
    
    // Monitoramento
    queries.foreach { case (name, query) =>
      println(s"Started streaming query: $name")
      query.awaitTermination(1000) // Check status
    }
    
    queries
  }
  
  /**
   * Parar todas as queries
   */
  def stopAllQueries(queries: Map[String, StreamingQuery]): Unit = {
    queries.foreach { case (name, query) =>
      if (query.isActive) {
        println(s"Stopping query: $name")
        query.stop()
      }
    }
  }
}

/**
 * Gerenciamento de estado para streams
 */
object StateManagement {
  
  import org.apache.spark.sql.streaming.GroupState
  import org.apache.spark.sql.streaming.GroupStateTimeout
  
  /**
   * Estado de sessão de usuário
   */
  case class UserSessionState(
    userId: String,
    sessionId: String,
    startTime: Long,
    lastEventTime: Long,
    eventCount: Int,
    eventTypes: Set[String]
  )
  
  /**
   * Evento de entrada
   */
  case class UserEvent(
    userId: String,
    sessionId: String,
    eventType: String,
    timestamp: Long,
    properties: Map[String, String]
  )
  
  /**
   * Função de atualização de estado para sessões
   */
  def updateUserSessionState(
    key: String,
    events: Iterator[UserEvent],
    state: GroupState[UserSessionState]
  ): Iterator[UserSessionState] = {
    
    val currentState = state.getOption.getOrElse(
      UserSessionState(key, "", 0L, 0L, 0, Set.empty)
    )
    
    val eventsList = events.toList
    if (eventsList.isEmpty) {
      // Timeout - retornar estado final
      state.remove()
      Iterator(currentState)
    } else {
      // Atualizar estado com novos eventos
      val latestEvent = eventsList.maxBy(_.timestamp)
      val allEventTypes = currentState.eventTypes ++ eventsList.map(_.eventType).toSet
      
      val newState = currentState.copy(
        sessionId = latestEvent.sessionId,
        startTime = if (currentState.startTime == 0L) latestEvent.timestamp else currentState.startTime,
        lastEventTime = latestEvent.timestamp,
        eventCount = currentState.eventCount + eventsList.size,
        eventTypes = allEventTypes
      )
      
      // Configurar timeout para 30 minutos de inatividade
      state.setTimeoutDuration("30 minutes")
      state.update(newState)
      
      Iterator(newState)
    }
  }
}
```

#### 3. 📊 Analytics Avançado

**Engine de Machine Learning Distribuído**
```scala
package com.galafis.bigdata.analytics

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.feature._
import org.apache.spark.ml.classification._
import org.apache.spark.ml.regression._
import org.apache.spark.ml.clustering._
import org.apache.spark.ml.recommendation._
import org.apache.spark.ml.evaluation._
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import com.galafis.bigdata.core.SparkSessionManager
import scala.util.{Try, Success, Failure}

/**
 * Engine de Machine Learning distribuído com pipelines automatizados
 */
class MLPipelineEngine(spark: SparkSession = SparkSessionManager.getOrCreateSession()) {
  
  import spark.implicits._
  
  /**
   * Pipeline de classificação automatizado
   */
  def createClassificationPipeline(
    data: DataFrame,
    labelCol: String,
    featureCols: Seq[String],
    algorithmType: String = "randomforest"
  ): (PipelineModel, Map[String, Double]) = {
    
    // Preparação de features
    val featurePreprocessor = createFeaturePreprocessor(data, featureCols)
    
    // Seletor de algoritmo
    val classifier = algorithmType.toLowerCase match {
      case "randomforest" => new RandomForestClassifier()
        .setLabelCol(labelCol)
        .setFeaturesCol("features")
        .setNumTrees(100)
        .setMaxDepth(10)
        .setSubsamplingRate(0.8)
        
      case "gradientboosting" => new GBTClassifier()
        .setLabelCol(labelCol)
        .setFeaturesCol("features")
        .setMaxIter(100)
        .setMaxDepth(8)
        .setStepSize(0.1)
        
      case "logisticregression" => new LogisticRegression()
        .setLabelCol(labelCol)
        .setFeaturesCol("features")
        .setMaxIter(100)
        .setRegParam(0.01)
        .setElasticNetParam(0.5)
        
      case "naivebayes" => new NaiveBayes()
        .setLabelCol(labelCol)
        .setFeaturesCol("features")
        .setSmoothing(1.0)
        
      case _ => throw new IllegalArgumentException(s"Unsupported algorithm: $algorithmType")
    }
    
    // Pipeline completo
    val pipeline = new Pipeline()
      .setStages(featurePreprocessor :+ classifier)
    
    // Split treino/teste
    val Array(trainData, testData) = data.randomSplit(Array(0.8, 0.2), seed = 42)
    
    // Treinar modelo
    val model = pipeline.fit(trainData)
    
    // Avaliar modelo
    val predictions = model.transform(testData)
    val metrics = evaluateClassificationModel(predictions, labelCol)
    
    (model, metrics)
  }
  
  /**
   * Pipeline de regressão automatizado
   */
  def createRegressionPipeline(
    data: DataFrame,
    labelCol: String,
    featureCols: Seq[String],
    algorithmType: String = "randomforest"
  ): (PipelineModel, Map[String, Double]) = {
    
    val featurePreprocessor = createFeaturePreprocessor(data, featureCols)
    
    val regressor = algorithmType.toLowerCase match {
      case "randomforest" => new RandomForestRegressor()
        .setLabelCol(labelCol)
        .setFeaturesCol("features")
        .setNumTrees(100)
        .setMaxDepth(10)
        
      case "gradientboosting" => new GBTRegressor()
        .setLabelCol(labelCol)
        .setFeaturesCol("features")
        .setMaxIter(100)
        .setMaxDepth(8)
        .setStepSize(0.1)
        
      case "linearregression" => new LinearRegression()
        .setLabelCol(labelCol)
        .setFeaturesCol("features")
        .setMaxIter(100)
        .setRegParam(0.01)
        .setElasticNetParam(0.5)
        
      case _ => throw new IllegalArgumentException(s"Unsupported algorithm: $algorithmType")
    }
    
    val pipeline = new Pipeline().setStages(featurePreprocessor :+ regressor)
    
    val Array(trainData, testData) = data.randomSplit(Array(0.8, 0.2), seed = 42)
    val model = pipeline.fit(trainData)
    val predictions = model.transform(testData)
    val metrics = evaluateRegressionModel(predictions, labelCol)
    
    (model, metrics)
  }
  
  /**
   * Pipeline de clustering automatizado
   */
  def createClusteringPipeline(
    data: DataFrame,
    featureCols: Seq[String],
    numClusters: Int = 5,
    algorithmType: String = "kmeans"
  ): (PipelineModel, Map[String, Double]) = {
    
    val featurePreprocessor = createFeaturePreprocessor(data, featureCols)
    
    val clusterer = algorithmType.toLowerCase match {
      case "kmeans" => new KMeans()
        .setFeaturesCol("features")
        .setPredictionCol("cluster")
        .setK(numClusters)
        .setMaxIter(100)
        .setSeed(42)
        
      case "gaussianmixture" => new GaussianMixture()
        .setFeaturesCol("features")
        .setPredictionCol("cluster")
        .setK(numClusters)
        .setMaxIter(100)
        .setSeed(42)
        
      case "bisectingkmeans" => new BisectingKMeans()
        .setFeaturesCol("features")
        .setPredictionCol("cluster")
        .setK(numClusters)
        .setMaxIter(100)
        .setSeed(42)
        
      case _ => throw new IllegalArgumentException(s"Unsupported algorithm: $algorithmType")
    }
    
    val pipeline = new Pipeline().setStages(featurePreprocessor :+ clusterer)
    val model = pipeline.fit(data)
    val predictions = model.transform(data)
    val metrics = evaluateClusteringModel(predictions)
    
    (model, metrics)
  }
  
  /**
   * Sistema de recomendação com ALS
   */
  def createRecommendationSystem(
    data: DataFrame,
    userCol: String,
    itemCol: String,
    ratingCol: String
  ): (ALSModel, Map[String, Double]) = {
    
    // Converter IDs para numéricos se necessário
    val userIndexer = new StringIndexer()
      .setInputCol(userCol)
      .setOutputCol("userIndex")
      .setHandleInvalid("skip")
    
    val itemIndexer = new StringIndexer()
      .setInputCol(itemCol)
      .setOutputCol("itemIndex")
      .setHandleInvalid("skip")
    
    val indexedData = itemIndexer.fit(userIndexer.fit(data).transform(data)).transform(
      userIndexer.fit(data).transform(data)
    )
    
    // Modelo ALS
    val als = new ALS()
      .setUserCol("userIndex")
      .setItemCol("itemIndex")
      .setRatingCol(ratingCol)
      .setRank(50)
      .setMaxIter(20)
      .setRegParam(0.1)
      .setAlpha(1.0)
      .setColdStartStrategy("drop")
      .setSeed(42)
    
    val Array(trainData, testData) = indexedData.randomSplit(Array(0.8, 0.2), seed = 42)
    val model = als.fit(trainData)
    
    // Avaliar modelo
    val predictions = model.transform(testData)
    val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol(ratingCol)
      .setPredictionCol("prediction")
    
    val rmse = evaluator.evaluate(predictions.filter(!col("prediction").isNaN))
    val metrics = Map("rmse" -> rmse)
    
    (model, metrics)
  }
  
  /**
   * Criar preprocessador de features
   */
  private def createFeaturePreprocessor(
    data: DataFrame,
    featureCols: Seq[String]
  ): Array[org.apache.spark.ml.PipelineStage] = {
    
    val schema = data.schema
    val stages = scala.collection.mutable.ArrayBuffer[org.apache.spark.ml.PipelineStage]()
    
    // Separar colunas por tipo
    val numericCols = featureCols.filter { col =>
      schema(col).dataType match {
        case _: NumericType => true
        case _ => false
      }
    }
    
    val stringCols = featureCols.filter { col =>
      schema(col).dataType match {
        case StringType => true
        case _ => false
      }
    }
    
    // Indexar colunas categóricas
    val indexedStringCols = stringCols.map { col =>
      val indexer = new StringIndexer()
        .setInputCol(col)
        .setOutputCol(s"${col}_indexed")
        .setHandleInvalid("skip")
      stages += indexer
      s"${col}_indexed"
    }
    
    // One-hot encoding para categóricas
    val encodedStringCols = indexedStringCols.map { col =>
      val encoder = new OneHotEncoder()
        .setInputCol(col)
        .setOutputCol(s"${col}_encoded")
      stages += encoder
      s"${col}_encoded"
    }
    
    // Imputar valores faltantes em colunas numéricas
    val imputedNumericCols = if (numericCols.nonEmpty) {
      val imputer = new Imputer()
        .setInputCols(numericCols.toArray)
        .setOutputCols(numericCols.map(_ + "_imputed").toArray)
        .setStrategy("mean")
      stages += imputer
      numericCols.map(_ + "_imputed")
    } else {
      numericCols
    }
    
    // Normalizar features numéricas
    val scaledNumericCols = if (imputedNumericCols.nonEmpty) {
      val scaler = new StandardScaler()
        .setInputCol("numeric_features")
        .setOutputCol("scaled_numeric_features")
        .setWithStd(true)
        .setWithMean(true)
      
      val numericAssembler = new VectorAssembler()
        .setInputCols(imputedNumericCols.toArray)
        .setOutputCol("numeric_features")
      
      stages += numericAssembler
      stages += scaler
      Seq("scaled_numeric_features")
    } else {
      Seq.empty
    }
    
    // Combinar todas as features
    val allFeatureCols = scaledNumericCols ++ encodedStringCols
    val finalAssembler = new VectorAssembler()
      .setInputCols(allFeatureCols.toArray)
      .setOutputCol("features")
    
    stages += finalAssembler
    stages.toArray
  }
  
  /**
   * Avaliar modelo de classificação
   */
  private def evaluateClassificationModel(
    predictions: DataFrame,
    labelCol: String
  ): Map[String, Double] = {
    
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol(labelCol)
      .setPredictionCol("prediction")
    
    val accuracy = evaluator.setMetricName("accuracy").evaluate(predictions)
    val f1 = evaluator.setMetricName("f1").evaluate(predictions)
    val precision = evaluator.setMetricName("weightedPrecision").evaluate(predictions)
    val recall = evaluator.setMetricName("weightedRecall").evaluate(predictions)
    
    Map(
      "accuracy" -> accuracy,
      "f1" -> f1,
      "precision" -> precision,
      "recall" -> recall
    )
  }
  
  /**
   * Avaliar modelo de regressão
   */
  private def evaluateRegressionModel(
    predictions: DataFrame,
    labelCol: String
  ): Map[String, Double] = {
    
    val evaluator = new RegressionEvaluator()
      .setLabelCol(labelCol)
      .setPredictionCol("prediction")
    
    val rmse = evaluator.setMetricName("rmse").evaluate(predictions)
    val mae = evaluator.setMetricName("mae").evaluate(predictions)
    val r2 = evaluator.setMetricName("r2").evaluate(predictions)
    
    Map(
      "rmse" -> rmse,
      "mae" -> mae,
      "r2" -> r2
    )
  }
  
  /**
   * Avaliar modelo de clustering
   */
  private def evaluateClusteringModel(predictions: DataFrame): Map[String, Double] = {
    val evaluator = new ClusteringEvaluator()
      .setFeaturesCol("features")
      .setPredictionCol("cluster")
    
    val silhouette = evaluator.evaluate(predictions)
    
    Map("silhouette" -> silhouette)
  }
  
  /**
   * Hyperparameter tuning com cross-validation
   */
  def hyperparameterTuning(
    data: DataFrame,
    pipeline: Pipeline,
    paramGrid: Array[org.apache.spark.ml.param.ParamMap],
    evaluator: org.apache.spark.ml.evaluation.Evaluator,
    numFolds: Int = 3
  ): PipelineModel = {
    
    val cv = new CrossValidator()
      .setEstimator(pipeline)
      .setEvaluator(evaluator)
      .setEstimatorParamMaps(paramGrid)
      .setNumFolds(numFolds)
      .setParallelism(4)
      .setSeed(42)
    
    cv.fit(data)
  }
}
```

### 🎯 Competências Demonstradas

#### Big Data e Spark
- ✅ **Apache Spark**: Processamento distribuído em memória
- ✅ **Scala Avançado**: Programação funcional e orientada a objetos
- ✅ **Streaming Real-time**: Kafka, Kinesis, processamento de streams
- ✅ **Data Lake**: Delta Lake, Iceberg, arquiteturas modernas

#### Machine Learning Distribuído
- ✅ **MLlib**: Machine learning escalável
- ✅ **Pipelines Automatizados**: Feature engineering, model training
- ✅ **Hyperparameter Tuning**: Cross-validation, grid search
- ✅ **Sistemas de Recomendação**: ALS, collaborative filtering

#### Infraestrutura e DevOps
- ✅ **Kubernetes**: Orquestração de containers
- ✅ **Docker**: Containerização de aplicações
- ✅ **Terraform**: Infrastructure as Code
- ✅ **Monitoramento**: Prometheus, Grafana, observabilidade

---

## 🇺🇸 English

### ⚡ Overview

Comprehensive **Big Data** platform developed with Scala and Apache Spark for large-scale distributed processing:

- 🔥 **Apache Spark**: In-memory distributed processing for batch and streaming
- 📊 **Advanced Analytics**: Machine Learning, Graph Analytics, distributed SQL
- 🌊 **Real-time Streaming**: Stream processing with Kafka and Kinesis
- 🗄️ **Data Lake**: Modern architecture with Delta Lake and Iceberg
- 🔄 **Scalable ETL**: Distributed and fault-tolerant data pipelines
- 📈 **Monitoring**: Complete observability with metrics and alerts

### 🎯 Platform Objectives

- **Process petabytes** of data with linear performance
- **Implement analytics** in real-time for instant decisions
- **Build data lakes** modern and governed
- **Automate pipelines** complex and critical data
- **Democratize big data** with simple APIs and clear documentation

### ⚡ Distributed Processing

#### 1. 🔥 Core Spark Framework
- Optimized Spark session manager
- Advanced DataFrame utilities
- Smart caching and partitioning
- Performance monitoring and optimization

#### 2. 🌊 Real-time Streaming
- Advanced Kafka stream processor
- State management for streams
- Windowed aggregations
- Real-time anomaly detection

#### 3. 📊 Advanced Analytics
- Distributed machine learning engine
- Automated ML pipelines
- Recommendation systems
- Hyperparameter tuning

### 🎯 Skills Demonstrated

#### Big Data and Spark
- ✅ **Apache Spark**: In-memory distributed processing
- ✅ **Advanced Scala**: Functional and object-oriented programming
- ✅ **Real-time Streaming**: Kafka, Kinesis, stream processing
- ✅ **Data Lake**: Delta Lake, Iceberg, modern architectures

#### Distributed Machine Learning
- ✅ **MLlib**: Scalable machine learning
- ✅ **Automated Pipelines**: Feature engineering, model training
- ✅ **Hyperparameter Tuning**: Cross-validation, grid search
- ✅ **Recommendation Systems**: ALS, collaborative filtering

#### Infrastructure and DevOps
- ✅ **Kubernetes**: Container orchestration
- ✅ **Docker**: Application containerization
- ✅ **Terraform**: Infrastructure as Code
- ✅ **Monitoring**: Prometheus, Grafana, observability

---

## 📄 Licença | License

Apache License 2.0 - veja o arquivo [LICENSE](LICENSE) para detalhes | see [LICENSE](LICENSE) file for details

## 📞 Contato | Contact

**GitHub**: [@galafis](https://github.com/galafis)  
**LinkedIn**: [Gabriel Demetrios Lafis](https://linkedin.com/in/galafis)  
**Email**: gabriel.lafis@example.com

---

<div align="center">

**Desenvolvido com ❤️ para Big Data | Developed with ❤️ for Big Data**

[![GitHub](https://img.shields.io/badge/GitHub-galafis-blue?style=flat-square&logo=github)](https://github.com/galafis)
[![Scala](https://img.shields.io/badge/Scala-DC322F?style=flat-square&logo=scala&logoColor=white)](https://www.scala-lang.org/)

</div>

