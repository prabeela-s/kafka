```
{
"invoice_no": 1232323,
"amount": 2345.65,
"customer_id": 543434343
} - 70 chars -x 2 bytes = 140 bytes per message

1000000 Messages, each 140 bytes = 133 MB 

   transger producer to broker 140 bytes 
    store 140 bytes, replicate 140 bytes 
    consumers read 140 btytes

   json jasonValue = load("jsonstr..")
      read   1232323 as text, then they convert to int before loading into python/java/js

----
AVRO - binary format, schema 

{ --> registed in schema registry
    invoice_no: int, // field 1 [4 bytes]
    amount: float, // field 2  [4 bytes]
    customer_id: int // field 3  [4 bytes]
} - 12 bytes

when avro serialize the message, 
try to serialize without field name in binary format 

stored as bytes in native type 1232323 2345.65  543434343
                                int[4]     float[4]   int [4]

11 MB size for 1 million messages 
no need to convert string to int, string to float here. 

```
