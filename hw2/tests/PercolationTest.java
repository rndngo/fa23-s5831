import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class PercolationTest {
    private int[][] getState(int N, Percolation p) {
        int[][] state = new int[5][5];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = open + full;
            }
        }
        return state;
    }
    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(5);
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        int[][] expectedState = {
                {0, 3, 0, 0, 0},
                {3, 3, 0, 0, 0},
                {3, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
        assertThat(p.numberOfOpenSites()).isEqualTo(6);
    }

    @Test
    public void yourTestHere() {
        // TODO: write some more tests
        int N = 5;
        Percolation p = new Percolation(5);
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                p.open(x,y);
            }
        }
        assertThat(p.numberOfOpenSites()).isEqualTo(25);
        assertThat(p.percolates()).isTrue();
    }
    @Test
    public void N12() {
        int N = 1;
        Percolation p = new Percolation(1);
        assertThat(p.percolates()).isFalse();
        p.open(0,0);
        assertThat(p.percolates()).isTrue();
        N = 2;
        p = new Percolation(2);
        assertThat(p.isFull(1,1)).isFalse();
        p.open(0,0);
        p.open(1,1);
        assertThat(p.percolates()).isFalse();
        p.open(1,0);
        assertThat(p.percolates()).isTrue();
        assertThat(p.isFull(0,1)).isFalse();
        assertThat(p.isOpen(0,1)).isFalse();
    }

    @Test
    public void Nisnegative() {
        int N = -100;
        Percolation p = new Percolation(N);
    }

}
