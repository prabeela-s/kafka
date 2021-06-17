# Log Compaction

open a command prompt, create a topic with compaction enabled.. 

```
confluent local destroy
```

Now run zookeeper.bat and broker-0.bat on windows....

run all the below commands on windows cmd prompt , NOT ON Linux
 
```
    kafka-topics  --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic settings --config cleanup.policy=compact --config delete.retention.ms=60000 --config segment.ms=60000 --config min.cleanable.dirty.ratio=0.01   
```

```
    kafka-topics --list --bootstrap-server localhost:9092
```    
   
```    
    kafka-topics --describe  --bootstrap-server localhost:9092  --topic settings
```


open a command prompt and run producer with key value delimited by colon :

```
notes: enter some text and press enter key, each line is consider as one message

kafka-console-producer --broker-list localhost:9092 --topic settings --property "parse.key=true" --property "key.separator=:"
```

left side colon represent key, right of colon represent value.. 

```
machine1.power:on
machine1.temp:18
machine1.fanspeed:3
machine1.fanmode:swing
machine2.power:off
machine2.temp:24
machine2.fanspeed:5
machine2.fanmode:fixed
machine1.temp:20
machine1.fanspeed:2
machine1.fanmode:fixed
machine2.power:on
machine1.temp:19
machine1.fanspeed:1
machine1.fanmode:rotate
```


note: open 4th Command Prompt

listen for the messages published/latest

