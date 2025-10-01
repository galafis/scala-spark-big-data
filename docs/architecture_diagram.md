```mermaid
graph TD
    A[Fonte de Dados]
    B(Ingestão de Dados)
    C{Processamento ETL}
    D[Data Lake (Delta Lake)]
    E(Processamento em Lote)
    F(Processamento em Streaming)
    G[Modelos de ML & Análises]
    H[Detecção de Anomalias em Tempo Real]
    I[Resultados & Dashboards]
    J[Usuários/Aplicações]

    A --> B
    B --> C
    C --> D
    D --> E
    D --> F
    E --> G
    F --> H
    G --> I
    H --> I
    I --> J

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

    subgraph Aplicação Scala
        A
        B
        I
        J
    end
```
