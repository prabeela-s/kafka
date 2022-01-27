## KSQL

You need linux to use all the below commands, open command prompt and run below command for at least 4 different windows

```
wsl.exe -u training
```


Now run `ksql client` which is console based client, will connect to ksql server.

```
ksql
```


Basic commands, to list streams and tables.

```
SHOW STREAMS;

SHOW TABLES;

```

## Orders Stream example

```
CREATE STREAM orders_stream (
            orderId varchar, 
            customerId varchar, 
            country varchar, 
            amount double
            ) WITH (kafka_topic='orders', value_format='JSON');


```

```
SHOW STREAMS
```

Run non-persisted queries

```
SELECT * FROM orders_stream emit changes ;
```

To stop the query later, do Ctrl + C


Now Run OrderProducer.java code..


## Using DataGen Tool

confluent kafka has datagen utility, which can generate data for learning purpose..

clickstream like example

1. users stream
    generate users who has userId, gender, region data
2. pageview stream
     generate pageviews which has userId, pageId, time
     
join can be done with userId with both the stream

## Preparation

Launch Linux Shell 1 to produce users data

below produce the records, write to topic users

```
ksql-datagen quickstart=users format=json topic=users maxInterval=60000 iterations=5000000
```

Launch Linux  Shell 2

below produce the pageview records, write to topic pageviews

```
ksql-datagen quickstart=pageviews format=json topic=pageviews maxInterval=60000 iterations=5000000
```

Launch Linux  Shell 3 for kSQL interactive queries

```
ksql
```

```
SHOW STREAMS;

SHOW TABLES;

CREATE STREAM users_stream (userid varchar, regionid varchar, gender varchar) WITH (kafka_topic='users', value_format='JSON');

SHOW TABLES;

DESCRIBE users_stream;
```

NON_PERSISTED QUERIES [Means, the output/result is not stored into KAfka Brokers]

To stop the non persisted query, use Ctrl + C

```
select userid, regionid, gender from users_stream EMIT CHANGES;
```

Now generate users records on shell 1, if datagen stop, run again.. 

```
select userid, regionid, gender from users_stream where gender='FEMALE'  EMIT CHANGES;
```

Now generate users records on shell 1, if datagen stop, run again..

```
select userid, regionid, gender from users_stream where gender='MALE'  EMIT CHANGES;
```

Now generate users records on shell 1, if datagen stop, run again..


PERSISTED QUERIES [CREATE STREAM AS ] results written to Kafka
Will be runnign automatically, need to use TERMINATE command to stop them

persisted queries will create topics like users_female, users_male kafka topics, and results shall be published to kafka topics..

```
CREATE TABLE users_female AS SELECT userid AS userid, regionid FROM users_stream where gender='FEMALE';

CREATE TABLE users_male AS SELECT userid AS userid, regionid FROM users_stream where gender='MALE';
```

now check whether new topics created or not

```
SHOW STREAMS;

SHOW TOPICS;
```

Listen for from newly created streams..

```
select * from users_female  EMIT CHANGES;
select * from users_male  EMIT CHANGES;
```

Now create pageviews_stream from pageviews data

```
 CREATE STREAM pageviews_stream (userid varchar, pageid varchar) WITH (kafka_topic='pageviews', value_format='JSON');
 
 select * from pageviews_stream  EMIT CHANGES;
```


now generate pageviews usign datagen



JOIN pages view and users stream

```
CREATE STREAM user_pageviews_enriched_stream AS SELECT users_stream.userid AS userid, pageid, regionid, gender FROM pageviews_stream LEFT JOIN users_stream WITHIN 1 HOURS ON pageviews_stream.userid = users_stream.userid;

select * from user_pageviews_enriched_stream  EMIT CHANGES;
```

Ctrl +C to exit

use window, time slicing, group by, aggregation

```
CREATE TABLE pageviews_region_table WITH (VALUE_FORMAT='JSON') AS SELECT gender, regionid, COUNT() AS numusers FROM user_pageviews_enriched_stream WINDOW TUMBLING (size 60 second) GROUP BY gender, regionid HAVING COUNT() >= 1;

select * from pageviews_region_table  EMIT CHANGES;


```

### Order Data from Java


```
CREATE STREAM orders_stream (orderId varchar, amount double, customerId varchar, country varchar) WITH (kafka_topic='orders', value_format='JSON');

DESCRIBE  orders_stream;

SELECT * FROM ORDERS_STREAM EMIT CHANGES;
```


Run OrderProducer.java


### Invoice Avro data from Java

```
CREATE STREAM invoice_stream (id varchar, qty int, amount int, customerId varchar, state varchar, country string, invoiceDate bigint  )
       	 WITH (kafka_topic='invoices', value_format='AVRO');
```

```
SELECT * FROM invoice_stream EMIT CHANGES;
```




List the persisted queries

```
SHOW QUERIES;
```

explain the query with query id



C***** - QUERY ID

```
EXPLAIN CTAS_PAGEVIEWS_REGION_TABLE_3; 

```

To stop the query / once stopped, cannot be restarted, need to run fresh query
`Query ID may vary, use the right one from show queries`

```
TERMINATE  CTAS_PAGEVIEWS_REGION_TABLE_3;

```

DROP STREAM and TABLE

```
DROP STREAM  users_male; 


DROP TABLE  pageviews_region;
```
 


# launch Linxu shell 4

for the consumer...

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic USERS_FEMALE --from-beginning "

kafka-console-consumer --bootstrap-server localhost:9092 --topic PAGEVIEWS_REGION_TABLE --from-beginning "
```


# KSQL AVRO

Check result in control center

http://ip:9021



```

ksql-datagen quickstart=users format=avro topic=users-avro maxInterval=60000 iterations=5000000

ksql-datagen quickstart=pageviews format=avro topic=pageviews-avro maxInterval=60000 iterations=5000000
```

```sql
CREATE STREAM users_stream_avro (userid varchar, regionid varchar, gender varchar) WITH (kafka_topic='users-avro', value_format='AVRO');

 CREATE STREAM pageviews_stream_avro (userid varchar, pageid varchar) WITH (kafka_topic='pageviews-avro', value_format='AVRO');

 CREATE STREAM user_pageviews_enriched_stream_avro WITH (VALUE_FORMAT='AVRO') AS SELECT users_stream_avro.userid AS userid, pageid, regionid, gender FROM pageviews_stream_avro LEFT JOIN users_stream_avro WITHIN 1 HOURS ON pageviews_stream_avro.userid = users_stream_avro.userid;


CREATE TABLE pageviews_region_table_avro WITH (VALUE_FORMAT='AVRO') AS SELECT gender, regionid, COUNT() AS numusers FROM user_pageviews_enriched_stream_avro WINDOW TUMBLING (size 60 second) GROUP BY gender, regionid HAVING COUNT() >= 1;

```
