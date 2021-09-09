```
Kafka Messages can be deleted based on two policies
  1. retention period - create topic/alter topic/default from config
  		after certain period message shall be deleted

  2. retention bytes 
  		if topic message total size grows beyond a certain threshold,
  		  delete the messages


  -1 value, disabled

  3. Log compaction
  		use of key: partition
  					log compaction


Air conditioners, IoT, connected to internet

Remote - Control
				Settings - State machine

					On/Off
					FAN - Speed
					Swing mode
					Temp - 20 deg c

on every state change, we publish a kafka message to a topic,

key:"AC-1-fan"  value: {device-id: "AC-1", "fan": 2}  -- sep 01,2021, 9:00 AM
key:"AC-1-temp" value: {device-id: "AC-1", "temp": 20} -- sep 01,2021, 9:02 AM
key:"AC-1-fan" value: {device-id: "AC-1", "fan": 3} -- sep 01,2021, 9:05 AM
key:"AC-1-temp" value: {device-id: "AC-1", "temp": 18} -- sep 01,2021, 9:10 AM

------------------------------------------
7 days retention period, messages shall be deleted
{device-id: "AC-1", "fan": 2}     -- sep 08,2021, 9:01 AM - deleted from kafka
{device-id: "AC-1", "temp": 20} -- sep 08,2021, 9:02 AM
{device-id: "AC-1", "fan": 3} -- sep 08,2021, 9:05 AM
{device-id: "AC-1", "temp": 18} -- sep 08,2021, 9:10 AM

After 7 days, topics left with no meessages
-------------------
Log compaction - it retains last known key and value, delete everything else


key:"AC-1-fan" value: {device-id: "AC-1", "fan": 3}
key:"AC-1-temp" value: {device-id: "AC-1", "temp": 18}

Can I remove message from kafka, 
	1. not possible by any API
	2. But on the compacted topics, 
			if the value is null for the given key, message can be deleted

to delete message from topic on compaction,

key:"AC-1-fan" value: null
key:"AC-1-temp" value: null

the keys AC-1-fan, AC-1-temp shall be removed from the compaction topic

req: Historical and live data 
		maintain two topics

		topics: ticks-historical 

					contains retention perod - 2 weeks

		topics: ticks-live, only last knwon value
					compaction topics - that second data


how freq to check for compaction/compact the messages, server.properties or 
on the given topic

kafka-topics --alter --topic my_topic_name --zookeeper my_zookeeper:2181 --config cleanup.policy=compact

delete.retention.ms

kafka-topics --alter --topic my_topic_name --zookeeper my_zookeeper:2181 --config cleanup.policy=delete





```

