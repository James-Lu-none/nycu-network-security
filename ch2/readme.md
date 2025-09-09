# Chapter 2 - Cryptography Fundamentals

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
