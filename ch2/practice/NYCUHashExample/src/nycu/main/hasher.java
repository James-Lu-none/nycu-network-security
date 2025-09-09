package nycu.main;

import java.security.MessageDigest;
import java.util.Scanner;

public class hasher {
	public static final Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to use NYCU Hasher.");
		System.out.println("1. Run hash encoder");
		System.out.println("2. Run hash validator");
		System.out.println("Enter your choice: ");
		String choice = "";
		choice = input.nextLine();
		switch (choice.charAt(0)) {
			case '1':
				encoder();
				break;
			case '2':
				validator();
				break;
			default:
				System.out.println("Unknown choice.");
				System.exit(0);
		}
		input.close();
	}
	
	public static void encoder() {
		System.out.println("Enter the string to be hashed: ");
		String data = input.nextLine();
		try {
			MessageDigest md = MessageDigest.getInstance(nycu.data.config.HASH_ALGORITHM);
			byte[] digest = md.digest(data.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			System.out.println("The SHA-256 hash is: " + sb.toString());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void validator() {
		System.out.println("Enter the string to be validated: ");
		String data = input.nextLine();
		System.out.println("Enter the hash to be validated against: ");
		String hash = input.nextLine();
		try {
			MessageDigest md = MessageDigest.getInstance(nycu.data.config.HASH_ALGORITHM);
			byte[] digest = md.digest(data.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			if (sb.toString().equals(hash)) {
				System.out.println("The hash is valid.");
			} else {
				System.out.println("The hash is invalid.");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
