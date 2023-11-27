import core.AutograderBuddy;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

public class WorldGenTests {
    @Test
    public void basicTest() {
        // put different seeds here to test different worlds
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1234567890123456789s");

        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(5000); // pause for 5 seconds so you can see the output
    }

    @Test
    public void basicInteractivityTest() {
        // TODO: write a test that uses an input like "n123swasdwasd"
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n123swwwww");

        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(5000);
    }

    @Test
    public void basicSaveTest() {
        // TODO: write a test that calls getWorldFromInput twice, with "n123swasd:q" and with "lwasd"

        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1392967723524655428sddsaawws:q");


        TETile[][] tiles3 = AutograderBuddy.getWorldFromInput("n1392967723524655428sddsaawwsaddw");

        TERenderer ter = new TERenderer();

        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles3);
        StdDraw.pause(5000);

        ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        // ter.renderFrame(tiles);
//        StdDraw.pause(5000);

        TETile[][] tiles2 = AutograderBuddy.getWorldFromInput("laddw");

        ter = new TERenderer();
        ter.initialize(tiles2.length, tiles2[0].length);
        ter.renderFrame(tiles2);
        StdDraw.pause(5000);


    }
}
