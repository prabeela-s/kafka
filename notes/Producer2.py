from kafka import KafkaProducer
producer = KafkaProducer(bootstrap_servers='localhost:9092',api_version=(0,11,5))
topic = 'test5'
data = "64 Good Night!!"
print(data)
future = producer.send(topic,data.encode('utf-8'))
future.get(timeout=10)
print(future)