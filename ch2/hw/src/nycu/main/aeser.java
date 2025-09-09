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
        final String choice = input.nextLine();
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
        String path = System.getenv("AES_KEY_FILE_PATH");
        String key = null;
        if (path == null) {
            System.out.println("Enter data directory path:");
            path = input.nextLine();
        }
        final File file = new File(path);
        if (!file.exists()) {
			if (file.isDirectory()) {
				System.out.println("Cipher path is not a directory. Please delete it and try again.");
				return null;
			}
			try {
				System.out.println("Key file not found. Generating new key file at: " + file.getPath());
				if (file.createNewFile()) {
					key = generateRandomAesKey();
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

	public static String generateRandomAesKey() {
		try {
			final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			final SecretKey secretKey = keyGen.generateKey();
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		} catch (final Exception e) {
			throw new RuntimeException("Error generating AES key", e);
		}
	}

    public static File getDataDir() {
        String path = System.getenv("AES_DATA_DIR");
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
        String path = System.getenv("AES_CIPHER_DIR");
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
            if (key == null) return;

            final File dataDir = getDataDir();
            final File cipherDir = getCipherDir();
            if (dataDir == null || cipherDir == null) return;

            final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            final File[] files = dataDir.listFiles();
            if (files == null) {
                System.out.println("No files found in data directory.");
                return;
            }

			for (final File file : files) {
				if (!file.isFile()) continue;

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
			if (key == null) return;

			final File cipherDir = getCipherDir();
			final File dataDir = getDataDir();
			if (cipherDir == null || dataDir == null) return;

			final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			final File[] files = cipherDir.listFiles();
			if (files == null) {
				System.out.println("No files found in cipher directory.");
				return;
			}

            for (final File file : files) {
                if (!file.isFile() || !file.getName().endsWith(".enc")) continue;

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
}
