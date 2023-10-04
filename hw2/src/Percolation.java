import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int dimension;
    private boolean[][] grid;
    private int opened;
    private WeightedQuickUnionUF GToU;
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
        this.percolating = false;
        grid = new boolean[N][N];
        GToU = new WeightedQuickUnionUF(N * N + 2);
        water = N * N;
        bottom = water + 1;
    }
    /*
    if row = 1 and col = 3 thus saying (1,3),
    we can do something along the line of (row * 5) + col = index for GToU.
    This will counter the 0 meaning even if row is 0.
    But what if row is the last row aka 4 if it's a dimension 5.
    4 * 5 + col = 20 + col. Thus if col = 0 we get item at index 20 as it goes from 0 - dimension squared.
     */
    private void checkAdjacentItems(int row, int col) {
        for (int i = -1; i < 2; i += 2) {
            checkForRows(row + i, col, row);
            checkForCols(row, col + i, col);
        }

    }
    private void checkForCols(int row, int col, int ocol) {
        int center = convertGTU(row, ocol);
        if (checkBounds(row, col)) {
            int x = convertGTU(row, col);
            if (isFull(row, col) && !isFull(row, ocol)) {
                GToU.union(water, center);
            } else if (isOpen(row, col) && isFull(row, ocol)) {
                GToU.union(water, x);
            } else if (isOpen(row, col)) {
                GToU.union(x, center);
            }
        }
    }
    private void checkForRows(int row, int col, int orow) {
        int center = convertGTU(orow, col);
        if (checkBounds(row, col)) {
            int x = convertGTU(row, col);
            if (isFull(row, col) && !isFull(orow, col)) {
                GToU.union(water, center);
            } else if (isOpen(row, col) && isFull(orow, col)) {
                GToU.union(water, x);
            } else if (isOpen(row, col)) {
                GToU.union(x, center);
            }
        } else if (row < 0) {
            if (!isFull(orow, col)) {
                GToU.union(water, center);
            }
        } else if (row >= dimension) {
            if (isFull(orow, col)) {
                this.percolating = true;
            }
        }
    }
    private int convertGTU(int row, int col) {
        return (row * dimension) + col;
    }
    private boolean checkBounds(int row, int col) {
        return (0 <= row && row < dimension) && (0 <= col && col < dimension);
    }

    public void open(int row, int col) {
        if (checkBounds(row, col)) {
            grid[row][col] = true;
            opened++;
            checkAdjacentItems(row, col);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isOpen(int row, int col) {
        if (checkBounds(row, col)) {
            return grid[row][col]; //returns the boolean value.
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isFull(int row, int col) {
        if (checkBounds(row, col)) {
            int x = convertGTU(row, col);
            return GToU.connected(x, water); // checks if the coordinate is connected to water.
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
//        int x = dimension - 1;
//        if (x < 0) {
//            return this.percolating;
//        }
//        for (int i = 0; i < dimension; i++) {
//            if (isFull(dimension - 1, i)) {
//                return true;
//            }
//        }
//        return this.percolating;
//    }
        return percolating;
    }
}
