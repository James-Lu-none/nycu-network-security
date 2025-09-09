package nycu.main;

import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.io.File;
import java.nio.file.Files;

public class aeser {

    public static final Scanner input = new Scanner(System.in);

	// System.getenv("AES_KEY_FILE_PATH");
	// System.getenv("AES_DATA_DIR");
	// System.getenv("AES_CIPHER_DIR");

    public static void main(String[] args) {
        System.out.println("Welcome to use NYCU AESer.");
        System.out.println("1. Run AES encryptor");
        System.out.println("2. Run AES decryptor");
        System.out.println("Enter your choice: ");
        String choice = input.nextLine();
        switch (choice.charAt(0)) {
            case '1':
                encryptor();
                break;
            case '2':
                decryptor();
                break;
            default:
                System.out.println("Unknown choice.");
                System.exit(0);
        }
        input.close();
    }

    public static String getAesKey() {
        String envKey = System.getenv("AES_KEY");
        if (envKey == null) {
            System.out.println("Please input your key (16, 24, or 32 characters):");
            envKey = input.nextLine();
        }
        if (envKey.length() != 16 && envKey.length() != 24 && envKey.length() != 32) {
            System.out.println("Invalid key length. Must be 16, 24, or 32 characters.");
            return null;
        }
        return envKey;
    }

	public static String generateRandomAesKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			SecretKey secretKey = keyGen.generateKey();
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		} catch (Exception e) {
			throw new RuntimeException("Error generating AES key", e);
		}
	}

    public static File getDataDir() {
        String path = System.getenv("AES_DATA_DIR");
        if (path == null) {
            System.out.println("Enter data directory path:");
            path = input.nextLine();
        }
        File dir = new File(path);
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
        String path = System.getenv("AES_CIPHER_DIR");
        if (path == null) {
            System.out.println("Enter cipher directory path:");
            path = input.nextLine();
        }
        File dir = new File(path);
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
            String key = getAesKey();
            if (key == null) return;

            File dataDir = getDataDir();
            File cipherDir = getCipherDir();
            if (dataDir == null || cipherDir == null) return;

            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            File[] files = dataDir.listFiles();
            if (files == null) {
                System.out.println("No files found in data directory.");
                return;
            }

            for (File file : files) {
                if (!file.isFile()) continue;

                byte[] fileBytes = Files.readAllBytes(file.toPath());
                byte[] encrypted = cipher.doFinal(fileBytes);
                String encoded = Base64.getEncoder().encodeToString(encrypted);

                File outFile = new File(cipherDir, file.getName() + ".enc");
                Files.write(outFile.toPath(), encoded.getBytes("UTF-8"));
                System.out.println("Encrypted: " + file.getName() + " -> " + outFile.getPath());
            }
        } catch (Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
        }
	}

	public static void decryptor() {
        try {
            String key = getAesKey();
            if (key == null) return;

            File cipherDir = getCipherDir();
            File dataDir = getDataDir();
            if (cipherDir == null || dataDir == null) return;

            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            File[] files = cipherDir.listFiles();
            if (files == null) {
                System.out.println("No files found in cipher directory.");
                return;
            }

            for (File file : files) {
                if (!file.isFile() || !file.getName().endsWith(".enc")) continue;

                byte[] encodedBytes = Files.readAllBytes(file.toPath());
                byte[] encrypted = Base64.getDecoder().decode(new String(encodedBytes, "UTF-8"));
                byte[] decrypted = cipher.doFinal(encrypted);

                String baseName = file.getName().replaceFirst("\\.enc$", "");
                File outFile = new File(dataDir, baseName);
                Files.write(outFile.toPath(), decrypted);
                System.out.println("Decrypted: " + file.getName() + " -> " + outFile.getPath());
            }
        } catch (Exception e) {
            System.out.println("Error during decryption: " + e.getMessage());
        }
    }
}
