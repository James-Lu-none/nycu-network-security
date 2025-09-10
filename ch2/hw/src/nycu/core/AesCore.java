package nycu.core;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.function.Consumer;

public class AesCore {

    public static final Scanner input = new Scanner(System.in);
    
    public final static String AES_KEY_FILE_PATH_ENV_NAME = "AES_KEY_FILE_PATH";
    public final static String AES_DATA_DIR_ENV_NAME = "AES_DATA_DIR";
    public final static String AES_CIPHER_DIR_ENV_NAME = "AES_CIPHER_DIR";
    public final static int AES_KEY_LENGTH = 256;

    public static String getAesKey() {
        String path = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
        String key = null;
        if (path == null) {
            System.out.println("Enter AES key file path:");
            path = input.nextLine();
        }
        final File file = new File(path);
        if (!file.exists()) {
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
        if (path != null) {
            try {
                final byte[] keyBytes = Files.readAllBytes(file.toPath());
                key = new String(keyBytes).trim();
                if (!(key.length() == 16 || key.length() == 24 || key.length() == 32)) {
                    System.out.println("Key length is not 16, 24, or 32 characters.");
                    return null;
                }
            } catch (final Exception e) {
                System.out.println("Failed to read key from file: " + e.getMessage());
                return null;
            }
        }
        return key;
    }

    public static String generateRandomAesKey(int keyLength) {
        try {
            final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keyLength);
            final SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (final Exception e) {
            throw new RuntimeException("Error generating AES key", e);
        }
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
            if (key == null) {
                return;
            }

            final File dataDir = getDataDir();
            final File cipherDir = getCipherDir();
            if (dataDir == null || cipherDir == null) {
                return;
            }

            final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            final File[] files = dataDir.listFiles();
            if (files == null) {
                System.out.println("No files found in data directory.");
                return;
            }

            for (final File file : files) {
                if (!file.isFile()) {
                    continue;
                }

                final byte[] fileBytes = Files.readAllBytes(file.toPath());
                final byte[] encrypted = cipher.doFinal(fileBytes);
                final String encoded = Base64.getEncoder().encodeToString(encrypted);

                final File outFile = new File(cipherDir, file.getName() + ".enc");
                Files.write(outFile.toPath(), encoded.getBytes("UTF-8"));
                System.out.println("Encrypted: " + file.getName() + " -> " + outFile.getPath());
            }
        } catch (final Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
        }
    }

    public static void decryptor() {
        try {
            final String key = getAesKey();
            if (key == null) {
                return;
            }

            final File cipherDir = getCipherDir();
            final File dataDir = getDataDir();
            if (cipherDir == null || dataDir == null) {
                return;
            }

            final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            final File[] files = cipherDir.listFiles();
            if (files == null) {
                System.out.println("No files found in cipher directory.");
                return;
            }

            for (final File file : files) {
                if (!file.isFile() || !file.getName().endsWith(".enc")) {
                    continue;
                }

                final byte[] encodedBytes = Files.readAllBytes(file.toPath());
                final byte[] encrypted = Base64.getDecoder().decode(new String(encodedBytes, "UTF-8"));
                final byte[] decrypted = cipher.doFinal(encrypted);

                final String baseName = file.getName().replaceFirst("\\.enc$", "");
                final File outFile = new File(dataDir, baseName);
                Files.write(outFile.toPath(), decrypted);
                System.out.println("Decrypted: " + file.getName() + " -> " + outFile.getPath());
            }
        } catch (Exception e) {
            System.out.println("Error during decryption: " + e.getMessage());
        }
    }

    public static int encryptFiles(String key, File cipherDir, File dataDir, Consumer<String> logConsumer) throws Exception {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("AES key is missing.");
        }
        if (!cipherDir.exists() || !cipherDir.isDirectory()) {
            throw new IllegalArgumentException("Cipher directory is invalid.");
        }
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        File[] files = cipherDir.listFiles();
        if (files == null) {
            throw new IllegalStateException("No files found in cipher directory.");
        }

        final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        int count = 0;
        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }
            final byte[] fileBytes = Files.readAllBytes(file.toPath());
            final byte[] encrypted = cipher.doFinal(fileBytes);
            final String encoded = Base64.getEncoder().encodeToString(encrypted);

            File outFile = new File(dataDir, file.getName() + ".enc");
            Files.write(outFile.toPath(), encoded.getBytes("UTF-8"));

            logConsumer.accept("Encrypted: " + file.getName() + " -> " + outFile.getPath());
            count++;
        }

        logConsumer.accept("Encryption finished. Total " + count + " file(s).");
        return count;
    }

    public static int decryptFiles(String key, File cipherDir, File dataDir, Consumer<String> logConsumer) throws Exception {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("AES key is missing.");
        }
        if (!cipherDir.exists() || !cipherDir.isDirectory()) {
            throw new IllegalArgumentException("Cipher directory is invalid.");
        }
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        File[] files = cipherDir.listFiles();
        if (files == null) {
            throw new IllegalStateException("No files found in cipher directory.");
        }

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        int count = 0;
        for (File file : files) {
            if (!file.isFile() || !file.getName().endsWith(".enc")) {
                continue;
            }

            byte[] encodedBytes = Files.readAllBytes(file.toPath());
            byte[] encrypted = Base64.getDecoder().decode(new String(encodedBytes, "UTF-8"));
            byte[] decrypted = cipher.doFinal(encrypted);

            String baseName = file.getName().replaceFirst("\\.enc$", "");
            File outFile = new File(dataDir, baseName);
            Files.write(outFile.toPath(), decrypted);

            logConsumer.accept("Decrypted: " + file.getName() + " -> " + outFile.getPath());
            count++;
        }

        logConsumer.accept("Decryption finished. Total " + count + " file(s).");
        return count;
    }
}
