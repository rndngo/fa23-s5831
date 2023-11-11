package core;

import tileengine.TERenderer;

import java.util.Scanner;
import javax.swing.JOptionPane;
//princeton STDDraw ??
//gui issues, only printing, no pop

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize the TERenderer, but do not initialize it yet.
        TERenderer ter = new TERenderer();
        boolean running = true;

        while (running) {
            displayMainMenu();
            char choice = scanner.nextLine().toUpperCase().charAt(0); // Take the first character of the input and convert it to upper case

            switch (choice) {
                case 'N':
                    System.out.println("Enter seed for your world:");
                    long seed = Long.parseLong(scanner.nextLine());
                    int width = 80;
                    int height = 40;
                    ter.initialize(width, height);
                    World world = new World("12345", width, height, seed);
                    ter.renderFrame(world.world);
                    break;
            }
        }

        //ter.close(); // This would close the window if TERenderer supports it
        //System.out.println("Thank you for playing!");
    }

    private static void displayMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("N - Start a new world");
    }
}
