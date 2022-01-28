## File Connectors, Source and Sink

```
wsl.exe -u training
```

```
cd /home/training

sudo apt install jq


confluent local list connectors

confluent local status connectors
```


# work setup

Every user, create a folder with initial, cd into folder, update the filepath accordingly..

example, gopalakrishnan, create gk directory, cd into gk directory, and create all files with gk- prefix..

Similarly kafka topics should have prefix gk-

```
root@k5:~# mkdir gk
root@k5:~# cd gk
root@k5:~/gk# pwd
/root/gk
root@k5:~/gk#
```



File Connector, File Source connector
    input file, read from teh file stocks.csv, watch the file change,
    publish to kafka topic called stocks
 
Create the file 


```
touch stocks.csv
```
 
 

Load the source connector / run the connector

```
touch stock-file-source.json

nano stock-file-source.json
```

and below content  into nano

```

{
 "name": "stock-file-source",
 "config": {
     "connector.class": "FileStreamSource",
     "tasks.max": "1",
    "file": "/home/training/stocks.csv",
    "topic": "stocks"
     }
 }
```

Ctrl + O - to save the content

if it is prompting to write content,  Hit Enter key

Ctrl + X - to quit the nano editor


Use cat command to check content

```
cat stock-file-source.json
```


```
confluent local load stock-file-source -- -d stock-file-source.json

confluent local load gk-stock-file-source -- -d stock-file-source.json
```

Check whether connector is running or not

```
confluent local status connectors
```

check specific connector status 

```
confluent local status stock-file-source

```

start consumer on stocks topic on separate linux shell..

``` 
kafka-console-consumer --bootstrap-server localhost:9092 --topic  stocks   --from-beginning

kafka-console-consumer --bootstrap-server localhost:9092 --topic  gk-stocks   --from-beginning

```

Put some data into csv file

```
echo "1234,10" >> stocks.csv

echo "1235,20" >> stocks.csv

echo "1236,30" >> stocks.csv


cat stocks.csv
```


to unload kafka connector running? 

```
confluent local unload stock-file-source
```



# File Sink connector


Ensure simpleproducer.java topic should be greetings

```
public class SimpleProducer {

    public static String TOPIC = "greetings";

}
```

```
touch greetings.txt


touch greetings-file-sink.json

nano greetings-file-sink.json

```

paste below content

```
{
 "name": "greetings-file-sink",
 "config": {
     "connector.class": "FileStreamSink",
     "tasks.max": "1",
    "file": "/home/training/greetings.txt",
    "topics": "greetings",
"key.converter": "org.apache.kafka.connect.storage.StringConverter",
"value.converter": "org.apache.kafka.connect.storage.StringConverter"
     }
 }
```
 
 Ctrl + O - to save the content

if it is prompting to write content, Hit Enter key

Ctrl + X - to quit the nano editor

Use cat command to check content

```
cat greetings-file-sink.json

```
 
## Done

```
confluent local load greetings-file-sink -- -d greetings-file-sink.json
confluent local status greetings-file-sink
```

Run the SimpleProducer.java


```
cat greetings.txt
```


```
confluent local  unload greetings-file-sink

```


# Invoices to file sink



## One last example Avro and file sink

```
touch invoices.txt


touch invoices-file-sink.json

nano invoices-file-sink.json

```

paste below content



 

```
{
 "name": "invoices-file-sink",
 "config": {
     "connector.class": "FileStreamSink",
     "tasks.max": "1",
    "file": "/home/training/invoices.txt",
    "topics": "invoices",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "io.confluent.connect.avro.AvroConverter",
    "value.converter.schema.registry.url": "http://localhost:8081"
     }
 }
```


```
confluent local load invoices-file-sink -- -d invoices-file-sink.json

confluent local status invoices-file-sink

```


Ctrl + O - to save the content

if it is prompting to write content,  Hit Enter key

Ctrl + X - to quit the nano editor


Use cat command to check content

```
cat invoices-file-sink.json
```
 

## DONE
 

```
Now run the InvoiceProducer.java that pblish to invoices topics
```
 
 Do this command periodically
 
``` 
cat invoices.txt
```



### ensure connectors unloaded to save memory if run in constrained environment

```
confluent local  unload invoices-file-sink
```
 
 
