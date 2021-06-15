to list all consumers

```
kafka-consumer-groups --bootstrap-server localhost:9092 --list
```

to describe a specific group 

```
kafka-consumer-groups --bootstrap-server localhost:9092 --describe  --group greetings-consumer-group
```


to reset offset to 0 on specific topic

```
kafka-consumer-groups --bootstrap-server localhost:9092  --group greetings-consumer-group --reset-offsets --to-earliest --execute --topic greetings2
```

and check if offset reset


```
kafka-consumer-groups --bootstrap-server localhost:9092 --describe  --group greetings-consumer-group
```
