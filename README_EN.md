# Scala Spark Big Data

![Hero Image](images/hero_image.jpg)

## 🇬🇧 English

[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Scala](https://img.shields.io/badge/Scala-2.12-blue.svg)](https://www.scala-lang.org/)
[![Apache Spark](https://img.shields.io/badge/Apache_Spark-3.3.0-orange.svg)](https://spark.apache.org/)
[![sbt](https://img.shields.io/badge/sbt-1.x-green.svg)](https://www.scala-sbt.org/)

This repository presents a Big Data processing project using Scala and Apache Spark. The project demonstrates the implementation of ETL pipelines, batch and real-time data analysis, and the construction of machine learning models for fraud detection and customer segmentation.

### Features

*   **Batch Processing**: Ingestion, transformation, and loading of large volumes of transactional data.
*   **Streaming Processing**: Analysis of data streams for real-time anomaly detection and aggregations.
*   **Data Analysis & Machine Learning**: Implementation of machine learning algorithms for fraud detection, customer segmentation, time series analysis, and market basket analysis.
*   **Modular Structure**: Code organized into modules for easier maintenance and scalability.
*   **Flexible Configuration**: Use of configuration files to manage application and Spark parameters.

### Technologies Used

*   **Scala**: Main programming language.
*   **Apache Spark**: Unified framework for Big Data processing.
*   **Delta Lake**: Data storage layer for data lakes.
*   **sbt**: Build tool for Scala projects.
*   **Apache Hadoop (AWS S3)**: Integration with cloud storage.
*   **Logback**: Logging system.
*   **ScalaTest & Mockito**: Frameworks for unit testing.

### Project Structure

```
scala-spark-big-data/
├── src/
│   ├── main/
│   │   └── scala/com/galafis/bigdata/
│   │       ├── analytics/        # Analytics and ML logic
│   │       ├── apps/             # Main applications
│   │       ├── core/             # Core components
│   │       ├── etl/              # Extraction, Transformation, and Loading pipelines
│   │       ├── models/           # Data model definitions
│   │       ├── monitoring/       # Monitoring tools
│   │       ├── storage/          # Storage abstraction layers
│   │       ├── streaming/        # Streaming processing logic
│   │       └── utils/            # Utility functions
├── tests/
│   ├── unit/             # Unit tests
│   └── integration/      # Integration tests
├── config/               # Application configuration files
├── data/                 # Sample or mock data
├── docs/                 # Additional documentation, diagrams
├── images/               # Images and visual assets
├── notebooks/            # Jupyter/Zeppelin notebooks for exploration
├── project/              # sbt configurations
├── build.sbt             # sbt build definitions
├── README.md             # Portuguese README
├── README_EN.md          # This file
├── LICENSE               # Project license
└── CONTRIBUTING.md       # Contribution guide
```

### How to Use

#### Prerequisites

*   Java Development Kit (JDK) 8 or higher
*   sbt (Scala Build Tool)
*   Apache Spark (for cluster execution, optional for `local[*]`) 

#### Building the Project

To build the project, navigate to the root directory and run:

```bash
sbt clean compile
```

#### Running Tests

To run unit and integration tests:

```bash
sbt test
```

To generate a code coverage report:

```bash
sbt coverageReport
```

#### Running the Application

The project can be run in different modes: `batch`, `streaming`, or `analytics`.

**Batch Mode:**

```bash
sbt "run batch"
```

**Streaming Mode:**

```bash
sbt "run streaming"
```

**Analytics Mode:**

```bash
sbt "run analytics"
```

### Architecture Diagram

```mermaid
graph TD
    A[Data Source] --> B(Data Ingestion)
    B --> C{ETL Processing}
    C --> D[Data Lake (Delta Lake)]
    D --> E(Batch Processing)
    D --> F(Streaming Processing)
    E --> G[ML Models & Analytics]
    F --> H[Real-time Anomaly Detection]
    G --> I[Results & Dashboards]
    H --> I
    I --> J[Users/Applications]

    subgraph Apache Spark
        C
        E
        F
        G
        H
    end

    subgraph AWS S3
        D
    end

    subgraph Scala Application
        A
        B
        I
        J
    end
```

### License

This project is licensed under the MIT License - see the `LICENSE` file for details.

### Author

**Gabriel Demetrios Lafis**

*   [GitHub](https://github.com/galafis)
*   [LinkedIn](https://www.linkedin.com/in/gabriel-demetrios-lafis/)

