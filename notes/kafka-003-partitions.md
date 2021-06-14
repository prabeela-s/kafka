
Our keys are null, so the data is stored in round robin basics on each p0, p1, p2...

```
kafka-topics --create  --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic messages

```

```
Do it yourself
Check kafka describe command for messages topics
check c:/tmp fodler, see how kafka created folder/partitions for messages topic
```

```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic messages
```

## Partition

Kafka topic messages are stored into paritions, paritition is subset of whole data in the topic without duplication.

```
messages: M0, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10
```

a topic with 1 paritions, how messages may be stored in paritions

```
P0: [M0, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10]
```

a topic with 2 paritions, how messages may be stored in paritions, may be, based on configured algorithms/partitioner without key or key=null

```
P0: [M0, M2, M4, , M6,  M8,  M10]
P1: [M1, M3,  M5, M7, M9]
```

To get all the messages, we need to read from all partitions


a topic with 3 paritions, how messages may be stored in paritions, may be, based on configured algorithms/partitioner without key or key=null

```
P0: [M0, M4,  M8,  M10]
P1: [M1, M3,  M5, M9]
P2: [M2, M6,  M7]
```

To get all the messages, we need to read from all partitions

# consume messages

```
kafka-console-producer --broker-list localhost:9092 --topic messages
```

You can produce messages like M0, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10 one at a time in producer

Susbcribe from all partitions

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic messages  --from-beginning
```

Susbcribe from specific partitions
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

