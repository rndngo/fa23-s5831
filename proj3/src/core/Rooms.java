package core;

public record Rooms(int width, int height, int startX, int startY) {

    // Constructor for Rooms class

    // Method to check if a room overlaps with this room
    public boolean overlaps(Rooms other) {
        if (this.startX + this.width < other.startX || other.startX + other.width < this.startX) {
            return false;
        }
        return this.startY + this.height >= other.startY && other.startY + other.height >= this.startY;
    }

    // Add more functionality as necessary...
}
