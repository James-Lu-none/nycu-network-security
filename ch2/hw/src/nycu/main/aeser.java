package nycu.main;

import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.io.File;

public class aeser {

	public static final Scanner input = new Scanner(System.in);

	// envKey = System.getenv("AES_KEY");
	// srcFilePath = System.getenv("AES_SRC_FILE_PATH");
	// dstFilePath = System.getenv("AES_DST_FILE_PATH");
	public static String envKey = null;
	public static String srcFilePath = null;
	public static String dstFilePath = null;

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
		srcFilePath = System.getenv("AES_SRC_FILE_PATH");
		dstFilePath = System.getenv("AES_DST_FILE_PATH");
		if (envKey != null) {
			if (envKey.length() != 16 && envKey.length() != 24 && envKey.length() != 32) {
				System.out.println("Environment variable AES_KEY is set but its length is not 16, 24 or 32.");
				envKey = null;
			}
		}
		if (srcFilePath != null) {
			File srcFile = new File(srcFilePath);
			if (!srcFile.exists()) {
				System.out.println("Environment variable AES_SRC_FILE_PATH is set but the file does not exist.");
				srcFilePath = null;
			}
		}
	}
	public static void encryptor() {
		String key;
		File srcFile;
		if (envKey != null) {
			key = envKey;
			System.out.println("Using key from environment variable.");
		} else {
			System.out.println("please input your key:");
			System.out.println("Key should be 16, 24 or 32 characters long.");
			key = input.nextLine();
		}
		if (srcFilePath != null) {
			System.out.println("Using source file path from environment variable.");
			srcFile = new File(srcFilePath);
		} else {
			System.out.println("please input file path:");
			srcFilePath = input.nextLine();
			srcFile = new File(srcFilePath);
			if (!srcFile.exists()) {
				System.out.println("Source file does not exist.");
				srcFilePath = null;
			}
		}
		File dstFile = new File(dstFilePath);

		try {
			// Read file content
			byte[] fileBytes = java.nio.file.Files.readAllBytes(srcFile.toPath());
			final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encrypted = cipher.doFinal(fileBytes);
			// Write encrypted data to dst file (Base64 encoded)
			String encoded = Base64.getEncoder().encodeToString(encrypted);
			java.nio.file.Files.write(dstFile.toPath(), encoded.getBytes("UTF-8"));
			System.out.println("Encryption complete. Encrypted data written to: " + dstFile.getPath());
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
