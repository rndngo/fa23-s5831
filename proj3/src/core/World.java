package core;

import tileengine.TETile;

public class World {

    // build your own world!
    int rooms;
    int hallways;
    int width;
    int height;
    TETile[][] world;


    public World(int width,int height) {
        this.height = height;
        this.width = width;
        world = new TETile[width][height];

    }

    private class Rooms {
        public Rooms() {

        }
    }

    private class Hallways {

    }



}
