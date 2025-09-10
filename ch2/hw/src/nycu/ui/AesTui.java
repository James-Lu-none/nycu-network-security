package nycu.ui;

import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import static nycu.core.AesCore.*;

public class AesTui {

    public final static int AES_KEY_LENGTH = 256;
    public static final Scanner input = new Scanner(System.in);

    public static String getAesKey() {
        String path = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
        String key = null;
        if (path == null) {
            System.out.println("Enter AES key file path:");
            path = input.nextLine();
        }
        final File file = new File(path);
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
        String path = System.getenv(AES_DATA_DIR_ENV_NAME);
        if (path == null) {
            System.out.println("Enter data directory path:");
            path = input.nextLine();
        }
        final File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Data directory created: " + dir.getPath());
            } else {
                System.out.println("Failed to create data directory.");
                return null;
            }
        }
        if (!dir.isDirectory()) {
            System.out.println("data path is not a directory.");
            return null;
        }
        return dir;
    }

    public static File getCipherDir() {
        String path = System.getenv(AES_CIPHER_DIR_ENV_NAME);
        if (path == null) {
            System.out.println("Enter cipher directory path:");
            path = input.nextLine();
        }
        final File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Cipher directory created: " + dir.getPath());
            } else {
                System.out.println("Failed to create cipher directory.");
                return null;
            }
        }
        if (!dir.isDirectory()) {
            System.out.println("Cipher path is not a directory.");
            return null;
        }
        return dir;
    }

    public static void encryptor() {
        try {
            final String key = getAesKey();
            final File dataDir = getDataDir();
            final File cipherDir = getCipherDir();

            int count = encryptFiles(key, cipherDir, dataDir, System.out::println);
        } catch (final Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
        }
    }

    public static void decryptor() {
        try {
            final String key = getAesKey();
            final File dataDir = getDataDir();
            final File cipherDir = getCipherDir();

            int count = decryptFiles(key, cipherDir, dataDir, System.out::println);
        } catch (Exception e) {
            System.out.println("Error during decryption: " + e.getMessage());
        }
    }

}
