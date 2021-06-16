based on https://www.confluent.io/blog/kafka-elasticsearch-connector-tutorial/

```
 sudo apt install kafkacat
```

```
touch external.supplier.product.listings
nano external.supplier.product.listings
```

```
{
  "schema": {
    "type": "struct",
    "fields": [
      {
        "type": "string",
        "optional": false,
        "field": "productName"
      },
      {
        "type": "string",
        "optional": false,
        "field": "description"
      },
      {
        "type": "string",
        "optional": false,
        "field": "price"
      }
    ],
  },
  "payload": {
    "productName": "Bagotte BG600 Robot Vacuum Cleaner",
    "description": "Bagotte 1500 Pa powerful vacuum cleaner robot, easy to access every corner of the kitchen.",
    "price": 199.9
  }
}
```

```
kafkacat -P -b localhost:9092 -t external.supplier.schema.product.listings -K: <<EOF

13240212:{"schema":{"type":"struct","fields":[{"type":"string","optional":false,"field":"productName"},{"type":"string","optional":false,"field":"description"},{"type":"string","optional":false,"field":"price"}]},"payload":{"productName":"Bagotte BG600 Robot Vacuum Cleaner","description":"Bagotte 1500 Pa powerful vacuum cleaner robot, easy to access every corner of the kitchen.","price":"199.99"}} 13300212:{"schema":{"type":"struct","fields":[{"type":"string","optional":false,"field":"productName"},{"type":"string","optional":false,"field":"description"},{"type":"string","optional":false,"field":"price"}]},"payload":{"productName":"Shark NV601UKT Upright Vacuum Cleaner","description":"Pet vacuum - pet power brush attachment easily removes pet hair from carpets, stairs and sofas: Hose stretches up to 2.15 m.","price":"9.99"}}
EOF

```
