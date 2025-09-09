package nycu.main;

import java.util.Scanner;

public class macer {
	public static final Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to use NYCU Macer.");
		System.out.println("1. Run Mac encoder");
		System.out.println("2. Run Mac validator");
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
		
	}
	
	public static void validator() {
		
	}

}
