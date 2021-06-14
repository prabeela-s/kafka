# a message with key and value

producer with key and value pair

when key is present,  Parttion No = hash(key) % NUM_PARITIONS  [producer only decide partitions]

console producer with key:value, where as key is used for partitioning

```
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic billings
```

Enter key value pair using : delimiter
```
key1:value1
USA:100
IN:200
```

key1,IN,USA are keys for the messages, HASH is obtained based on keys

## Given the same key, the data should be land in same partition

```
kafka-console-producer --broker-list localhost:9092 --topic billings --property "parse.key=true" --property "key.separator=:"
```
 
```
kafka-console-consumer --bootstrap-server localhost:9092 --topic billings --partition 0 --from-beginning --property print.key=true

kafka-console-consumer --bootstrap-server localhost:9092 --topic billings --partition 1 --from-beginning --property print.key=true

kafka-console-consumer --bootstrap-server localhost:9092 --topic billings --partition 2 --from-beginning --property print.key=true
```
