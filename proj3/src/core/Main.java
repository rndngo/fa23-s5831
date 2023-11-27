package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final TERenderer TER = new TERenderer();
    public static World world;
    private static boolean LOS = true; // Default is off

    public static void main(String[] args) {
        TER.initialize(WIDTH, HEIGHT);
        displayMainMenu();
        interactWithInputString();
    }

    private static void displayMainMenu() {
        // Clear the screen and set up the drawing canvas
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        // Set larger font for the title
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 10, "CS61B Menu");

        // Set smaller font for the menu options
        font = new Font("Arial", Font.PLAIN, 16);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 6, "N - New World");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 4, "L - Load World");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "Q - Quit");

        // Show the canvas
        StdDraw.show();
    }

    //double buffer method, draw to off-site buffer then copy in one
    // to reduce flickering!
    private static long seed;
    private static void interactWithInputString() {
        while (true) {
            boolean needsRedraw = false;

            if (world != null) {
                world.updateElapsedTime();
                drawFrame();
            }

            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                switch (key) {
                    case 'n':
                        if (world == null) {
                            seed = promptForSeed();
                            if (seed < 0) {
                                displayMainMenu();
                                break;
                            }
                            world = new World(WIDTH, HEIGHT, seed);
                            needsRedraw = true;
                        }
                        break;
                    case 'l':
                        long loadSeed = promptForSeed();
                        if (loadSeed < 0) {
                            displayMainMenu();
                            break;
                        }
                        load(loadSeed);

                        if (world != null) {
                            needsRedraw = true;
                        }
                        break;
                    case 'q':
                        if (world != null) {
                            LoadSave.save(seed + " " + world.avatar.avatarX + " " + world.avatar.avatarY
                                    + " " + world.avatar.hasKey + " " + world.avatar.doorUnlocked);
                        }
                        System.exit(0);
                        break;
                    case 'w':
                    case 'a':
                    case 's':
                    case 'd':
                        if (world != null) {
                            world.avatar.moveAvatar(key);
                            world.reRenderDoor();
                            needsRedraw = true;
                        }
                        break;
                    case 'g':
                        LOS = !LOS;
                        needsRedraw = true;
                        break;
                    case 'o':
                        promptForAvatarName();
                        break;
                }
            }

            if (needsRedraw && world != null) {
                StdDraw.pause(100);
                drawFrame();
            }
        }
    }
    public static void load(long loadSeed) {
        File file = new File("./src/core/SaveSlots.txt");
        if (!file.exists()) {
            displayMainMenu();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] loading = line.split(" ");
                long savedSeed = Long.parseLong(loading[0]);
                if (savedSeed == loadSeed) {
                    world = new World(WIDTH, HEIGHT, loadSeed);
                    seed = loadSeed;
                    int Y = Integer.parseInt(loading[2]);
                    int X = Integer.parseInt(loading[1]);
                    boolean keyD = Boolean.parseBoolean(loading[3]);
                    boolean door = Boolean.parseBoolean(loading[4]);
                    world.avatar.loadAvatar(X, Y);
                    world.loadWorld(keyD, door);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading save file: " + e.getMessage());
            displayMainMenu();
        }
    }

    private static void promptForAvatarName() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "Enter Avatar Name:");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 2, "Press S to Continue");
        StdDraw.show();

        StringBuilder name = new StringBuilder();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'S' || key == 's') {
                    break;
                } else {
                    name.append(key);
                }
            }
            // Update the displayed name
            StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, name.toString());
            StdDraw.show();
        }
        if (world != null) {
            world.avatar.setName(name.toString());
        }
    }

    private static long promptForSeed() {
        StringBuilder seed = new StringBuilder();

        while (true) {
            clearAndDisplaySeedPrompt(seed);

            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'S' || key == 's') {
                    try {
                        return Long.parseLong(seed.toString());
                    } catch (NumberFormatException e) {
                        // Handle invalid seed input (like an empty string)
                        seed = new StringBuilder(); // Reset the seed input
                        continue;
                    }
                } else if (key == 'b' || key == 'B') {
                    return -1;
                } else if (Character.isDigit(key)) {
                    seed.append(key);
                }
            }
        }
    }

    private static void clearAndDisplaySeedPrompt(StringBuilder seed) {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        // Redraw the seed input prompt
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "Enter World Seed:");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 2, "Press S to Continue");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 4, "Press B to Go Back");

        // Display the current seed
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, seed.toString());

        StdDraw.show();
    }
    private static void drawFrame() {
        StdDraw.clear();
        TER.renderFrame(world.world);
        drawHUD();
        StdDraw.show();
    }
    private static void drawHUD() {
        if (world != null) {
            int mouseX = (int) StdDraw.mouseX();
            int mouseY = (int) StdDraw.mouseY();

            if (mouseX >= 0 && mouseX < WIDTH && mouseY >= 0 && mouseY < HEIGHT) {
                TETile tile = world.world[mouseX][mouseY];
                String description = (tile == null) ? "None" : tile.description();
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.textLeft(1, HEIGHT - 1, "Current Tile: " + description);
            }
            if (world.avatar.hasKey) {
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.textLeft(  1, HEIGHT - 3, "Key Obtained");
            }
            long elapsedTime = world.getElapsedTime();
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textRight(WIDTH - 1, HEIGHT - 1, "Time: " + elapsedTime + "s");
            StdDraw.show();
        }
    }
}
//PREVIOUS IMPLEMENTATION THAT UPDATED AS GAME RAN. FLICKERS ISSUE
//IMPLEMENTED DOUBLE BUFFER BUT NOW TIME ISN'T UPDATING AS IT GOES
//ONLY UPDATES WHEN AVATAR IS PROMPTED


//private static void interactWithInputString() {
//    while (true) {
//        if (world != null) {
//            world.updateElapsedTime();
//        }
//
//        if (StdDraw.hasNextKeyTyped()) {
//            char key = Character.toLowerCase(StdDraw.nextKeyTyped());
//            switch (key) {
//                case 'n':
//                    long seed = promptForSeed();
//                    world = new World(WIDTH, HEIGHT, seed);
//                    break;
//                case 'l':
//                    // Implement load logic
//                    break;
//                case 'q':
//                    System.exit(0);
//                    break;
//                case 'w':
//                case 'a':
//                case 's':
//                case 'd':
//                    if (world != null) {
//                        world.moveAvatar(key);
//                    }
//                    break;
//            }
//        }
//
//        if (world != null) {
//            ter.renderFrame(world.world);
//            drawHUD();
//        }
//    }
//}
//
//    private static void drawHUD() {
//        if (world != null) {
//            int mouseX = (int) StdDraw.mouseX();
//            int mouseY = (int) StdDraw.mouseY();
//
//            if (mouseX >= 0 && mouseX < WIDTH && mouseY >= 0 && mouseY < HEIGHT) {
//                TETile tile = world.world[mouseX][mouseY];
//                String description = (tile == null) ? "None" : tile.description();
//                StdDraw.setPenColor(Color.WHITE);
//                StdDraw.textLeft(1, HEIGHT - 1, "Current Tile: " + description);
//            }
//
//            long elapsedTime = world.getElapsedTime();
//            StdDraw.setPenColor(Color.WHITE);
//            StdDraw.textRight(WIDTH - 1, HEIGHT - 1, "Time: " + elapsedTime + "s");
//            StdDraw.show();
//        }
//    }