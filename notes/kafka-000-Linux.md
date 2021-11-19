Windows WSL - Ubuntu 

Confluent enterprise is installed

username: bigdata
password: bigdata

```
wsl.exe -u bigdata


cd /home/bigdata
```

/home/bigdata is home directory..



Open Command Prompt

```
c:> hostname
```

Make note of hostname displayed 

On Windows


Open C:\Windows\System32\drivers\etc\hosts file in notepad++


add below entry to hosts file and save the file

```
127.0.0.1       <<yourhostname>>.localdomain 
```

example may look like

127.0.0.1       WIN-9KB2D8HA2DF.localdomain


