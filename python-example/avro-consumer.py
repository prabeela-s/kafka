import io
import struct

from avro.io import BinaryDecoder, DatumReader
from confluent_kafka import Consumer
from confluent_kafka.avro.cached_schema_registry_client import CachedSchemaRegistryClient
from confluent_kafka.avro.serializer import SerializerError

# Please adjust your server and url

# KAFKA BROKER URL
consumer = Consumer({
    'bootstrap.servers': 'k1.training.sh:9021',
    'group.id': 'abcd'
})
# ksql-datagen quickstart=users format=avro topic=users-avro2 maxInterval=60000 iterations=5

# SCHEMA URL 
register_client = CachedSchemaRegistryClient(url="http://k1.training.sh:8081")
consumer.subscribe(['users-avro2'])

MAGIC_BYTES = 0


def unpack(payload):
    magic, schema_id = struct.unpack('>bi', payload[:5])

    # Get Schema registry
    # Avro value format
    if magic == MAGIC_BYTES:
        schema = register_client.get_by_id(schema_id)
        reader = DatumReader(schema)
        output = BinaryDecoder(io.BytesIO(payload[5:]))
        abc = reader.read(output)
        return abc
    # String key
    else:
        # If KSQL payload, exclude timestamp which is inside the key. 
        # payload[:-8].decode()
        return payload.decode()


def get_data():
    while True:
        try:
            msg = consumer.poll(1)
            if msg is None:
                continue
            print("Msg ", msg)
        except SerializerError as e:
            print("Message deserialization failed for {}: {}".format(msg, e))
            raise SerializerError

        if msg.error():
            print("AvroConsumer error: {}".format(msg.error()))
            return

        value = unpack(msg.value())
        print(value)
        # key, value = unpack(msg.key()), unpack(msg.value())
        # print(key, value)

if __name__ == '__main__':
    get_data()