run this, and do Ctrl + C, then run again, then do Ctrl + C, run again.. to check whether messages are really removed or not..
 

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic settings --from-beginning --property print.key=true
```

CRASH with compaction deletion


ERROR Failed to clean up log for settings-1 in dir C:\tmp\kafka-logs due to IOException (kafka.server.LogDirFailureChannel)

[2021-06-17 16:04:20,321] ERROR Failed to clean up log for settings-1 in dir C:\tmp\kafka-logs due to IOException (kafka.server.LogDirFailureChannel)
java.nio.file.FileSystemException: C:\tmp\kafka-logs\settings-1\00000000000000000000.timeindex.cleaned: The process cannot access the file because it is being used by another process.

        at sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:86)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:97)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:102)
        at sun.nio.fs.WindowsFileSystemProvider.implDelete(WindowsFileSystemProvider.java:269)
        at sun.nio.fs.AbstractFileSystemProvider.deleteIfExists(AbstractFileSystemProvider.java:108)
        at java.nio.file.Files.deleteIfExists(Files.java:1165)
        at kafka.log.Log$.deleteFileIfExists(Log.scala:2800)
        at kafka.log.LogSegment$.deleteIfExists(LogSegment.scala:669)
        at kafka.log.LogCleaner$.createNewCleanedSegment(LogCleaner.scala:465)
        at kafka.log.Cleaner.cleanSegments(LogCleaner.scala:576)
        at kafka.log.Cleaner.$anonfun$doClean$6(LogCleaner.scala:548)
        at kafka.log.Cleaner.$anonfun$doClean$6$adapted(LogCleaner.scala:547)
        at scala.collection.immutable.List.foreach(List.scala:392)
        at kafka.log.Cleaner.doClean(LogCleaner.scala:547)
        at kafka.log.Cleaner.clean(LogCleaner.scala:521)
        at kafka.log.LogCleaner$CleanerThread.cleanLog(LogCleaner.scala:384)
        at kafka.log.LogCleaner$CleanerThread.cleanFilthiestLog(LogCleaner.scala:343)
        at kafka.log.LogCleaner$CleanerThread.tryCleanFilthiestLog(LogCleaner.scala:324)
        at kafka.log.LogCleaner$CleanerThread.doWork(LogCleaner.scala:313)
        at kafka.utils.ShutdownableThread.run(ShutdownableThread.scala:96)
[2021-06-17 16:04:20,361] ERROR Failed to clean up log for settings-1 in dir C:\tmp\kafka-logs due to IOException (kafka.server.LogDirFailureChannel)
java.nio.file.FileSystemException: C:\tmp\kafka-logs\settings-1\00000000000000000000.timeindex.cleaned: The process cannot access the file because it is being used by another process.

        at sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:86)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:97)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:102)
        at sun.nio.fs.WindowsFileSystemProvider.implDelete(WindowsFileSystemProvider.java:269)
        at sun.nio.fs.AbstractFileSystemProvider.deleteIfExists(AbstractFileSystemProvider.java:108)
        at java.nio.file.Files.deleteIfExists(Files.java:1165)
        at kafka.log.Log$.deleteFileIfExists(Log.scala:2800)
        at kafka.log.LogSegment$.deleteIfExists(LogSegment.scala:669)
        at kafka.log.LogCleaner$.createNewCleanedSegment(LogCleaner.scala:465)
        at kafka.log.Cleaner.cleanSegments(LogCleaner.scala:576)
        at kafka.log.Cleaner.$anonfun$doClean$6(LogCleaner.scala:548)
        at kafka.log.Cleaner.$anonfun$doClean$6$adapted(LogCleaner.scala:547)
        at scala.collection.immutable.List.foreach(List.scala:392)
        at kafka.log.Cleaner.doClean(LogCleaner.scala:547)
        at kafka.log.Cleaner.clean(LogCleaner.scala:521)
        at kafka.log.LogCleaner$CleanerThread.cleanLog(LogCleaner.scala:384)
        at kafka.log.LogCleaner$CleanerThread.cleanFilthiestLog(LogCleaner.scala:343)
        at kafka.log.LogCleaner$CleanerThread.tryCleanFilthiestLog(LogCleaner.scala:324)
        at kafka.log.LogCleaner$CleanerThread.doWork(LogCleaner.scala:313)
        at kafka.utils.ShutdownableThread.run(ShutdownableThread.scala:96)
[2021-06-17 16:04:20,394] ERROR Failed to clean up log for settings-1 in dir C:\tmp\kafka-logs due to IOException (kafka.server.LogDirFailureChannel)
java.nio.file.FileSystemException: C:\tmp\kafka-logs\settings-1\00000000000000000000.timeindex.cleaned: The process cannot access the file because it is being used by another process.

        at sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:86)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:97)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:102)
        at sun.nio.fs.WindowsFileSystemProvider.implDelete(WindowsFileSystemProvider.java:269)
        at sun.nio.fs.AbstractFileSystemProvider.deleteIfExists(AbstractFileSystemProvider.java:108)
        at java.nio.file.Files.deleteIfExists(Files.java:1165)
        at kafka.log.Log$.deleteFileIfExists(Log.scala:2800)
        at kafka.log.LogSegment$.deleteIfExists(LogSegment.scala:669)
        at kafka.log.LogCleaner$.createNewCleanedSegment(LogCleaner.scala:465)
        at kafka.log.Cleaner.cleanSegments(LogCleaner.scala:576)
        at kafka.log.Cleaner.$anonfun$doClean$6(LogCleaner.scala:548)
        at kafka.log.Cleaner.$anonfun$doClean$6$adapted(LogCleaner.scala:547)
        at scala.collection.immutable.List.foreach(List.scala:392)
        at kafka.log.Cleaner.doClean(LogCleaner.scala:547)
        at kafka.log.Cleaner.clean(LogCleaner.scala:521)
        at kafka.log.LogCleaner$CleanerThread.cleanLog(LogCleaner.scala:384)
        at kafka.log.LogCleaner$CleanerThread.cleanFilthiestLog(LogCleaner.scala:343)
        at kafka.log.LogCleaner$CleanerThread.tryCleanFilthiestLog(LogCleaner.scala:324)
        at kafka.log.LogCleaner$CleanerThread.doWork(LogCleaner.scala:313)
        at kafka.utils.ShutdownableThread.run(ShutdownableThread.scala:96)
[2021-06-17 16:04:20,430] ERROR Failed to clean up log for settings-1 in dir C:\tmp\kafka-logs due to IOException (kafka.server.LogDirFailureChannel)
java.nio.file.FileSystemException: C:\tmp\kafka-logs\settings-1\00000000000000000000.timeindex.cleaned: The process cannot access the file because it is being used by another process.

        at sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:86)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:97)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:102)
        at sun.nio.fs.WindowsFileSystemProvider.implDelete(WindowsFileSystemProvider.java:269)
        at sun.nio.fs.AbstractFileSystemProvider.deleteIfExists(AbstractFileSystemProvider.java:108)
        at java.nio.file.Files.deleteIfExists(Files.java:1165)
        at kafka.log.Log$.deleteFileIfExists(Log.scala:2800)
        at kafka.log.LogSegment$.deleteIfExists(LogSegment.scala:669)
        at kafka.log.LogCleaner$.createNewCleanedSegment(LogCleaner.scala:465)
        at kafka.log.Cleaner.cleanSegments(LogCleaner.scala:576)
        at kafka.log.Cleaner.$anonfun$doClean$6(LogCleaner.scala:548)
        at kafka.log.Cleaner.$anonfun$doClean$6$adapted(LogCleaner.scala:547)
        at scala.collection.immutable.List.foreach(List.scala:392)
        at kafka.log.Cleaner.doClean(LogCleaner.scala:547)
        at kafka.log.Cleaner.clean(LogCleaner.scala:521)
        at kafka.log.LogCleaner$CleanerThread.cleanLog(LogCleaner.scala:384)
        at kafka.log.LogCleaner$CleanerThread.cleanFilthiestLog(LogCleaner.scala:343)
        at kafka.log.LogCleaner$CleanerThread.tryCleanFilthiestLog(LogCleaner.scala:324)
        at kafka.log.LogCleaner$CleanerThread.doWork(LogCleaner.scala:313)
        at kafka.utils.ShutdownableThread.run(ShutdownableThread.scala:96)
[2021-06-17 16:04:20,474] ERROR Failed to clean up log for settings-1 in dir C:\tmp\kafka-logs due to IOException (kafka.server.LogDirFailureChannel)
java.nio.file.FileSystemException: C:\tmp\kafka-logs\settings-1\00000000000000000000.timeindex.cleaned: The process cannot access the file because it is being used by another process.

        at sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:86)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:97)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:102)
        at sun.nio.fs.WindowsFileSystemProvider.implDelete(WindowsFileSystemProvider.java:269)
        at sun.nio.fs.AbstractFileSystemProvider.deleteIfExists(AbstractFileSystemProvider.java:108)
        at java.nio.file.Files.deleteIfExists(Files.java:1165)
        at kafka.log.Log$.deleteFileIfExists(Log.scala:2800)
        at kafka.log.LogSegment$.deleteIfExists(LogSegment.scala:669)
        at kafka.log.LogCleaner$.createNewCleanedSegment(LogCleaner.scala:465)
        at kafka.log.Cleaner.cleanSegments(LogCleaner.scala:576)
        at kafka.log.Cleaner.$anonfun$doClean$6(LogCleaner.scala:548)
        at kafka.log.Cleaner.$anonfun$doClean$6$adapted(LogCleaner.scala:547)
        at scala.collection.immutable.List.foreach(List.scala:392)
        at kafka.log.Cleaner.doClean(LogCleaner.scala:547)
        at kafka.log.Cleaner.clean(LogCleaner.scala:521)
        at kafka.log.LogCleaner$CleanerThread.cleanLog(LogCleaner.scala:384)
        at kafka.log.LogCleaner$CleanerThread.cleanFilthiestLog(LogCleaner.scala:343)
        at kafka.log.LogCleaner$CleanerThread.tryCleanFilthiestLog(LogCleaner.scala:324)
        at kafka.log.LogCleaner$CleanerThread.doWork(LogCleaner.scala:313)
        at kafka.utils.ShutdownableThread.run(ShutdownableThread.scala:96)
[2021-06-17 16:04:20,535] ERROR Failed to clean up log for settings-1 in dir C:\tmp\kafka-logs due to IOException (kafka.server.LogDirFailureChannel)
java.nio.file.FileSystemException: C:\tmp\kafka-logs\settings-1\00000000000000000000.timeindex.cleaned: The process cannot access the file because it is being used by another process.

        at sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:86)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:97)
        at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:102)
        at sun.nio.fs.WindowsFileSystemProvider.implDelete(WindowsFileSystemProvider.java:269)
        at sun.nio.fs.AbstractFileSystemProvider.deleteIfExists(AbstractFileSystemProvider.java:108)
        at java.nio.file.Files.deleteIfExists(Files.java:1165)
        at kafka.log.Log$.deleteFileIfExists(Log.scala:2800)
        at kafka.log.LogSegment$.deleteIfExists(LogSegment.scala:669)
        at kafka.log.LogCleaner$.createNewCleanedSegment(LogCleaner.scala:465)
        at kafka.log.Cleaner.cleanSegments(LogCleaner.scala:576)
        at kafka.log.Cleaner.$anonfun$doClean$6(LogCleaner.scala:548)
        at kafka.log.Cleaner.$anonfun$doClean$6$adapted(LogCleaner.scala:547)
        at scala.collection.immutable.List.foreach(List.scala:392)
java.nio.file.FileSystemException: C:\tmp\kafka-logs\settings-1\00000000000000000000.timeindex.cleaned: The process cannot access the file because it is being used by another process.
