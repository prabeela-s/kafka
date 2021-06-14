# ibm-kafka-june-2021

open C:\confluent-5.5.1\bin\windows\kafka-run-class.bat in notepad++ (right click, edit with notepad++)

paste below line around line 45 

```
rem class path patch for kafka on windows
if exist %BASE_DIR%\share\java\kafka\* (
call:concat %BASE_DIR%\share\java\kafka\*
)
```
