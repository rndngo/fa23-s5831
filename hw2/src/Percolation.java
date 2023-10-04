import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int dimension;
    private boolean[][] grid;
    private int opened;
    private WeightedQuickUnionUF GToU;
    private WeightedQuickUnionUF isFullUnion;
    private final int water;
    private final int bottom;
    private boolean percolating;

    // use % to get a row and column.

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N is less than 0");
        }
        dimension = N;
        opened = 0;
        grid = new boolean[N][N];
        GToU = new WeightedQuickUnionUF(N * N + 2);
        isFullUnion = new WeightedQuickUnionUF(N * N + 1);
        water = N * N;
        bottom = water + 1;
    }
    /*
    if row = 1 and col = 3 thus saying (1,3),
    we can do something along the line of (row * 5) + col = index for GToU.
    This will counter the 0 meaning even if row is 0.
    But what if row is the last row aka 4 if it's a dimension 5.
    4 * 5 + col = 20 + col. Thus, if col = 0 we get item at index 20 as it goes from 0 - dimension squared.
     */
    private int convertGTU(int row, int col) {
        return (row * dimension) + col;
    }
    private boolean validBounds(int row, int col) {
        return (0 <= row && row < dimension) && (0 <= col && col < dimension);
    }

    public void open(int row, int col) {
        if (!validBounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        if (isOpen(row, col)) {
            return;
        }

        grid[row][col] = true;
        opened++;

        // implementing checkAdjacentItems functionality here
        int center = convertGTU(row, col);

        // Do not mess with this order! last item must be last, we must check bottom last
        Object[][] xyOffsets = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (Object[] xyOffset : xyOffsets) {
            int otherRow = row + (int) xyOffset[0];
            int otherCol = col + (int) xyOffset[1];
            if (otherRow < 0) { // we are touching water
                GToU.union(water, center);
                isFullUnion.union(water, center);
            }
            if (otherRow >= dimension) { // we are touching the bottom
                GToU.union(center, bottom);
            }

            if (validBounds(otherRow, otherCol) && isOpen(otherRow, otherCol)) {
                int x = convertGTU(otherRow, otherCol);
                GToU.union(x, center);
                isFullUnion.union(x, center);
            }
        }

    }

    public boolean isOpen(int row, int col) {
        if (!validBounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        return grid[row][col]; //returns the boolean value.

    }

    public boolean isFull(int row, int col) {
        if (!validBounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        int x = convertGTU(row, col);
        return isFullUnion.connected(x, water); // checks if the coordinate is connected to water.
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return GToU.connected(bottom, water);
    }
}
