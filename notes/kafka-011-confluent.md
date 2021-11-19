# confluent development env

start all services, works only on mac/unix/linux

```
confluent local start
```

if there is failure in above command, stop the services, delete the data, topics etc
```
confluent local destroy
```

then start

```
confluent local start
```

to stop all services without deleting
```
confluent local stop
```

to know where the data for zookeeper, brokers are stored,

```
confluent local current
```

to know whether kafka and other services are  running or not

```
confluent local status
```
