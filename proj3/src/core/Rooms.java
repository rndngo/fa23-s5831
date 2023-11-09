package core;

public class Rooms {

    public int width;
    public int height;
    public int startX;
    public int startY;

    // Constructor for Rooms class
    public Rooms(int width, int height, int startX, int startY) {
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
    }

    // Method to check if a room overlaps with this room
    public boolean overlaps(Rooms other) {
        if (this.startX + this.width < other.startX || other.startX + other.width < this.startX) {
            return false;
        }
        if (this.startY + this.height < other.startY || other.startY + other.height < this.startY) {
            return false;
        }
        return true;
    }

    // Add more functionality as necessary...
}
