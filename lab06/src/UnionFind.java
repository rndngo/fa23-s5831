public class UnionFind {
    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     * You can assume that we are only working with non-negative integers as the items
     * in our disjoint sets.
     */
    private int[] data;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        data = new int[N];
        for (int i = 0; i < N; i++) {
            data[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        int size = 0;
        int branch = data[v];
        for (int i = 0; i < data.length; i++) {
            if (data[i] == branch) {
                size ++;
            }
        }
        return size;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if (v < 0) {
            return find(v);
        }
        return data[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if (parent(v1) == -1 && parent(v2) == -1) {
            return false;
        }
        if ((parent(v1) < 0 && parent(v2) < 0 && (parent(v1) != parent(v2)))) {
            return false;
        }
        if (parent(v1) == parent(v2)) {
            return true;
        }
        if (parent(v1) < 0) {
            return connected(v1, parent(v2));
        }
        if (parent(v2) < 0) {
            return connected(parent(v1), v2);
        }
        return connected(parent(v1),parent(v2));
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if (v > data.length) {
            throw new IllegalArgumentException();
        }
        if (parent(v) < 0) {
            return v;
        }
        return parent(v);
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if (parent(v1) == parent(v2)) {
            data[v2] = data[v2] + data[v1];
            data[v1] = v2;
        }
        else if (parent(v1) < 0 && parent(v2) < 0) {
            data[v1] = data[v1] - data[v2];
            data[v2] = v1;
        }
        else if (parent(v1) < 0) {
             union(v1, parent(v2));
        }
        else if (parent(v2) < 0) {
             union(parent(v1), v2);
        }
        else {
            union(parent(v1),parent(v2));
        }
    }

    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     */
    public int[] returnData() {
        return data;
    }
}
