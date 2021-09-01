to list all consumers

```
kafka-consumer-groups --bootstrap-server localhost:9092 --list
```

to start consumer with specific group

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic billings --group invoicegroup    --property print.key=true
```


to describe a specific group 

```
kafka-consumer-groups --bootstrap-server localhost:9092 --describe  --group greetings-consumer-group
```

### Two modes

1. execute `--execute` - will reset
2. dry run `--dry-run` - will give plan, not execute or not reset

### topics, we two options

1. `--topic` - to specify a specific topics
2. `--all-topics` - to reset all topics for that consumer group


to reset offset to 0 on specific topic

```
kafka-consumer-groups --bootstrap-server localhost:9092  --group greetings-consumer-group --reset-offsets --to-earliest --execute --topic greetings2
```

to reset offset to latest

```
kafka-consumer-groups --bootstrap-server localhost:9092  --group greetings-consumer-group --reset-offsets --to-latest --execute --topic greetings2
```


to reset offset to specific to datetime YYYY-MM-DDTHH:mm:SS.sss

```
kafka-consumer-groups --bootstrap-server localhost:9092  --group greetings-consumer-group --reset-offsets --to-datetime 2021-06-15T11:01:01.999  --execute --topic greetings2
```


and check if offset reset


```
kafka-consumer-groups --bootstrap-server localhost:9092 --describe  --group greetings-consumer-group
```


shift current ofset by n numbers

```
kafka-consumer-groups --bootstrap-server localhost:9092  --group greetings-consumer-group --reset-offsets --shift-by 10  --execute --topic greetings2
```

shift by ofset negative by n numbers

```
kafka-consumer-groups --bootstrap-server localhost:9092  --group greetings-consumer-group --reset-offsets --shift-by -5   --execute --topic greetings2
```



## Dry Run

this give plans, doesn't reset the offset... safer option, first do with dry run and then apply execute..


```
kafka-consumer-groups --bootstrap-server localhost:9092  --group greetings-consumer-group --reset-offsets --shift-by -5   --dry-run --topic greetings2
```

```
kafka-consumer-groups --bootstrap-server localhost:9092  --group greetings-consumer-group --reset-offsets --to-datetime 2021-06-15T07:01:01.999  --dry-run --topic greetings2
```
