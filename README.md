# ibm-kafka-june-2021

# for Kafka Stream rockdb dll issue

Download C++ Redistributabe 2015 and install it

https://www.microsoft.com/en-us/download/details.aspx?id=48145

Download vc_redist.x64.exe and install it


and in the pom.xml,

```
 <dependency>
            <groupId>org.rocksdb</groupId>
            <artifactId>rocksdbjni</artifactId>
            <version>5.18.4</version>
        </dependency>
```


# Day 3.5 onwards

```
wsl.exe -u root
```

```
sudo apt install jq
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




----

```
sudo nano /etc/profile.d/wsl-integration.sh
```

```
# Check if we have HOME folder
if [ "${HOME}" = "/" ]; then
  return
fi
```

```
sudo /etc/init.d/mysql start
sudo /etc/init.d/mysql stop
```


```
sudo apt remove mysql-server

sudo apt autoremove
```

if any error with ubutnu install pacakage fix

```
sudo apt --fix-broken install
```


References:

1. https://github.com/RaphaHell42/pyspark-kafka-streaming
2. 
