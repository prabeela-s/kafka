
Our keys are null, so the data is stored in round robin basics on each p0, p1, p2...

```
kafka-topics --create  --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic messages

```

# consume messages

```
kafka-console-producer --broker-list localhost:9092 --topic messages
```
```
kafka-console-consumer --bootstrap-server localhost:9092 --topic messages --partition 0 --from-beginning
```
```
kafka-console-consumer --bootstrap-server localhost:9092 --topic messages --partition 1 --from-beginning
```

Topic, partition with offset

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic messages --partition 2  --offset 2
```

