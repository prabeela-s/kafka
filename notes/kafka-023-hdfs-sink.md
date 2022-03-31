## HDFS Sink

```
touch hdfs-sink.json
```

```
nano hdfs-sink.json
```

paste below

```
{
"name": "hdfs-sink",
"config": {
"connector.class": "io.confluent.connect.hdfs.HdfsSinkConnector",
"tasks.max": "1",
"topics": "test_hdfs",
"hdfs.url": "hdfs://localhost:9000",
"hadoop.conf.dir": "/home/ubuntu/hadoop-2.7.7/etc/hadoop",
"hadoop.home": "/home/ubuntu/hadoop-2.7.7",
"flush.size": "3",
"rotate.interval.ms": "1000"
}
}
```

```
 confluent local load hdfs-sink -- -d  hdfs-sink.json
 confluent local status hdfs-sink
 
  confluent local unload hdfs-sink

```
