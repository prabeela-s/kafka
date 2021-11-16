Open command prompt to start zookeeper

```
%KAFKA_HOME%\bin\windows\zookeeper-server-start %KAFKA_HOME%\etc\kafka\zookeeper.properties
```


Open second command prompt, to start zookeeper


```
%KAFKA_HOME%\bin\windows\kafka-server-start %KAFKA_HOME%\etc\kafka\server.properties
```


----




create a file zookeeper.bat in Desktop, paste below

```
%KAFKA_HOME%\bin\windows\zookeeper-server-start %KAFKA_HOME%\etc\kafka\zookeeper.properties
```


Double click and run zookeeper.bat 


create a file broker-0.bat in Desktop, pate below

```
%KAFKA_HOME%\bin\windows\kafka-server-start %KAFKA_HOME%\etc\kafka\server.properties
```

Double click and run broker-0.bat

