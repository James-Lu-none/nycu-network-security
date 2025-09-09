package nycu.main;

import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.io.File;

public class aeser {

	public static final Scanner input = new Scanner(System.in);

	// envKey = System.getenv("AES_KEY");
	// dataFilePath = System.getenv("AES_DATA_FILE_PATH");
	// cipherFilePath = System.getenv("AES_CIPHER_FILE_PATH");
	public static String envKey = null;
	public static String dataFilePath = null;
	public static String cipherFilePath = null;

	public static void main(String[] args) {
		System.out.println("Welcome to use NYCU AESer.");
		getEnv();
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
	public static void getEnv() {
		envKey = System.getenv("AES_KEY");
		dataFilePath = System.getenv("AES_DATA_FILE_PATH");
		cipherFilePath = System.getenv("AES_CIPHER_FILE_PATH");
		if (envKey != null) {
			if (envKey.length() != 16 && envKey.length() != 24 && envKey.length() != 32) {
				System.out.println("Environment variable AES_KEY is set but its length is not 16, 24 or 32.");
				envKey = null;
			}
		}
		if (dataFilePath != null) {
			File dataFile = new File(dataFilePath);
			if (!dataFile.exists()) {
				System.out.println("Environment variable AES_DATA_FILE_PATH is set but the file does not exist.");
				dataFilePath = null;
			}
		}
	}
	public static void encryptor() {
		String key;
		File dataFile;
		if (envKey != null) {
			key = envKey;
			System.out.println("Using key from environment variable.");
		} else {
			System.out.println("please input your key:");
			System.out.println("Key should be 16, 24 or 32 characters long.");
			key = input.nextLine();
		}
		if (dataFilePath != null) {
			System.out.println("Using source file path from environment variable.");
			dataFile = new File(dataFilePath);
		} else {
			System.out.println("please input file path:");
			dataFilePath = input.nextLine();
			dataFile = new File(dataFilePath);
			if (!dataFile.exists()) {
				System.out.println("Source file does not exist.");
				dataFilePath = null;
			}
		}
		File cipherFile = new File(cipherFilePath);

		try {
			// Read file content
			byte[] fileBytes = java.nio.file.Files.readAllBytes(dataFile.toPath());
			final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encrypted = cipher.doFinal(fileBytes);
			// Write encrypted data to cipher file (Base64 encoded)
			String encoded = Base64.getEncoder().encodeToString(encrypted);
			java.nio.file.Files.write(cipherFile.toPath(), encoded.getBytes("UTF-8"));
			System.out.println("Encryption complete. Encrypted data written to: " + cipherFile.getPath());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void decryptor() {
		String key;
		if (envKey != null) {
			key = envKey;
			System.out.println("Using key from environment variable.");
		} else {
			System.out.println("please input your key:");
			System.out.println("Key should be 16, 24 or 32 characters long.");
			key = input.nextLine();
		}
		System.out.println("please input your ciphertext:");
		String ciphertext = input.nextLine();

		try {
			final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
			System.out.println("Decrypted data:");
			System.out.println(new String(decrypted, "UTF-8"));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
