# Chapter 2 - Cryptography Fundamentals
This project is developed in Java and provides a complete solution for batch file encryption and decryption using the Advanced Encryption Standard (AES) algorithm. It supports AES-128, AES-192, and AES-256 with flexible key generation and management.

Key features include a graphical user interface (GUI) for easy interactive operations, as well as a console mode that enables scripting and automation. The tool allows users to generate secure random AES keys (stored as Base64), encrypt or decrypt all files in a specified source directory, and output results to a designated target directory.

The application supports the use of environment variables for configuration, making it easy to integrate into automated workflows or containerized environments, such as Docker. Additionally, it is compatible with major desktop platforms (Windows, Linux, macOS) and can run inside a Java development container. 

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
