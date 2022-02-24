from confluent_kafka.avro import AvroConsumer
from confluent_kafka.avro.serializer import SerializerError


c = AvroConsumer({
    'bootstrap.servers': 'k1.training.sh:9092',
    'group.id': 'groupid',
    'schema.registry.url': 'http://k1.training.sh:8081',
    'auto.offset.reset': 'earliest'
    })

# c.subscribe(['avro-python'])
c.subscribe(['db_products'])

while True:
    try:
        msg = c.poll(10)

    except SerializerError as e:
        print("Message deserialization failed for {}: {}".format(msg, e))
        break

    if msg is None:
        continue

    if msg.error():
        print("AvroConsumer error: {}".format(msg.error()))
        continue

    print(msg.value())

c.close()