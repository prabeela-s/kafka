# Mirror maker

ensure we have two Clusters running for demo


Cluster 1: 

Zookeeper : 2181
Broker: 9092

Cluster 2:


Zookeeper : 3181
Broker: 9098
Brokerid: 20

update zookeeper to 3181

Refer files from mirror-maker


kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings


kafka-console-consumer --bootstrap-server localhost:9098 --topic greetings


