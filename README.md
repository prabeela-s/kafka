# ibm-kafka-june-2021


# Day 3.5 onwards

```
wsl.exe -u root
```

start kafka local cluster using confluent command 

```
cd /root
```

```
confluent local start
```

if there is failure in above command,

```
confluent local destroy
```

then start 


```
confluent local start
```





open C:\confluent-5.5.1\bin\windows\kafka-run-class.bat in notepad++ (right click, edit with notepad++)

paste below line around line 45 

```
rem class path patch for kafka on windows
if exist %BASE_DIR%\share\java\kafka\* (
call:concat %BASE_DIR%\share\java\kafka\*
)
```


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
