# Chapter 2 - Cryptography Fundamentals
This project is developed in Java and provides a complete solution for batch file encryption and decryption using the Advanced Encryption Standard (AES) algorithm. It supports AES-128, AES-192, and AES-256 with flexible key generation and management.

Key features include a graphical user interface (GUI) for easy interactive operations, as well as a console mode that enables scripting and automation. The tool allows users to generate secure random AES keys (stored as Base64), encrypt or decrypt all files in a specified source directory, and output results to a designated target directory. We also display real-time logs in the GUI, showing detailed status messages and errors during encryption/decryption processes

The application supports the use of environment variables for configuration, making it easy to integrate into automated workflows or containerized environments, such as Docker. Additionally, it is compatible with major desktop platforms (Windows, Linux, macOS) and can run inside a Java development container. 

## GUI Mode
### Feature of GUI Mode
* User-friendly interface to configure AES key file, data directory, and cipher output directory
* Supports file and directory selection using native system file chooser dialogs (JFileChooser)
* Support recursive file encryption and decryption as an option
* Allows direct drag-and-drop of files or folders from the system file explorer into the input fields for quick path setting
* Automatic environment variable detection and loading for key and directory paths with toggle option
* Encryption and decryption operation buttons that enable multi-threaded processing
* Real-time log display area showing detailed status updates and error messages during operations
* Key length selection combobox with options for 128, 192, and 256-bit AES keys
* Clear log button for log management

![GUI_interface](https://github.com/James-Lu-none/nycu-network-security/blob/main/ch2/GUI_interface.png)

### Generate 16 bytes Key

![GUI_genkey](https://github.com/James-Lu-none/nycu-network-security/blob/main/ch2/GUI_genkey.png)

### Encrypt File

![GUI_ecryptfile](https://github.com/James-Lu-none/nycu-network-security/blob/main/ch2/encryptfile.png)

### Decrypt File

![GUI_decryptfile](https://github.com/James-Lu-none/nycu-network-security/blob/main/ch2/decryptfile.png)


## Console Mode




## build

```
javac -d bin src/nycu/**/*.java
```

## run

```
java -cp bin nycu.main.hasher
```

## set up env

```
export AES_KEY_FILE_PATH=key
export AES_DATA_DIR=data
export AES_CIPHER_DIR=cipher
```

## working java container

```
docker run -v ./hw:/app/hw -it openjdk:21-jdk-slim bash
```
