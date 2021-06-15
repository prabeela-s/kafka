// SimpleConsumer.java
package kafka.workshop;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class LogConsumer {
    public static String TOPIC = "logs";

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();

        props.put(BOOTSTRAP_SERVERS_CONFIG, Settings.BOOTSTRAP_SERVERS_LIST);

        // Consumer group id, should be unique, partition allocated to consumers inside teh group
        props.put(GROUP_ID_CONFIG, "logs-consumer-group"); // offset, etc, TODO

        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); // commit every 1 second

        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");

        // key deserialize the bytes data into String format, JSON,AVRO formats
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // value deserialize the bytes data into String format, JSON,AVRO formats
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // <Key as string, Value as string>
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // subscribe from one or more topics
        consumer.subscribe(Collections.singletonList(TOPIC));

        System.out.println("Consumer starting..");


        List<PartitionInfo> partitions = consumer.partitionsFor(TOPIC);
        for (PartitionInfo partitionInfo: partitions) {
            System.out.println("Partition " + partitionInfo);
            System.out.println("Leader " + partitionInfo.leader());
        }

        System.out.println("---------------------------");
        consumer.close();

    }

}
