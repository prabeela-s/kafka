```
kafka-topics  --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic simple.elasticsearch.data
```

```
curl -X POST http://localhost:8083/connectors -H "Content-Type: application/json" -d '{
 "name": "simple-elasticsearch-connector",
 "config": {
   "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
   "connection.url": "http://elasticsearch:9200",
   "tasks.max": "1",
   "topics": "simple.elasticsearch.data",
   "type.name": "_doc",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
 "value.converter.schemas.enable": "false",
 "schema.ignore": "true",
 "key.ignore": "true"
 }
}'

```

Product message to topic 

```
kafkacat -P -b localhost:9092 -t simple.elasticsearch.data
```

```
hello

hi
```


```
curl localhost:9200/simple.elasticsearch.data/_search | jq
```
