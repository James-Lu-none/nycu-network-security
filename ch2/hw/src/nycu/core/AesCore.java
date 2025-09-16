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
    
    private static File createNewEncryptionFolder(File baseDir) {
        String timestamp = String.format("%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS", System.currentTimeMillis());
        File newDir = new File(baseDir, "encrypted_" + timestamp);
        if (!newDir.exists() && !newDir.mkdirs()) {
            throw new RuntimeException("無法創建新的加密目錄: " + newDir.getPath());
        }
        return newDir;
    }
    
    private static void encryptDirectory(SecretKeySpec secretKey, Cipher cipher, File sourceDir, File targetDir, String relativePath, Consumer<String> logConsumer) throws Exception {
        File currentTargetDir = new File(targetDir, relativePath);
        if (!currentTargetDir.exists() && !currentTargetDir.mkdirs()) {
            throw new IllegalStateException("無法創建目標目錄: " + currentTargetDir);
        }

        File currentSourceDir = new File(sourceDir, relativePath);
        File[] files = currentSourceDir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            String currentRelativePath = relativePath.isEmpty() ? file.getName() : relativePath + File.separator + file.getName();
            
            if (file.isDirectory()) {
                encryptDirectory(secretKey, cipher, sourceDir, targetDir, currentRelativePath, logConsumer);
            } else {
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                byte[] encrypted = cipher.doFinal(fileBytes);
                String encoded = Base64.getEncoder().encodeToString(encrypted);

                File outFile = new File(targetDir, currentRelativePath + ".enc");
                Files.write(outFile.toPath(), encoded.getBytes("UTF-8"));
                logConsumer.accept("已加密: " + currentRelativePath + " -> " + outFile.getName());
            }
        }
    }
    
    private static void decryptDirectory(SecretKeySpec secretKey, Cipher cipher, File sourceDir, File targetDir, String relativePath, Consumer<String> logConsumer) throws Exception {
        File currentTargetDir = new File(targetDir, relativePath);
        if (!currentTargetDir.exists() && !currentTargetDir.mkdirs()) {
            throw new IllegalStateException("無法創建目標目錄: " + currentTargetDir);
        }

        File currentSourceDir = new File(sourceDir, relativePath);
        File[] files = currentSourceDir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            String currentRelativePath = relativePath.isEmpty() ? file.getName() : relativePath + File.separator + file.getName();
            
            if (file.isDirectory()) {
                decryptDirectory(secretKey, cipher, sourceDir, targetDir, currentRelativePath, logConsumer);
            } else if (file.getName().endsWith(".enc")) {
                String content = Files.readString(file.toPath());
                byte[] encrypted = Base64.getDecoder().decode(content);
                byte[] decrypted = cipher.doFinal(encrypted);
                
                String outFileName = currentRelativePath.substring(0, currentRelativePath.length() - 4); // 移除 .enc
                File outFile = new File(targetDir, outFileName);
                Files.write(outFile.toPath(), decrypted);
                logConsumer.accept("已解密: " + currentRelativePath + " -> " + outFileName);
            }
        }
    }

    public static String generateRandomAesKey(final int keyLength) {
        try {
            final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keyLength);
            final SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (final Exception e) {
            throw new RuntimeException("Error generating AES base64Key", e);
        }
    }

    public static void encryptFiles(final String base64Key, final File dataDir, final File cipherDir, final Consumer<String> logConsumer) throws Exception {
        if (base64Key == null || base64Key.isEmpty()) {
            throw new IllegalArgumentException("AES base64Key is missing.");
        }
        final byte[] key;
        try {
            key = Base64.getDecoder().decode(base64Key);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Key file does not contain valid Base64-encoded data.", e);
        }
        if (!(key.length == 16 || key.length == 24 || key.length == 32)) {
            throw new IllegalArgumentException("AES key length is invalid: must be 16, 24, or 32 bytes, but found " + key.length);
        }

        if (!cipherDir.exists() || !cipherDir.isDirectory()) {
            if (!cipherDir.mkdirs()) {
                throw new IllegalArgumentException("無法創建加密目錄基礎路徑: " + cipherDir.getPath());
            }
        }
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            throw new IllegalArgumentException("Data directory is invalid.");
        }

        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // 在指定的加密目錄中創建新的時間戳記目錄
        File newEncryptionDir = createNewEncryptionFolder(cipherDir);
        logConsumer.accept("開始加密目錄: " + dataDir.getPath());
        logConsumer.accept("加密檔案將儲存至: " + newEncryptionDir.getPath());
        encryptDirectory(secretKey, cipher, dataDir, newEncryptionDir, "", logConsumer);
        logConsumer.accept("加密完成.");
    }

    public static void decryptFiles(final String base64Key, final File dataDir, final File cipherDir, final Consumer<String> logConsumer) throws Exception {
        if (base64Key == null || base64Key.isEmpty()) {
            throw new IllegalArgumentException("AES base64Key is missing.");
        }
        final byte[] key;
        try {
            key = Base64.getDecoder().decode(base64Key);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Key file does not contain valid Base64-encoded data.", e);
        }
        if (!(key.length == 16 || key.length == 24 || key.length == 32)) {
            throw new IllegalArgumentException("AES key length is invalid: must be 16, 24, or 32 bytes, but found " + key.length);
        }

        if (!cipherDir.exists() || !cipherDir.isDirectory()) {
            throw new IllegalArgumentException("Cipher directory is invalid.");
        }
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            throw new IllegalArgumentException("Data directory is invalid.");
        }

        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        
        logConsumer.accept("開始解密目錄: " + cipherDir.getPath());
        decryptDirectory(secretKey, cipher, cipherDir, dataDir, "", logConsumer);
        logConsumer.accept("解密完成.");
    }
}
