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

run the Invoice Producer after creating topics.

