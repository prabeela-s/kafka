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
 
