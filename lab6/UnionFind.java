public class UnionFind {

    // TODO - Add instance variables?
    private int[] parent;       // record parent of i
    private int[] size;         // record number of sites in subtree at i
    private int networkCount;   // number of network

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i += 1) {
            parent[i] = i;
            size[i] = 1;
        }
        networkCount = n;
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int v1) {
        if (v1 < 0 || v1 >= parent.length) {
            throw new IllegalArgumentException("index " + v1 + " is not between 0 and " + (parent.length - 1));
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return size[v1];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        if (parent[v1] == v1) {
            return -sizeOf(v1);
        } else {
            return parent[v1];
        }
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        if (rootV1 == rootV2) {
            return;
        }
        if (size[rootV2] > size[rootV1]) {
            parent[rootV1] = rootV2;
            size[rootV2] += sizeOf(rootV1);
        } else {
            parent[rootV2] = rootV1;
            size[rootV1] += sizeOf(rootV2);
        }
        networkCount -= 1;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int v1) {
        validate(v1);
        while (v1 != parent[v1]) {
            v1 = parent[v1];
        }
        return v1;
    }
}
