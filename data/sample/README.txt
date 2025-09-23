# Sales Data Sample Format

Este diretório deve conter arquivos de exemplo no formato Parquet.

## Formato esperado para sales_data.parquet:

- **id**: Long - Identificador único da venda
- **product_name**: String - Nome do produto
- **category**: String - Categoria do produto 
- **price**: Double - Preço unitário
- **quantity**: Integer - Quantidade vendida
- **sale_date**: Date - Data da venda
- **customer_id**: Long - ID do cliente
- **region**: String - Região da venda
- **total_amount**: Double - Valor total (price * quantity)

## Exemplo de estrutura:
```
id | product_name | category | price | quantity | sale_date  | customer_id | region | total_amount
1  | Laptop      | Tech     | 999.99| 1        | 2025-01-01 | 123        | North  | 999.99
2  | Mouse       | Tech     | 25.50 | 2        | 2025-01-01 | 124        | South  | 51.00
```

Os dados devem estar no formato Parquet para otimização de performance com Spark.
