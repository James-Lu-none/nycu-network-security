package nycu.main;

import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


public class aeser {

public static final Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to use NYCU AESer.");
		System.out.println("1. Run AES encryptor");
		System.out.println("2. Run AES decryptor");
		System.out.println("Enter your choice: ");
		String choice = "";
		choice = input.nextLine();
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
	
	public static void encryptor() {
		System.out.println("please input your key:");
		System.out.println("Key should be 16, 24 or 32 characters long.");
		String key = input.nextLine();
		System.out.println("please input your data:");
		String data = input.nextLine();

		try {
			final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
			System.out.println("Encrypted data:");
			System.out.println(Base64.getEncoder().encodeToString(encrypted));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
	
	public static void decryptor() {
		System.out.println("please input your key:");
		System.out.println("Key should be 16, 24 or 32 characters long.");
		String key = input.nextLine();
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
