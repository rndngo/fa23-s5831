package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.List;
import java.util.Random;

public class Avatar {
    private final int width;
    private final int height;
    private final TETile[][] world;

    public TETile[][] getWorld() {
        return world;
    }
    private final List<Rooms> roomsList;
    private final Random random;
    private TETile avatar;
    public TETile getAvatar() {
        return avatar;
    }
    private boolean doorUnlocked;
    public boolean isDoorUnlocked() {
        return doorUnlocked;
    }
    public void openDoor() {
        doorUnlocked = true;
    }
    private int avatarX, avatarY;  // Coordinates of the avatar
    public int getAvatarX() {
        return avatarX;
    }
    public int getAvatarY() {
        return avatarY;
    }
    private String name;

    public Avatar(int width, int height, TETile[][] world, List<Rooms> roomsList, Random random) {
        this.width = width;
        this.height = height;
        this.world = world;
        this.roomsList = roomsList;
        this.random = random;
        doorUnlocked = false;
        avatar = Tileset.AVATAR;
        hasKey = false;
        this.world[avatarX][avatarY] = avatar;
        this.name = "Player 1";
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public String getName() {
        return this.name;
    }

    public void loadAvatar(int X, int Y) {
        world[avatarX][avatarY] = Tileset.FLOOR;
        avatarX = X;
        avatarY = Y;
        world[avatarX][avatarY] = avatar;
    }

    private boolean hasKey;
    public boolean isHasKey() {
        return hasKey;
    }
    public void obtainedKey() {
        hasKey = true;
    }

    public void moveAvatar(char direction) {

        int newX = avatarX, newY = avatarY;

        switch (direction) {
            case 'w' -> newY++;
            // Move up
            case 'a' -> newX--;
            // Move left
            case 's' -> newY--;
            // Move down
            case 'd' -> newX++;
            // Move right
            default -> {
            }
        }


        // Check for walls or out of bounds
        if (newX < 0 || newX >= width || newY < 0 || newY >= height
                || world[newX][newY].equals(Tileset.WALL) || world[newX][newY].equals(Tileset.NOTHING)
                || (!hasKey && world[newX][newY].equals(Tileset.LOCKED_DOOR))) {
            return;  // Don't move if it's not a valid move

        } else if (world[newX][newY].equals(Tileset.KEY) && !hasKey) {
            hasKey = true;
        } else if (hasKey && world[newX][newY].equals(Tileset.LOCKED_DOOR)) {
            doorUnlocked = true;
            world[newX][newY] = Tileset.UNLOCKED_DOOR;
        }

        // Move the avatar
        world[avatarX][avatarY] = Tileset.FLOOR;  // Or previous tile
        avatarX = newX;
        avatarY = newY;
        world[avatarX][avatarY] = avatar;

    }

    public void placeAvatarInRandomRoom() {
        if (roomsList.isEmpty()) {
            return;
        }

        Rooms randomRoom = roomsList.get(random.nextInt(roomsList.size()));

        for (int x = randomRoom.startX(); x < randomRoom.startX() + randomRoom.width(); x++) {
            for (int y = randomRoom.startY(); y < randomRoom.startY() + randomRoom.height(); y++) {
                if (world[x][y].equals(Tileset.FLOOR) && !world[x][y].equals(Tileset.KEY)) {
                    avatarX = x;
                    avatarY = y;
                    world[x][y] = Tileset.AVATAR;
                    return;
                }
            }
        }
    }
    public boolean[][] lOS(int radius) {
        boolean[][] inSight = new boolean[width][height];
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x * x + y * y <= radius * radius) {
                    int sightX = avatarX + x;
                    int sightY = avatarY + y;
                    if (sightX >= 0 && sightX < width && sightY >= 0 && sightY < height) {
                        inSight[sightX][sightY] = true;
                    }
                }
            }
        }
        return inSight;
    }


}
