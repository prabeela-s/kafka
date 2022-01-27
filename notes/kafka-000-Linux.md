Windows WSL - Ubuntu 

Confluent enterprise is installed

username: training
password: training

```
wsl.exe -u training


cd /home/training
```

/home/training is home directory..



Open Command Prompt

```
c:> hostname
```

Make note of hostname displayed 

On Windows


Open C:\Windows\System32\drivers\etc\hosts file in notepad

```
notepad C:\Windows\System32\drivers\etc\hosts
```


add below entry to hosts file and save the file

```
127.0.0.1       <<yourhostname>>.localdomain 
```

example may look like

127.0.0.1       WIN-9KB2D8HA2DF.localdomain


