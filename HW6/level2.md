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