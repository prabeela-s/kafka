package kafka.workshop.streams;

import kafka.workshop.Settings;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.StreamsBuilder;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;

// kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic texts
// kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic word-count

// kafka-console-producer --broker-list localhost:9092 --topic texts

// kafka-console-consumer --bootstrap-server localhost:9092 --topic word-count --from-beginning --property print.key=true --property print.value=true --formatter kafka.tools.DefaultMessageFormatter --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer


public class WordCountStream {

    public static void main(final String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Settings.BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore("in-mem");

        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> textLines = builder.stream("texts");

        KTable<String, Long> wordCounts = textLines
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .groupBy((key, word) -> word)
                .count(Materialized.<String, Long>as(storeSupplier)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long())) ;

        wordCounts.toStream().to("word-count", Produced.with(Serdes.String(), Serdes.Long()));

        // collection of streams put together
        final KafkaStreams streams = new KafkaStreams(builder.build(), props);

        try {
            streams.cleanUp();
        }catch(Exception e) {
            System.out.println("error while cleanup states");
        }

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
