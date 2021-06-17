# MySQL , JDBC Source and Sink : Event Sourcing

```
wsl.exe -u root

cd /root
```

Some issue with WSL with ubuntu CACHE files location, which is needed by mysql


```
sudo nano /etc/profile.d/wsl-integration.sh
```

Add below line on begining of the file..

```
# Check if we have HOME folder
if [ "${HOME}" = "/" ]; then
  return
fi
```

Ctrl + O - to save the content

if it is prompting to write content, Hit Enter key

Ctrl + X - to quit the nano editor

To start mysql, use 
```
sudo /etc/init.d/mysql start
```

to stop mysql use, right now DO NOT STOP MYSQL

```
sudo /etc/init.d/mysql stop
```


to uninstall Mysql,  DO NOT DO THIS NOW
```
sudo apt remove mysql-server

sudo apt autoremove
```
 
# Install MYSQL JDBC


```
cd /root

rm confluent-5.5.1/share/java/kafka-connect-jdbc/mysql-connector-java-5.1.47.jar
rm confluent-5.5.1/share/java/kafka-connect-jdbc/mysql-connector-java-5.1.47-bin.jar
```

```
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.25.tar.gz

tar xf mysql-connector-java-8.0.25.tar.gz
cp mysql-connector-java-8.0.25/*.jar confluent-5.5.1/share/java/kafka-connect-jdbc

```
================


# MYSQL Table/Database Setup


## Setup the database 

```
mysql -uroot


CREATE USER 'team'@'localhost' IDENTIFIED BY 'team1234';

CREATE DATABASE ecommerce; 

GRANT ALL PRIVILEGES ON ecommerce.* TO 'team'@'localhost';

exit
```


Exit from the shell

```

mysql -uroot

USE ecommerce;

-- detect insert/update changes using timestamp , but HARD delete

create table products (id int, 
                       name varchar(255), 
                       price int, 
                       create_ts timestamp DEFAULT CURRENT_TIMESTAMP, 
                       update_ts timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP );
                       
             
exit
```             



# MYSQL connectors


### START PROPERTY JSON CONFIGURATION

```
touch mysql-product-source.json
 
nano  mysql-product-source.json
```
   paste below
```
   {
   "name": "mysql-product-source",
   "config": {
     "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
     "key.converter": "io.confluent.connect.avro.AvroConverter",
     "key.converter.schema.registry.url": "http://localhost:8081",
     "value.converter": "io.confluent.connect.avro.AvroConverter",
     "value.converter.schema.registry.url": "http://localhost:8081",
     "connection.url": "jdbc:mysql://localhost:3306/ecommerce?user=team&password=team1234",
     "_comment": "Which table(s) to include",
     "table.whitelist": "products",
     "mode": "timestamp",
      "timestamp.column.name": "update_ts",
     "validate.non.null": "false",
     "_comment": "The Kafka topic will be made up of this prefix, plus the table name  ",
     "topic.prefix": "db_"
   }
 }
```
 
the above configuration shall publish the data to kafka topic db_products, where db_ is predix, table is products 

```
cat mysql-product-source.json
 ```
 
 
### restart the cluster once due to jdbc drivers copied just now

```
confluent local stop
confluent local start
```
 
### END PROPERTY JSON CONFIGURATION


 Note: the topic shall be <<PREFIX>>+<<TableName>> example: db_products
 
 ```
 confluent local load mysql-product-source -- -d mysql-product-source.json
 
 confluent local status connectors
 
 confluent local status mysql-product-source
 
 ```
  
  now open second command prompt and run below command

```

 kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic db_products --from-beginning --property schema.registry.url="http://localhost:8081"
 ```
  
 
  now insert data into mysql
  
  ```
  
mysql -uroot

USE ecommerce;
  
  insert into products (id, name,price) values(1, 'product1', 100);
  insert into products (id, name,price) values(2, 'product2', 200);
  insert into products (id, name,price) values(3, 'product3', 300);
  insert into products (id, name,price) values(4, 'product4', 400);
  
  ```
 
  as you insert, you can see the topics db_products with inserted data, this called Event Sourcing..
  
  
  
 # MySQL Sink connectors
 
  
 
## MYSQL SINK Connectors
  Consume from Topics, write to database
  kafka-avro-console-producers
  
  
  open new linux shell
 
  ```
  wsl -u root
  ```
  
 ```
  cd /root
 ```
  
  
```
touch  mysql-product-sink.json

nano  mysql-product-sink.json
```


```
   {
   "name": "mysql-product-sink",
   "config": {
     "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
     "topics": "products",
    
    "key.converter": "io.confluent.connect.avro.AvroConverter",
    "key.converter.schema.registry.url" : "http://localhost:8081",
    "value.converter" : "io.confluent.connect.avro.AvroConverter",
    "value.converter.schema.registry.url" : "http://localhost:8081",   
     
     "connection.url": "jdbc:mysql://localhost:3306/ecommerce?user=team&password=team1234",
     "auto.create": true
   }
 }
```
  
```
 confluent local load mysql-product-sink -- -d  mysql-product-sink.json
```
  
  
 ```
  confluent local status mysql-product-sink
  ```
  
Copy paste without new line
    
```
kafka-avro-console-producer --broker-list localhost:9092 --topic products --property value.schema='{"type":"record","name":"product","fields":[{"name":"id","type":"int"},{"name":"name", "type": "string"}, {"name":"price", "type": "int"}]}'  --property schema.registry.url="http://localhost:8081"
```   
    
    Copy paste without new line
    
```
{"id":11,"name":"phone2","price":111}
{"id":12,"name":"phone2","price":222}
{"id":13,"name":"phone2","price":333}
```

now check the table
   
```
select * from products
```

  
# Insert invoices into table
  
  
```
touch  mysql-invoice-sink.json

nano  mysql-invoice-sink.json
```


```
   {
   "name": "mysql-invoice-sink",
   "config": {
     "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
     "topics": "invoices",
    
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter" : "io.confluent.connect.avro.AvroConverter",
    "value.converter.schema.registry.url" : "http://localhost:8081",   
     
     "connection.url": "jdbc:mysql://localhost:3306/ecommerce?user=team&password=team1234",
     "auto.create": true
   }
 }
```
  
```
 confluent local load mysql-invoice-sink -- -d  mysql-invoice-sink.json
```
  
  
 ```
  confluent local status mysql-invoice-sink
  ```
  
 ```
  now run the InvoiceProducer in Java 
  ```
  
  In mysql
  
  ```
  SHOW TABLES;
  
  SELECT * FROM invoices;
  
  ```
