package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    int numberrooms;
    int width;
    int height;
    String worldID;
    TETile[][] world;
    private Random random;
    private boolean doorplaced;
    List<Rooms> roomsList;

    public World(String ID, int width, int height, long seed) {
        this.height = height;
        this.width = width;
        worldID = ID;
        doorplaced = false;
        world = new TETile[width][height];
        random = new Random(seed);
        numberrooms = random.nextInt(20) + 8;
        roomsList = new ArrayList<>();
        createWorld();
        createRooms();
        createHallways();
        createDoor();
        addWallsAroundFloors();
    }
    private class Cords {
        int x;
        int y;
        public Cords(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private void createDoor() {
        Rooms room = roomsList.get(random.nextInt(numberrooms));

        int x = room.startX - 1;
        int y = room.startY - 1;
        int w = room.width + 2;
        int h = room.height + 2;

        List<Cords> storage = new ArrayList<>();
//
        for (int i = x + 1; i < w+x - 2; i++) {
            if (!(world[i][y].equals(Tileset.FLOOR))) {
                storage.add(new Cords(i, y));
            }
        }
        for (int i = y + 1; i < h+y - 2; i++) {
            if (!(world[x][i].equals(Tileset.FLOOR))) {
                storage.add(new Cords(x, i));
            }
        }
        for (int i = x + 1; i < w+x - 2; i++) {
            if (!(world[i][y].equals(Tileset.FLOOR))) {
                storage.add(new Cords(i, y + h - 2));
            }
        }
        for (int i = y + 1; i < h+y - 2; i++) {
            if (!(world[x][i].equals(Tileset.FLOOR))) {
                storage.add((new Cords(x + w - 2, i)));
            }
        }
        Cords cord = storage.get(random.nextInt(storage.size()));
        world[cord.x][cord.y] = Tileset.LOCKED_DOOR;
    }

    private void createWorld() {
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    private void addWallsAroundFloors() {
        TETile[][] worldCopy = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            System.arraycopy(world[x], 0, worldCopy[x], 0, world[x].length);
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // current tile is a floor cont to next tile
                if (worldCopy[x][y].equals(Tileset.FLOOR)) {
                    continue;
                }
                if (isNextToFloor(x, y, worldCopy) && !(world[x][y].equals(Tileset.LOCKED_DOOR))) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    private boolean isNextToFloor(int x, int y, TETile[][] worldCopy) {
        int minX = Math.max(x - 1, 0);
        int maxX = Math.min(x + 1, width - 1);
        int minY = Math.max(y - 1, 0);
        int maxY = Math.min(y + 1, height - 1);

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if (i == x && j == y) {
                    continue;
                }
                if (worldCopy[i][j].equals(Tileset.FLOOR)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void createHallways() {
        UnionFind uf = new UnionFind(roomsList.size());
        //  connect rooms until all are in one set
        while (uf.numberOfSets() > 1) {
            for (int i = 0; i < roomsList.size(); i++) {
                if (uf.numberOfSets() == 1) {
                    break; // breaks if all rooms are set
                }

                Rooms roomA = roomsList.get(i);
                Rooms roomB = findClosestRoomNotInSet(roomA, uf);

                if (roomB != null) {
                    // join roomA and roomB with a hallway
                    createHallwayBetweenRooms(roomA, roomB);
                    // Union sets
                    uf.union(i, roomsList.indexOf(roomB));
                }
            }
        }
    }

    private void createHallwayBetweenRooms(Rooms roomA, Rooms roomB) {
        int aCenterX = roomA.startX + roomA.width / 2;
        int aCenterY = roomA.startY + roomA.height / 2;
        int bCenterX = roomB.startX + roomB.width / 2;
        int bCenterY = roomB.startY + roomB.height / 2;
        // Horizontal
        for (int x = Math.min(aCenterX, bCenterX); x <= Math.max(aCenterX, bCenterX); x++) {
            world[x][aCenterY] = Tileset.FLOOR;
        }
        // Vertical
        for (int y = Math.min(aCenterY, bCenterY); y <= Math.max(aCenterY, bCenterY); y++) {
            world[bCenterX][y] = Tileset.FLOOR;
        }
    }

    private int calculateDistance(Rooms room1, Rooms room2) {
        int room1CenterX = room1.startX + room1.width / 2;
        int room1CenterY = room1.startY + room1.height / 2;
        int room2CenterX = room2.startX + room2.width / 2;
        int room2CenterY = room2.startY + room2.height / 2;

        return Math.abs(room1CenterX - room2CenterX) + Math.abs(room1CenterY - room2CenterY);
    }

    private Rooms findClosestRoomNotInSet(Rooms currentRoom, UnionFind uf) {
        Rooms closestRoom = null;
        int closestDistance = Integer.MAX_VALUE;
        int currentRoomIndex = roomsList.indexOf(currentRoom);

        for (int i = 0; i < roomsList.size(); i++) {
            if (uf.find(currentRoomIndex) != uf.find(i)) { // rooms are not already in the same set
                Rooms otherRoom = roomsList.get(i);
                int distance = calculateDistance(currentRoom, otherRoom);

                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestRoom = otherRoom;
                }
            }
        }
        return closestRoom;
    }
    private void createRooms() {
        while (roomsList.size() < numberrooms) {
            int width = random.nextInt(17) + 3; // randomization
            int height = random.nextInt(17) + 3;
            int startX = random.nextInt(this.width - width) + 1;
            int startY = random.nextInt(this.height - height) + 1;

            Rooms room = new Rooms(width, height, startX, startY);
            boolean overlaps = false;
            for (Rooms other : roomsList) {
                if (room.overlaps(other)) {
                    overlaps = true;
                    break;
                }
            }
            if (!overlaps) {
                roomsList.add(room);
                for (int x = 0; x < room.width-1; x++) {
                    for (int y = 0; y < room.height-1; y++) {
                        world[room.startX + x][room.startY + y] = Tileset.FLOOR;
                    }
                }
            }
        }
    }
}
