package nycu.ui;

import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import static nycu.core.AesCore.*;

public final class AesTui {

    public static final int AES_KEY_LENGTH = 256;
    public static boolean recursive;
    public static final Scanner input = new Scanner(System.in);

    public static String getAesKey() {
        final String path = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
        String key = null;
        String keyPath = path;
        if (keyPath == null) {
            System.out.println("Enter AES key file path:");
            keyPath = input.nextLine();
        }
        final File file = new File(keyPath);
        if (file.exists()) {
            try {
                final byte[] keyBytes = Files.readAllBytes(file.toPath());
                key = new String(keyBytes).trim();
            } catch (final Exception e) {
                System.out.println("Failed to read key from file: " + e.getMessage());
                return null;
            }
        } else {
            if (file.isDirectory()) {
                System.out.println("Entered path is a directory. Please delete it and try again.");
                return null;
            }
            try {
                System.out.println("Key file not found. Generating new key file at: " + file.getPath());
                if (file.createNewFile()) {
                    key = generateRandomAesKey(AES_KEY_LENGTH);
                    Files.write(file.toPath(), key.getBytes());
                    System.out.println("Key file generated.");
                } else {
                    System.out.println("Failed to create key file.");
                    return null;
                }
            } catch (final Exception e) {
                System.out.println("Error creating key file: " + e.getMessage());
                return null;
            }
        }
        return key;
    }

    public static File getDataDir() {
        final String path = System.getenv(AES_DATA_DIR_ENV_NAME);
        String dirPath = path;
        if (dirPath == null) {
            System.out.println("Enter data directory path:");
            dirPath = input.nextLine();
        }
        final File dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Data directory created: " + dir.getPath());
                return dir;
            } else {
                System.out.println("Failed to create data directory.");
                return null;
            }
        }
        return dir;
    }

    public static File getCipherDir() {
        final String path = System.getenv(AES_CIPHER_DIR_ENV_NAME);
        String dirPath = path;
        if (dirPath == null) {
            System.out.println("Enter cipher directory path:");
            dirPath = input.nextLine();
        }
        final File dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Cipher directory created: " + dir.getPath());
            } else {
                System.out.println("Failed to create cipher directory.");
                return null;
            }
        }
        return dir;
    }

    public static void askEnableRecursive(){
        System.out.println("Do you want to encrypt/decrypt files recursively? (y/n)");
        final String choice = input.nextLine();
        recursive = "y".equals(choice);
    }

    public static void encryptor() {
        askEnableRecursive();
        try {
            final String key = getAesKey();
            final File dataDir = getDataDir();
            final File cipherDir = getCipherDir();

            encryptFiles(key, dataDir, cipherDir, recursive, System.out::println);
        } catch (final Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
        }
    }

    public static void decryptor() {
        askEnableRecursive();
        try {
            final String key = getAesKey();
            final File dataDir = getDataDir();
            final File cipherDir = getCipherDir();

            decryptFiles(key, dataDir, cipherDir, recursive, System.out::println);
        } catch (final Exception e) {
            System.out.println("Error during decryption: " + e.getMessage());
        }
    }

}
