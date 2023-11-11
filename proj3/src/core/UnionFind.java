package core;

public class UnionFind {
    private int[] parent;
    private int[] rank;
    private int count;
//iterate through list to find islands and connect them by closest
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        count = size; // Number of sets
        for (int i = 0; i < size; i++) {
            parent[i] = i; // Initially, each item is its own parent
            rank[i] = 0; // Rank is used for keeping the tree flat
        }
    }
    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]]; // Path compression
            p = parent[p];
        }
        return p;
    }
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // Make root of smaller rank point to root of larger rank
        if (rank[rootP] < rank[rootQ]) {
            parent[rootP] = rootQ;
        } else if (rank[rootP] > rank[rootQ]) {
            parent[rootQ] = rootP;
        } else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }

    public int numberOfSets() {
        return count;
    }
}

