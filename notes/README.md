Now we are running Ubuntu Linux 18 on Windows WSL

To login into ubuntu, 

open command prompt...

```
wsl.exe -u root
```

We will start Kafka with linux know, however all ports opened in Linux are exposed to windows by defualt [share network]

Don't run batch files like zookeeper or broker-0, ..... or schema regisry from desktop, instead  we will run kafka on linux..

intellij, kafka topics, concumer, producer, all shall work with windows..
