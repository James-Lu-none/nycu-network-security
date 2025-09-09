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
export AES_KEY=1234123412341234
export AES_DATA_FILE_PATH=a.txt
export AES_CIPHER_FILE_PATH=output
```

## working java container

```
docker run -v ./hw:/app/hw -it openjdk:21-jdk-slim bash
```
