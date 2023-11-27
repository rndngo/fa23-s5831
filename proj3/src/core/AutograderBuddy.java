package core;

import edu.princeton.cs.algs4.In;
import tileengine.TETile;
import tileengine.Tileset;


public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    private static final int W = 80;
    private static final int H = 40;
    public static TETile[][] getWorldFromInput(String input) {
        String[] letters = input.split("");
        StringBuilder seedBuilder = new StringBuilder();
        World world = null;

        for (int i = 0; i < letters.length; i++) {
            String command = letters[i].toLowerCase();
            switch (command) {
                case "n" -> {
                    i++;
                    while (i < letters.length && Character.isDigit(letters[i].charAt(0))) {
                        seedBuilder.append(letters[i]);
                        i++;
                    }

                    long seed = Long.parseLong(seedBuilder.toString());
                    world = new World(W, H, seed);
                }

                case "w", "a", "s", "d" -> {
                    if (world != null) {
                        world.avatar.moveAvatar(command.charAt(0));
                    }
                }

                case ":" -> {
                    if (i + 1 < letters.length && letters[i + 1].equals("q")) {
                        if (world != null) {
                            LoadSave.save(seedBuilder + " " + world.avatar.avatarX + " " + world.avatar.avatarY
                                    + " " + world.avatar.hasKey + " " + world.avatar.doorUnlocked);
//                            world = null;
                        }
                    }
                }

                case "l" -> {
                    String data = LoadSave.load();
                    if (data == null) {
                        return new World(W, H, 0).world;
                    }

                    String[] splitData = data.split(" ");
                    World loaded = new World(W, H, Long.parseLong(splitData[0]));
                    int Y = Integer.parseInt(splitData[2]);
                    int X = Integer.parseInt(splitData[1]);
                    boolean keyD = Boolean.parseBoolean(splitData[3]);
                    boolean door = Boolean.parseBoolean(splitData[4]);
                    loaded.avatar.loadAvatar(X, Y);
                    loaded.loadWorld(keyD, door);
                    world = loaded;
                }
            }
        }

        return world.world;
    }



    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
