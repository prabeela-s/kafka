# Schema Registry

1. REST API
2. Refer https://github.com/nodesense/ibm-kafka-june-2021/tree/main/confluent-5.5.1/bin/windows for downloading helper scripts to start schema registry
3. Refer https://github.com/nodesense/ibm-kafka-june-2021/tree/main/Desktop/schema-registry.bat and download to your Desktop as shortcut to start schema register
4. To start schema registry, you can double click Desktop/schema-registry.bat and start it.
5. Open the browser in remote desktop, check http://localhost:8081
6. To get all the subjects, <<TOPICNAME-key>> or <<TOPICNAME-value>> http://localhost:8081/subjects
7. To get all the versions of specific subjects http://localhost:8081/subjects/invoices-value/versions
8. To get schema specific to a version, http://localhost:8081/subjects/invoices-value/versions/1    where as 1 may change, refer step 7 for exact schema version
8. To get the schema from schema registry using schema id  http://localhost:8081/schemas/ids/1

  
### Avro console consumer

 ```
 kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic invoices --from-beginning --property schema.registry.url="http://localhost:8081"
  ```
  
  
