package nycu.core;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.function.Consumer;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesCore {
    
    public static final String AES_KEY_FILE_PATH_ENV_NAME = "AES_KEY_FILE_PATH";
    public static final String AES_DATA_DIR_ENV_NAME = "AES_DATA_DIR";
    public static final String AES_CIPHER_DIR_ENV_NAME = "AES_CIPHER_DIR";

    public static String generateRandomAesKey(final int keyLength) {
        try {
            final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keyLength);
            final SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (final Exception e) {
            throw new RuntimeException("Error generating AES key", e);
        }
    }

    public static int encryptFiles(final String key, final File dataDir, final File cipherDir, final Consumer<String> logConsumer) throws Exception {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("AES key is missing.");
        }
        final byte[] decodedKey;
        try {
            decodedKey = Base64.getDecoder().decode(key);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Key file does not contain valid Base64-encoded data.", e);
        }
        if (!(decodedKey.length == 16 || decodedKey.length == 24 || decodedKey.length == 32)) {
            throw new IllegalArgumentException("AES key length is invalid: must be 16, 24, or 32 bytes, but found " + decodedKey.length);
        }

        if (!cipherDir.exists() || !cipherDir.isDirectory()) {
            throw new IllegalArgumentException("Cipher directory is invalid.");
        }
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            throw new IllegalArgumentException("Data directory is invalid.");
        }

        final File[] files = dataDir.listFiles();
        if (files == null) {
            throw new IllegalStateException("No files found in data directory.");
        }

        final SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        int count = 0;
        for (final File file : files) {
            if (!file.isFile()) {
                continue;
            }
            final byte[] fileBytes = Files.readAllBytes(file.toPath());
            final byte[] encrypted = cipher.doFinal(fileBytes);
            final String encoded = Base64.getEncoder().encodeToString(encrypted);

            final File outFile = new File(cipherDir, file.getName() + ".enc");
            Files.write(outFile.toPath(), encoded.getBytes("UTF-8"));

            logConsumer.accept("Encrypted: " + file.getName() + " -> " + outFile.getPath());
            count++;
        }

        logConsumer.accept("Encryption finished. Total " + count + " file(s).");
        return count;
    }

    public static int decryptFiles(final String key, final File dataDir, final File cipherDir, final Consumer<String> logConsumer) throws Exception {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("AES key is missing.");
        }
        final byte[] decodedKey;
        try {
            decodedKey = Base64.getDecoder().decode(key);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Key file does not contain valid Base64-encoded data.", e);
        }
        if (!(decodedKey.length == 16 || decodedKey.length == 24 || decodedKey.length == 32)) {
            throw new IllegalArgumentException("AES key length is invalid: must be 16, 24, or 32 bytes, but found " + decodedKey.length);
        }

        if (!cipherDir.exists() || !cipherDir.isDirectory()) {
            throw new IllegalArgumentException("Cipher directory is invalid.");
        }
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            throw new IllegalArgumentException("Data directory is invalid.");
        }

        final File[] files = cipherDir.listFiles();
        if (files == null) {
            throw new IllegalStateException("No files found in cipher directory.");
        }

        final SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        int count = 0;
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

            logConsumer.accept("Decrypted: " + file.getName() + " -> " + outFile.getPath());
            count++;
        }

        logConsumer.accept("Decryption finished. Total " + count + " file(s) processed.");
        return count;
    }
}
