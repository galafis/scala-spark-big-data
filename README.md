# Scala Spark Big Data Platform

![Hero Image](images/hero_image.jpg)

## 🇧🇷 Português

Este repositório apresenta uma plataforma completa de Big Data desenvolvida com Scala e Apache Spark. O objetivo é demonstrar a construção de soluções robustas para processamento, análise e monitoramento de grandes volumes de dados.

### Funcionalidades Principais

*   **Processamento Batch:** ETL de dados transacionais e detecção de fraude.
*   **Processamento Streaming:** Análise em tempo real de fluxos de dados.
*   **Análise Avançada:** Segmentação de clientes, análise de séries temporais e análise de cesta de mercado.
*   **Monitoramento:** Coleta de métricas e sistema de alertas.
*   **Infraestrutura:** Suporte para Delta Lake, Iceberg, HDFS, S3, Kubernetes e Terraform.

### Estrutura do Projeto

```
. (raiz do repositório)
├── src/                # Código fonte principal (Scala)
├── tests/              # Testes unitários e de integração
├── docs/               # Documentação e diagramas
├── config/             # Arquivos de configuração
├── data/               # Dados de exemplo
├── docker/             # Configurações Docker
├── kubernetes/         # Configurações Kubernetes
├── notebooks/          # Notebooks de exploração e prototipagem
├── scripts/            # Scripts de automação e deploy
├── terraform/          # Infraestrutura como Código (Terraform)
├── images/             # Imagens e assets visuais
├── build.sbt           # Configuração do projeto SBT
└── README.md           # Este arquivo
```

### Como Usar

1.  **Clonar o Repositório:**
    ```bash
    git clone https://github.com/galafis/scala-spark-big-data.git
    cd scala-spark-big-data
    ```
2.  **Compilar o Projeto:**
    ```bash
    sbt clean compile
    ```
3.  **Executar Aplicações:**
    ```bash
    # Modo Batch
    sbt "run batch"

    # Modo Streaming
    sbt "run streaming"

    # Modo Analytics
    sbt "run analytics"
    ```

### Diagramas de Arquitetura

![System Architecture](images/system-architecture.png)



## 🇬🇧 English

This repository presents a comprehensive Big Data platform developed with Scala and Apache Spark. The goal is to demonstrate the construction of robust solutions for processing, analyzing, and monitoring large volumes of data.

### Key Features

*   **Batch Processing:** ETL for transactional data and fraud detection.
*   **Streaming Processing:** Real-time analysis of data streams.
*   **Advanced Analytics:** Customer segmentation, time series analysis, and market basket analysis.
*   **Monitoring:** Metrics collection and alerting system.
*   **Infrastructure:** Support for Delta Lake, Iceberg, HDFS, S3, Kubernetes, and Terraform.

### Project Structure

```
. (repository root)
├── src/                # Main source code (Scala)
├── tests/              # Unit and integration tests
├── docs/               # Documentation and diagrams
├── config/             # Configuration files
├── data/               # Sample data
├── docker/             # Docker configurations
├── kubernetes/         # Kubernetes configurations
├── notebooks/          # Exploration and prototyping notebooks
├── scripts/            # Automation and deployment scripts
├── terraform/          # Infrastructure as Code (Terraform)
├── images/             # Images and visual assets
├── build.sbt           # SBT project configuration
└── README.md           # This file
```

### How to Use

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/galafis/scala-spark-big-data.git
    cd scala-spark-big-data
    ```
2.  **Compile the Project:**
    ```bash
    sbt clean compile
    ```
3.  **Run Applications:**
    ```bash
    # Batch Mode
    sbt "run batch"

    # Streaming Mode
    sbt "run streaming"

    # Analytics Mode
    sbt "run analytics"
    ```

### Architecture Diagrams

![System Architecture](images/system-architecture.png)



