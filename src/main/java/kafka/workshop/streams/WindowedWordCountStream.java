package kafka.workshop;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

//kafka-topics --zookeeper localhost:2181 --create --topic words --replication-factor 1 --partitions 3
//kafka-console-producer --broker-list localhost:9092 --topic words

// the final result of word count should be published to kafka topic word-count-1minute where key is string, value is Long

//kafka-topics --zookeeper localhost:2181 --create --topic word-count-1minute --replication-factor 1 --partitions 3
// kafka-console-consumer --topic word-count-1minute --from-beginning  --bootstrap-server localhost:9092 --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer


// the final result of word count should be published to kafka topic word-count-5minute where key is string, value is Long

//kafka-topics --zookeeper localhost:2181 --create --topic word-count-5minute --replication-factor 1 --partitions 3
// kafka-console-consumer --topic word-count-5minute --from-beginning  --bootstrap-server localhost:9092 --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

public class WindowedWordCountStream {
    static  String bootstrapServers = "localhost:9092";
    //FIXME: chance schema url
    static String schemaUrl = "http://localhost:8081";

    public static <KTable> void main(String[] args) throws  Exception {


        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "word-count-window-stream");
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "word-count-window-stream-client");
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

        KStream<Windowed<String>, Long> windowedWordStream = indWordStream
                                                    .map( (key, value) -> new KeyValue<>(value, value))
                                                    .groupByKey()
                .windowedBy(TimeWindows.of((Duration.ofMinutes(1)) ))
                .count(Materialized.as("wordCount")).toStream();

        // Finally publish the output to kafka topic
        // Key is string, Value is long
        // code below convert Windowed<String> to String for key
       windowedWordStream
               .map ( (windowedKey, count) -> new KeyValue<>(windowedKey.key(), count))
               .to("word-count-1minute", Produced.with(Serdes.String(), Serdes.Long()));

        windowedWordStream.foreach(new ForeachAction<Windowed<String>, Long>() {
            @Override
            public void apply(Windowed<String> windowedKey, Long count) {
                //windowedKey.window().end(), start() // milli time
                System.out.println("Start " + windowedKey.window().start() + " " + windowedKey.window().startTime().toString());
                System.out.println("End " + windowedKey.window().end() + " " + windowedKey.window().endTime().toString());
                System.out.println("  Key " + windowedKey.key() + "  value    " + count );
            }
        });





        final KafkaStreams streams = new KafkaStreams(builder.build(), props);


        streams.start();



    }
    }
