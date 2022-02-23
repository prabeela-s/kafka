```
 1  hostname
    2  hostname -f
    3  clear
    4  apt update
    5  apt upgrade
    6  history
    7  apt install openjdk-8-jdk curl wget jq -y
    8  wget http://packages.confluent.io/archive/5.5/confluent-5.5.5-2.12.tar.gz
    9  tar xf confluent-5.5.5-2.12.tar.gz
   10  sudo mv confluent-5.5.5 /opt
   11  ls /opt
   12  chmod 777 /opt/confluent-5.5.5
   13  chmod 777 -R /opt/confluent-5.5.5
   14  wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.47.tar.gz
   15  tar xf mysql-connector-java-5.1.47.tar.gz
   16  cp mysql-connector-java-5.1.47/*.jar /opt/confluent-5.5.5/share/java/kafka-connect-jdbc
   17  echo "export KAFKA_HOME=/opt/confluent-5.5.5" >> ~/.bashrc
   18  echo "export PATH=\$PATH:\$KAFKA_HOME/bin" >>  ~/.bashrc
   19  echo "JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64" >> /etc/environment
   20  echo "JRE_HOME=/usr/lib/jvm/java-8-openjdk-amd64" >> /etc/environment
   21  echo "KAFKA_HOME=/opt/confluent-5.5.5" >> /etc/environment
   22  echo "CONFLUENT_HOME=/opt/confluent-5.5.5" >> /etc/environment
   23  history
   24  reboot
   25  history

```
