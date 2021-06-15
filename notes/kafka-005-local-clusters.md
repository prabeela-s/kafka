# run kafka in cluster

1. 4 brokers run the same PC
2. Hostname/ip address for the brokers are same
3. change the port number for brokers
4. broker-0 [id: 0] will listne on port 9092, will have log files at C:\tmp\kafka-logs
5. broker-1 [id: 1] will listne on port 9093, will have log files at C:\tmp\kafka-logs-1
6. broker-2 [id: 2] will listne on port 9094, will have log files at C:\tmp\kafka-logs-2
7. broker-3 [id: 3] will listne on port 9095, will have log files at C:\tmp\kafka-logs-3


### copy C:\confluent-5.5.1\etc\kafka\server.properties into 3 more times, rename the files like 

```
server-1.properties
server-2.properties
server-3.properties
```


### update server-1.properties

1. open the file in notepad++

change the below properties, note: uncomment listeners=PLAINTEXT:

```
....
....
broker.id=1
....
....
listeners=PLAINTEXT://:9093
...
...
log.dirs=/tmp/kafka-logs-1
```

save the file..


### update server-2.properties

1. open the file in notepad++

change the below properties, note: uncomment listeners=PLAINTEXT:

```
....
....
broker.id=2
....
....
listeners=PLAINTEXT://:9094
...
...
log.dirs=/tmp/kafka-logs-2
```

save the file..




### update server-2.properties

1. open the file in notepad++

change the below properties, note: uncomment listeners=PLAINTEXT:

```
....
....
broker.id=2
....
....
listeners=PLAINTEXT://:9095
...
...
log.dirs=/tmp/kafka-logs-2
```

save the file..




### update server-3.properties

1. open the file in notepad++

change the below properties, note: uncomment listeners=PLAINTEXT:

```
....
....
broker.id=3
....
....
listeners=PLAINTEXT://:9096
...
...
log.dirs=/tmp/kafka-logs-3
```

save the file..
