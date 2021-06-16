
cd root

rm confluent-5.5.1/share/java/kafka-connect-jdbc/mysql-connector-java-5.1.47.jar
rm confluent-5.5.1/share/java/kafka-connect-jdbc/mysql-connector-java-5.1.47-bin.jar


wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.25.tar.gz

tar xf mysql-connector-java-8.0.25.tar.gz
cp mysql-connector-java-8.0.25/*.jar confluent-5.5.1/share/java/kafka-connect-jdbc

