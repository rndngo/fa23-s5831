import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int dimension;
    private boolean[][] grid;
    private int opened;
    private WeightedQuickUnionUF GToU;
    private WeightedQuickUnionUF isFullUnion;
    private final int water;
    private final int bottom;

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

        int center = convertGTU(row, col);

        int[][] thingy = {{0,1}, {0,-1}, {-1,0}, {1,0}};

        for (int[] thingy2 : thingy) {
            int otherRow = row + thingy2[0];
            int otherCol = col + thingy2[1];

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
