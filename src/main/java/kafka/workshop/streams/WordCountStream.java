package kafka.workshop;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import kafka.workshop.models.Invoice;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;

import java.util.Arrays;
import java.util.Properties;

//kafka-topics --zookeeper localhost:2181 --create --topic words --replication-factor 1 --partitions 3
//kafka-console-producer --broker-list localhost:9092 --topic words

// the final result of word count should be published to kafka topic word-count where key is string, value is Long

//kafka-topics --zookeeper localhost:2181 --create --topic word-count --replication-factor 1 --partitions 3
// kafka-console-consumer --topic word-count --from-beginning  --bootstrap-server localhost:9092 --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer


public class WordCountStream {
    static  String bootstrapServers = "localhost:9092";
    //FIXME: chance schema url
    static String schemaUrl = "http://localhost:8081";

    public static <KTable> void main(String[] args) throws  Exception {


        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "word-count-stream");
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "word-count-stream-client");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1 * 1000);
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);


        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> wordStream = builder.stream("words");

        // apply transformation, topology, processor
        // transformation: remove white space , upper to lower case
        final KStream<String, String> filteredStream =  wordStream.map ( (key, value) -> new KeyValue<>(key, value.trim().toLowerCase()))
                .filter( (key, value) -> !value.isEmpty());

        // split line into word array
        final KStream<Object, String[]> splitWordStream = filteredStream.map ( (key, value) -> new KeyValue<>(null, value.split("\\W+")));

        // now we have word array, we need convert and flatten them into word, not word array
        // FlatMapValues
        final KStream<Object, String> indWordStream = splitWordStream.flatMapValues((values) -> Arrays.asList(values));

        KGroupedStream<String, String> groupedStream = indWordStream.groupBy( (key, value) -> value); // group by word, value is java, jvm

        KStream<String, Long> wordCountStream  = groupedStream.count(Materialized.as("wordCount"))
                .toStream();

        // Finally publish the output to kafka topic
        // Key is string, Value is long
        wordCountStream.to("word-count", Produced.with(Serdes.String(), Serdes.Long()));

        splitWordStream.foreach(new ForeachAction<Object, String[]>() {
            @Override
            public void apply(Object key, String[] words) {
                System.out.println("  Key " + key + "  value    " + words.length );
            }
        });


        filteredStream.foreach(new ForeachAction<String, String>() {
            @Override
            public void apply(String key, String line) {
                System.out.println("  Key " + key + "  value    " + line );
            }
        });

        wordCountStream.foreach(new ForeachAction<String, Long>() {
            @Override
            public void apply(String key, Long count) {
                System.out.println("  Key " + key + "  value    " + count );
            }
        });



        indWordStream.foreach(new ForeachAction<Object, String>() {
            @Override
            public void apply(Object key, String line) {
                System.out.println("indWordStream  Key " + key + "  value    " + line );
            }
        });

        // ------- builder is a topology builder, until here, no message is subscribed,
        // kafka stream is not started, no prorcessing until here


        // kafka streams starting, kafka subscribed, processing applied, output writen to kafka
        Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);


        streams.start();



    }
}
