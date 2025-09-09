package nycu.main;

import java.util.Scanner;

public class rsaer {

	public static final Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to use NYCU RSAer.");
		System.out.println("0. Run RSA Keypair Generator");
		System.out.println("1. Run RSA encryptor");
		System.out.println("2. Run RSA decryptor");
		System.out.println("Enter your choice: ");
		String choice = "";
		choice = input.nextLine();
		switch (choice.charAt(0)) {
			case '0':
				keypair();
				break;
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
	
	public static void keypair() {

	}
	
	public static void encryptor() {

		
	}
	
	public static void decryptor() {

	}

}
