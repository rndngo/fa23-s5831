import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayDeque;


public class Percolation {
    // TODO: Add any necessary instance variables.
    public int dimension;
    public boolean [] [] grid;
    public WeightedQuickUnionUF GToU;
    public int water;
    public boolean percolating;

    // use % to get a row and column.

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        dimension = N;
        percolating = false;
        grid = new boolean[N][N];
        GToU = new WeightedQuickUnionUF(N * N * 2);
        for (int x = 0; x < N; x ++) {
            for (int y = 0; y < N; y ++) {
                grid[x][y] = false;
            }
        }
        water = 25;
        GToU.union(water,water+1);
        for (int i = 1;i < 24; i ++) {
            GToU.union(water+i,water);
        }

    }
    /*
    if row = 1 and col = 3 thus saying (1,3),
    we can do something along the line of (row * 5) + col = index for GToU.
    This will counter the 0 meaning even if row is 0.
    But what if row is the last row aka 4 if it's a dimension 5.
    4 * 5 + col = 20 + col. Thus if col = 0 we get item at index 20 as it goes from 0 - dimension squared.
     */
    private void checkadjacentitems (int row, int col) {
        for (int i = -1; i < 2; i += 2) {
            checkforrows(row+i,col, row);
            checkforcols(row,col+i, col);
        }

    }
    private void checkforcols(int row, int col, int ocol) {
        int center = convertGTU(row,ocol);
        if (checkbounds(row,col)) {
            int x = convertGTU(row,col);
            if (isFull(row,col) && !isFull(row,ocol)) {
                GToU.union(water,center);
            } else if (isOpen(row, col) && isFull(row,ocol)) {
                GToU.union(water,x);
            } else if (isOpen(row, col)){
                GToU.union(x,center);
            }
        }
    }
    private void checkforrows(int row, int col,  int orow) {
        int center = convertGTU(orow,col);
        if (checkbounds(row,col)) {
            int x = convertGTU(row,col);
            if (isFull(row,col) && !isFull(orow,col)) {
                GToU.union(water,center);
            } else if (isOpen(row, col) && isFull(orow,col)) {
                GToU.union(water,x);
            } else if (isOpen(row,col)) {
                GToU.union(x,center);
            }
        } else if (row < 0) {
            if (!isFull(orow,col)) {
                GToU.union(water,center);
            }
        } else if (row > dimension) {
            if (isFull(orow,col)) {
                percolating = true;
            }
        }
    }
    private int convertGTU(int row, int col) {
        return (row*5)+col;
    }
    private boolean checkbounds(int row, int col) {
        return (0 <= row && row < dimension) && (0 <= col && col < dimension);
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (checkbounds(row,col)) {
            grid[row][col] = true;
            checkadjacentitems(row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (checkbounds(row,col)) {
            return grid[row][col]; //returns the boolean value.
        }
        else {
            return false;
        }
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if (checkbounds(row,col)) {
            int x = convertGTU(row, col);
            return GToU.connected(x, water); // checks if the coordinate is connected to water.
        }
        else {
            return false;
        }
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        int c = 0;
        for (int x = 0; x < dimension; x ++) {
            for (int y = 0; y < dimension; y ++) {
                if (grid[x][y]){
                    c ++;
                }
            }
        }
        return c;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return percolating;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
