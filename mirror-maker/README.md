```
    kafka-topics  --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

```
    kafka-topics  --create --zookeeper localhost:2281 --replication-factor 1 --partitions 1 --topic test
```

source side

```
kafka-console-producer --broker-list localhost:9092 --topic test
```

remote dc

```
kafka-console-consumer --bootstrap-server localhost:9098 --topic test
```
