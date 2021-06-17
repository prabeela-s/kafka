# Repartition/reassign partitions

kafka-reassign-partitions.bat

History:

Scenario 1:
```
Day 1: started with 1 broker, 1 replica, and 1 paritions
Day 2: add more paritions, more replicas..
```
Scenario 2:

```
Day 1: with brokers [broker-0, broker-1, broker-2]
Day 2: new broker to be added, old broker to be removed [broker-1]
```

We will use InvoiceProducer.java example, ...

```
kafka-topics --zookeeper localhost:2181 --create --topic invoices --replication-factor 1 --partitions 1
```

run the Invoice Producer after creating topics., data get added every 5 seconds...

```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic invoices
```

Now alter the topics.. adding more parititions

```
kafka-topics --zookeeper localhost:2181 --alter --topic invoices   --partitions 2
```


```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic invoices
```


Now plan for moving topics, by the way partitions may be to new nodes, new broker, older broker to new broker

in command prompt, 
```
C:\Users\Administrator> notepad topics-to-move.json
```

paste the content and save notepad file..

```
{
"topics": [
  {"topic":"invoices"}
],
"version":1
}
```

# generate a plan [not execution/only preview] for reparitions

below commnad generate a file which will have plan to move your data new broker/partitions etc..

```
kafka-reassign-partitions --zookeeper localhost:2181 --broker-list "1,2,3" --topics-to-move-json-file topics-to-move.json --generate > full-reassignment-file.json
```

```
notepad full-reassignment-file.json
```

