package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;


/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private TETile[][] tiles;

    public KnightWorld(int width, int height, int holeSize) {
        tiles = new TETile[width][height];
        fill();
        knightmovements();
        holes(holeSize);
    }
    private void fill() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = Tileset.WALL;
            }
        }
    }
    private int[][] movements;

    private void knightmovements() {
        movements = new int[][] {{1, 0}, {4, 1}, {2, 2}, {0, 3}, {3, 4}};
    }

    private void holes(int holeSize) {

        for (int x = 0; x < tiles.length; x += holeSize * 5) {
            for (int y = 0; y < tiles[0].length; y += holeSize * 5) {
                for (int[] cords : movements) {
                    int xcord = holeSize * cords[0] + x;
                    int ycord = holeSize * cords[1] + y;
                    squares(xcord, ycord, holeSize);
                }
            }
        }
    }



    private void squares(int startX, int startY, int size) {
        for (int x = startX; x < startX + size && x < tiles.length; x++) {
            for (int y = startY; y < startY + size && y < tiles[0].length; y++) {

                tiles[x][y] = Tileset.NOTHING; // representing the hole
            }
        }
    }


    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 60;
        int height = 40;
        int holeSize = 4;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}
