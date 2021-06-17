# Log Compaction

open a command prompt, create a topic with compaction enabled..
 
```
    kafka-topics  --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic settings --config cleanup.policy=compact --config delete.retention.ms=100 --config segment.ms=100 --config min.cleanable.dirty.ratio=0.01   
```

```
    kafka-topics --list --bootstrap-server localhost:9092
```    
   
```    
    kafka-topics --describe  --bootstrap-server localhost:9092  --topic settings
```


open a command prompt and run producer with key value delimited by colon :

```
notes: enter some text and press enter key, each line is consider as one message

kafka-console-producer --broker-list localhost:9092 --topic settings --property "parse.key=true" --property "key.separator=:"
```

left side colon represent key, right of colon represent value.. 

```
machine1.power:on
machine1.temp:18
machine1.fanspeed:3
machine1.fanmode:swing
machine2.power:off
machine2.temp:24
machine2.fanspeed:5
machine2.fanmode:fixed
machine1.temp:20
machine1.fanspeed:2
machine1.fanmode:fixed
machine2.power:on
```


note: open 4th Command Prompt

listen for the messages published/latest

run this, and do Ctrl + C, then run again, then do Ctrl + C, run again.. to check whether messages are really removed or not..
 

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic settings --from-beginning
```
