```

Infrastructures 
    Cloud
    onPremise 

    Dedicated HDD 
            7200 RPM
                A typical 7200 RPM HDD will deliver a read/write speed of 80-160MB/s
            SSD 
            NVEMe 
    RAID storage

    --
    NAS - 10 mbps to 300 mbps 
    SAS
    clusterFS
    ES/Disks etc

    File IO

    Network IO
         1 gpbs vs 10 gbps vs 20 gbps

         1 gpbs 
                125 Megabytes/sec [shared]

                50 MB per sec

------

stakeholders 
    micro services 
    logs
    ETL - Kafka connect
        DB integration
        OLTP To OLAP 
        OLTP to DataLake
        OLAP to OLTP 
        DateaLake to OLTP 

    Producerd - 50 MB to 300 MB per sec

    some topic may have replications - async 
        5 MB replicated to 5 more - 25 MB per sec - network io
        5 MB no replicas - 5 MB 
                         -------------------------
                            500 MB Sec Network IO - 10 VMs assumping 50 MB /sec 
    Consumeer 
        Spark
        Kafka stream 
        Nifi
        Apache Flink - stream
        Batch processing 
        Miror makers 

    Ack 1, all 

    Kafka connect
```
 
