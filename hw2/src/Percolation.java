import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayDeque;


public class Percolation {
    // TODO: Add any necessary instance variables.
    public boolean [] [] grid;
    public int [] GToU;
    public ArrayDeque<String> Index;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        grid = new boolean[N][N];
        GToU = new int[N*N];
        Index = new ArrayDeque<>();
        int c = 0;
        for (int x = 0; x < N; x ++) {
            for (int y = 0; y < N; y ++) {
                grid[x][y] = false;
                GToU[c] = -1;
                Index.addLast();
                c ++;
            }
        }
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        return false;
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        return false;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return 0;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
