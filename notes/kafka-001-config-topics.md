# Kafka config command alter partition, segment bytes

```
kafka-topics  --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic my-topic
```

```
kafka-topics --list --bootstrap-server localhost:9092
```

```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic my-topic
```

```
kafka-topics  --alter --bootstrap-server localhost:9092   --partitions 3 --topic my-topic
```

```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic my-topic
```

```
kafka-configs --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name my-topic --add-config retention.ms=3600000
```

```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic my-topic
```

```
kafka-configs --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name my-topic --add-config segment.bytes=214748364
```

```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic my-topic
```


```
kafka-topics --delete  --bootstrap-server localhost:9092  --topic my-topic
```
