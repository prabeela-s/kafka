For Avro jars

download below files and store it in your project lib directory

https://github.com/nodesense/deloitte-kafka-2020/raw/master/lib/avro-1.9.1.jar

https://github.com/nodesense/deloitte-kafka-2020/raw/master/lib/avro-tools-1.9.1.jar



Now above files stored in lib folder.. we have schema in resoruces/avro folder

run the tools in IntelliJ terminal to generate schema

command for single schema 

```
java -jar ./lib/avro-tools-1.9.1.jar compile schema ./src/main/resources/avro/invoice.avsc ./src/main/java
```

command for all schemas 

```
java -jar ./lib/avro-tools-1.9.1.jar compile schema ./src/main/resources/avro ./src/main/java
```
