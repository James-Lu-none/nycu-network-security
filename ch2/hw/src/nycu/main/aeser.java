package nycu.main;

import java.util.Scanner;
import javax.swing.SwingUtilities;

import nycu.ui.AesGui;
import nycu.ui.AesTui;

public class aeser {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("console")) {
            runConsoleMode();
        } else {
            runGUIMode();
        }
    }

    // Console mode
    private static void runConsoleMode() {
        System.out.println("Welcome to use NYCU AESer.");
        System.out.println("1. Run AES encryptor");
        System.out.println("2. Run AES decryptor");
        System.out.print("Enter your choice: ");

        final String choice = input.nextLine();
        switch (choice.charAt(0)) {
            case '1':
                AesTui.encryptor();
                break;
            case '2':
                AesTui.decryptor();
                break;
            default:
                System.out.println("Unknown choice.");
        }
        input.close();
    }

    // GUI mode
    private static void runGUIMode() {
        SwingUtilities.invokeLater(() -> {
            new AesGui().setVisible(true);
        });
    }
}
