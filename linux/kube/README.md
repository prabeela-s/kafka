SpringPeople/Walmart trainig

Open terminal

```
cd Desktop


wget http://packages.confluent.io/archive/5.5/confluent-5.5.5-2.12.tar.gz
tar xf confluent-5.5.5-2.12.tar.gz

rm confluent-5.5.5-2.12.tar.gz
```

```
sudo apt install nano
```

```
touch ~/.bashrc
```

```
echo "export KAFKA_HOME=/home/lab-user/Desktop/confluent-5.5.5" >> ~/.bashrc
echo "export CONFLUENT_HOME=/home/lab-user/Desktop/confluent-5.5.5" >> ~/.bashrc
echo "export PATH=\$PATH:\$KAFKA_HOME/bin" >>  ~/.bashrc
```

Close the terminal and open new terminal

```
confluent local start
```


