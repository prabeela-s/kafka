## on cluster of 4 brokers, create a topic with 4 partitions, 3 replicas

partitions shall be split into multiple brokers.., load balancing,   Fault tolerant.. replication still 3, 
each partitions copied into 3 different brokers.. 1 lead broker for each partition, others are followers



open a command prompt

```
    kafka-topics  --create --bootstrap-server localhost:9092,localhost:9093,localhost:9094,localhost:9095 --replication-factor 3 --partitions 4 --topic click-stream
```

```
    kafka-topics --list --bootstrap-server localhost:9092,localhost:9093,localhost:9094,localhost:9095
```    
   
```    
    kafka-topics --describe  --bootstrap-server localhost:9092,localhost:9093,localhost:9094,localhost:9095  --topic click-stream
```


Or

```
    kafka-topics  --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 4 --topic click-stream
```

```
    kafka-topics --list --bootstrap-server localhost:9093
```    
   
```    
    kafka-topics --describe  --bootstrap-server localhost:9095  --topic click-stream
```

open a command prompt and run producer

```
notes: enter some text and press enter key, each line is consider as one message

kafka-console-producer --broker-list localhost:9092,localhost:9093,localhost:9094,localhost:9095 --topic click-stream
```

produce some messages


Describe the topic..

```    
    kafka-topics --describe  --bootstrap-server localhost:9092,localhost:9093,localhost:9094,localhost:9095  --topic click-stream
```

Now stop the broker-0, check in zookeeper cli with command `ls /brokers/ids` to ensure broker-0 is not running..


Describe the topic again, you may see offline for broker-0, leader may have changed


```    
    kafka-topics --describe  --bootstrap-server localhost:9092,localhost:9093,localhost:9094,localhost:9095  --topic click-stream
```

Keep producing messages, we have 2 more replicas where all the data updated with other lead brokers..


bring up broker-0 again...


Describe the topic again, you may  broker-0 up, broker-0 is in sync with all the data updated , ISR - In sync Replica updated , no offline list for brokers


```    
    kafka-topics --describe  --bootstrap-server localhost:9092,localhost:9093,localhost:9094,localhost:9095  --topic click-stream
```

note: open 4th Command Prompt
 

get call the messages from beginging and then for new messages

```
kafka-console-consumer --bootstrap-server localhost:9092,localhost:9093,localhost:9094,localhost:9095 --topic click-stream --from-beginning 
```


from a specific partition only

```
kafka-console-consumer --bootstrap-server localhost:9092,localhost:9093,localhost:9094,localhost:9095 --topic click-stream --partition 0 --from-beginning
```
