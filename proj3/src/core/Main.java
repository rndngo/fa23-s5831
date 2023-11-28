package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.ArrayList;


public class Main {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final TERenderer TER = new TERenderer();
    private static World world;
    private static boolean LOS = true; // Default is off

    public static void main(String[] args) {
        TER.initialize(WIDTH, HEIGHT);
        displayMainMenu();
        interactWithInput();
    }
    private static final int TEN = 10;

    private static void displayMainMenu() {
        // Clear the screen and set up the drawing canvas
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        // Set larger font for the title
        Font font = new Font("Arial", Font.BOLD, TEN * 3);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + TEN, "CS61B Menu");

        // Set smaller font for the menu options
        font = new Font("Arial", Font.PLAIN, TEN + 6);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 6, "N - New World");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 4, "L - Load World");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "T - Toggle-ables");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "Q - Quit");

        // Show the canvas
        StdDraw.show();
    }

    //double buffer method, draw to off-site buffer then copy in one
    // to reduce flickering!
    private static long seed;
    private static int torches = 5;
    private static boolean wantsToggle = true;
    private static void interactWithInput() {
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
                            promptForAvatarName();
                            needsRedraw = true;
                        }
                        break;
                    case 'l':
                        if (world == null) {
                            String load = LoadSave.load();
                            if (load == null) {
                                break;
                            }
                            renderLoadedWorld(load);
                        }

                        break;
                    case 'q':
                        if (world == null) {
                            System.exit(0);
                        }
                        break;
                    case ':':
                        promptQuit();
                        break;
                    case 'w': case 'a': case 's': case 'd':
                        if (world != null) {
                            world.getAvatar().moveAvatar(key);
                            world.reRenderDoor();
                            needsRedraw = true;
                        }
                        break;
                    case 'g':
                        if (wantsToggle) {
                            LOS = !LOS;
                            needsRedraw = true;
                            break;
                        } else if (torches > 0) {
                            LOS = !LOS;
                            drawFrame();
                            StdDraw.pause(TEN * TEN * 5);
                            LOS = !LOS;
                            drawFrame();
                            torches--;
                        }
                        break;
                    case 't':
                        if (world == null) {
                            setWantsToggle();
                        }
                        break;
                    default:
                }
            }
            if (needsRedraw && world != null) {
                StdDraw.pause(TEN * TEN);
                drawFrame();
            }
            if (world != null && world.getAvatar().isDoorUnlocked()) {
                displayGameOver();
            }
        }
    }

    private static void promptQuit() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        Font font = new Font("Arial", Font.BOLD, TEN * 3);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + TEN, "Q : Return to Main Menu");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 5, "S : View Settings");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "B : Return to Game");
        StdDraw.show();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'q' || key == 'Q') {
                    StringBuilder string = new StringBuilder();
                    string.append(seed + " " + world.getAvatar().getAvatarX()
                            + " " + world.getAvatar().getAvatarY()
                            + " " + world.getAvatar().isHasKey() + " " + world.getAvatar().isDoorUnlocked());
                    LoadSave.save(string + " " + torches + " " + world.getAvatar().getName());
                    resetGame();
                    displayMainMenu();
                    break;
                } else if (key == 'b' || key == 'B') {
                    font = new Font("Arial", Font.PLAIN, TEN + 6);
                    StdDraw.setFont(font);
                    break;
                } else if (key == 's' || key == 'S') {
                    setWantsToggle();
                    break;
                }
            }
        }
    }
    private static void setWantsToggle() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        Font font = new Font("Arial", Font.BOLD, TEN * 3);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "Do you want to turn on 'Toggle Lights'?");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 2, "Y : Yes        N : No");
        StdDraw.show();
        font = new Font("Arial", Font.PLAIN, TEN + TEN - 5);
        StdDraw.setFont(font);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'Y' || key == 'y') {
                    wantsToggle = true;
                    break;
                } else if (key == 'n' || key == 'N') {
                    wantsToggle = false;
                    break;
                }
            }
        }
        if (world == null) {
            displayMainMenu();
        }
    }

    private static void renderLoadedWorld(String loaded) {
        String[] loading = loaded.split(" ");
        long savedSeed = Long.parseLong(loading[0]);
        world = new World(WIDTH, HEIGHT, savedSeed);
        seed = savedSeed;
        int Y = Integer.parseInt(loading[2]);
        int X = Integer.parseInt(loading[1]);
        boolean keyD = Boolean.parseBoolean(loading[3]);
        boolean door = Boolean.parseBoolean(loading[4]);
        world.getAvatar().loadAvatar(X, Y);
        world.loadWorld(keyD, door);
        torches = Integer.parseInt(loading[5]);
        world.getAvatar().setName(loading[6]);
    }

    private static void resetGame() {
        world = null;
        torches = 5;
    }

    private static void displayGameOver() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        Font font = new Font("Arial", Font.BOLD, TEN * 3);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + TEN, "Congrats on Completing the Game");
        StdDraw.show();
        StdDraw.pause(TEN * TEN * TEN * 5);
        font = new Font("Arial", Font.PLAIN, TEN + TEN - 5);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 6, "You will be directed to the Main Menu shortly");
        resetGame();
        LoadSave.save("");
        StdDraw.show();
        StdDraw.pause(TEN * TEN * TEN * 5);

        // Show the canvas
        displayMainMenu();
    }

    private static void promptForAvatarName() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "Enter Avatar Name:");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 2, "Press S to Continue");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 4, "Press ` to Go Back");
        StdDraw.show();

        StringBuilder name = new StringBuilder();
        boolean back = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'S' || key == 's') {
                    break;
                } else if (key == '`' || key == '~') {
                    back = true;
                    break;
                } else {
                    name.append(key);
                }
                StdDraw.clear(StdDraw.BLACK);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "Enter Avatar Name:");
                StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 2, "Press S to Continue");
                StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 4, "Press ` to Go Back");
                StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, name.toString());
                StdDraw.show();
            }
            // Update the displayed name

        }
        if (back) {
            seed = promptForSeed();
            world = new World(WIDTH, HEIGHT, seed);
            promptForAvatarName();
        }
        if (world != null && !name.isEmpty()) {
            world.getAvatar().setName(name.toString());
        }
    }

    private static long promptForSeed() {
        StringBuilder seeder = new StringBuilder();

        while (true) {
            clearAndDisplaySeedPrompt(seeder);

            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'S' || key == 's') {
                    try {
                        return Long.parseLong(seeder.toString());
                    } catch (NumberFormatException e) {
                        // Handle invalid seed input (like an empty string)
                        seeder = new StringBuilder(); // Reset the seed input
                        continue;
                    }
                } else if (key == 'b' || key == 'B') {
                    return -1;
                } else if (Character.isDigit(key)) {
                    seeder.append(key);
                }
            }
        }
    }

    private static void clearAndDisplaySeedPrompt(StringBuilder seeds) {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        // Redraw the seed input prompt
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 2, "Enter World Seed:");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 2, "Press S to Continue");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 4, "Press B to Go Back");

        // Display the current seed
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, seeds.toString());

        StdDraw.show();
    }
    private static class Cords {
        int x;
        int y;
        public Cords(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private static ArrayList<Cords> cordsList;
    private static void drawFrame() {
        StdDraw.clear();
        cordsList = new ArrayList<>();
        if (LOS && world != null) {
            boolean[][] inSight = world.getAvatar().lOS(5); // Radius of 5, for example
            TETile[][] tilesToRender = new TETile[WIDTH][HEIGHT];
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (inSight[x][y]) {
                        tilesToRender[x][y] = world.world[x][y];
                        cordsList.add(new Cords(x, y));
                    } else {
                        tilesToRender[x][y] = Tileset.NOTHING; // Or some other default tile
                    }
                }
            }
            StdDraw.clear(Color.BLACK);
            TER.drawTiles(tilesToRender);
        } else {
            StdDraw.clear(Color.BLACK);
            TER.drawTiles(world.world);
        }
        drawHUD();
        StdDraw.show();
    }
    private static void drawHUD() {
        if (world != null) {
            int mouseX = (int) StdDraw.mouseX();
            int mouseY = (int) StdDraw.mouseY();

            if (mouseX >= 0 && mouseX < WIDTH && mouseY >= 0 && mouseY < HEIGHT) {
                for (Cords cords : cordsList) {
                    if (cords.x == mouseX && cords.y == mouseY) {
                        TETile tile = world.world[mouseX][mouseY];
                        String description = (tile == null) ? "None" : tile.description();
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.textLeft(1, HEIGHT - 1, "Current Tile: " + description);
                    }
                }
            }
            if (world.getAvatar().isHasKey()) {
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.textLeft(1, HEIGHT - 3, "Key Obtained");
            }
            if (world != null) {
                // Display the avatar's name
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.textRight(WIDTH - 1, HEIGHT - 3, "Name: " + world.getAvatar().getName());
            }
            if (torches > 0 && !wantsToggle) {
                StdDraw.textRight(WIDTH - 1, HEIGHT - 5, "Torches Left: " + torches);
                StdDraw.textRight(WIDTH - 1, HEIGHT - 7, "Press G to Use");
            }
            long elapsedTime = world.getElapsedTime();
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textRight(WIDTH - 1, HEIGHT - 1, "Time: " + elapsedTime + "s");
            StdDraw.textLeft(1, 1, "'Shift' + ';' to Open Options");
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
