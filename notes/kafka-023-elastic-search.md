# Elastic Search

```
touch elasticsearch-sink.json

nano elasticsearch-sink.json
```

paste below

```
{
 "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
 "tasks.max": "1",
 "topics": "products",
 "name": "simple-elasticsearch-connector",
 "connection.url": "http://localhost:9200",
 "type.name": "_doc"
}
```

```
confluent local load elasticsearch-sink -- -d elasticsearch-sink.json

confluent local status elasticsearch-sink
```
