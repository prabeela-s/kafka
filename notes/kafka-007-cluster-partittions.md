## on cluster of 4 brokers, create a topic with 4 partitions

partitions shall be split into multiple brokers.., load balancing, not Fault tolerant.. replication still 1

open a command prompt

```
    kafka-topics  --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 4 --topic logs
```

```
    kafka-topics --list --bootstrap-server localhost:9092
```    
   
```    
    kafka-topics --describe  --bootstrap-server localhost:9092  --topic logs
```


open a command prompt and run producer

```
notes: enter some text and press enter key, each line is consider as one message

kafka-console-producer --broker-list localhost:9092 --topic logs
```

note: open 4th Command Prompt
 

get call the messages from beginging and then for new messages

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic logs --from-beginning 
```


from a specific partition only

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic logs --partition 0 --from-beginning
```
