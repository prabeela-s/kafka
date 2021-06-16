# Avro

-- open source format, apache foundation
-- stores data in binary format
-- uses schema registry to store schemas, version compatablity
-- schema registry, a REST based application, part of Confluent Open Source Kafka
-- Producer shall register the schema first time
-- every message we sent to broker, shall have schema id
-- consumer can get schema from schema registry
-- use schema registry url in both consumer and producer side


Avro saves data size

1. json payload need more size to store and transmit between broker/consumer/producer
2. Storage Space
3. Network IO  transmit between broker/consumer/producer
4. Every time data to be parsed between text and native format

````json
{
  "InvoiceNo": 42343243,
  "Amount": 300,
  "Quantity": 10
  "UnitPrice": 30,
  "CustomerId": 4234656,
  "StockCode": 6456454
}
```

- needed 136 chars, data is stored in bytes, it may need approx 1 char x 2 bytes = 136 chars x 2 = 272 bytes

---
Avro schema/NOT EXACT Number

{
  "InvoiceNo": Int, - 4 bytes
  "Amount": Double,  - 8 bytes
  "Quantity": Int  - 4 bytes
  "UnitPrice": Double, - 8 bytes
  "CustomerId": Int, - 4 bytes
  "StockCode": Int - 4 bytes
}

needed 32 bytes using Avro

```
Refer /lib/README.md file for creating POJO class, downloading jar files...
```


