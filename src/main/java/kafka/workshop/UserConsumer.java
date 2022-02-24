package kafka.workshop;

import kafka.workshop.Settings;
import kafka.workshop.models.Invoice;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class UserConsumer {
    public static String TOPIC = "users-avro";

    public static void main(String[] args) {


        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, Settings.BOOTSTRAP_SERVERS);
        props.put(GROUP_ID_CONFIG, "user-avro-consumer-example"); // offset, etc, TODO
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");


        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put("schema.registry.url", Settings.SCHEMA_REGISTRY);

        // <Key as string, Value as string>
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(props);

        // Subscribe for topic(s)
        consumer.subscribe(singletonList(TOPIC));

        System.out.println("Consumer Starting!");

        while (true) {
            // poll (timeout value), wait for 1 second, get all the messages
            // <Key, Value>
            ConsumerRecords<String, Object> records = consumer.poll(ofSeconds(1));
            // if no messages
            if (records.count() == 0)
                continue;


            // Iterating over each record
            for (ConsumerRecord<String, Object> record : records) {

                System.out.printf("partition= %d, offset= %d, key= %s, value= %s\n",
                        record.partition(),
                        record.offset(),
                        record.key(),
                        record.value());


                System.out.println("Record " + record.value());

                GenericRecord genericRecord = (GenericRecord) record.value();
                System.out.println("genericRecord " + genericRecord);

                // schema always obtained from schema registry
                // every avro message will have schema id as part of message
                Schema schema = genericRecord.getSchema();

                System.out.println("schema " + schema);
                System.out.println("userid field " + schema.getField("userid"));
                System.out.println("regionid field " + schema.getField("regionid"));
                System.out.println("gender field " + schema.getField("gender"));

                // field by position
                // get value by position

                System.out.println("Field 0 " + genericRecord.get(0));
                System.out.println("Field 2 " + genericRecord.get(1));
                System.out.println("Field 0 " + genericRecord.get(2));
                System.out.println("Field 2 " + genericRecord.get(3));

                /// field by name
                System.out.println("Fieldby name userid  " + genericRecord.get("userid"));
                System.out.println("Fieldby name gender  " + genericRecord.get("gender"));
                System.out.println("Fieldby name registertime  " + genericRecord.get("registertime"));

                System.out.println("Fieldby name region  " + genericRecord.get("region"));







            }
        }
    }
}
