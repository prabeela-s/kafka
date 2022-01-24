List installed Linux Subsystem

```
wslconfig /list /all


```

Terminal WSL

```
wsl.exe -t <DistroName>
```
  
  

Restart WSL,

in Windows run `services.msc`, rstart LxssManager service

or in Power shell

```
Get-Service LxssManager | Restart-Service
```

or using net command 

```
net stop LxssManager
net start LxssManager
```
 
### wsl.conf

located in `/etc`

/etc/wsl.conf

# installing SSH

https://www.illuminiastudios.com/dev-diaries/ssh-on-windows-subsystem-for-linux/


sudo apt install openssh-server

sudo nano /etc/ssh/sshd_config

change

```
PasswordAuthentication yes
```

service ssh status


sudo service ssh start

sudo service ssh --full-restart


