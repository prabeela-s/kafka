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

below commnad generate a file which will have plan to move your data new broker/partitions etc..Somehow we want to remove broker 1 from cluster

```
kafka-reassign-partitions --zookeeper localhost:2181 --broker-list "0,2,3" --topics-to-move-json-file topics-to-move.json --generate > full-reassignment-file.json
```

```
notepad full-reassignment-file.json
```

it will look like, 

```
Current partition replica assignment
{"version":1,
 "partitions":[
      {"topic":"invoices","partition":1,"replicas":[2],"log_dirs":["any"]},
      {"topic":"invoices","partition":0,"replicas":[1],"log_dirs":["any"]}
      ]
}

Proposed partition reassignment configuration
{
 "version":1,
  "partitions":[
      {"topic":"invoices","partition":1,"replicas":[2],"log_dirs":["any"]},
      {"topic":"invoices","partition":0,"replicas":[3],"log_dirs":["any"]}
   ]
}
```

You can optimize/reconfigure the json content as per your requirements...., above file contain present and proposed content.. 

after the config file full-reassignment-file.json changes done, we need to execute the plan...

```
notepad reassignment.json
```

and copy only Proposed partition reassignment configuration as json content into reassignment.json, NOT CURRENT PLAN, NOT TEXT PART.., 

changes plan as per  your interest

save the file


now describe again one more time, to ensure prior setup.

```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic invoices
```


##### STOP THE INVOICEPRODUCER

To execute the plan, 

```
kafka-reassign-partitions --zookeeper localhost:2181 --reassignment-json-file   reassignment.json --execute
```


after reassignment.. describe again..

```
kafka-topics --describe  --bootstrap-server localhost:9092  --topic invoices
```

