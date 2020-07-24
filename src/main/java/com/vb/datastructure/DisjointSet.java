package com.vb.datastructure;

public class DisjointSet {
    private final int size;
    private final int[] parent;

    public DisjointSet(int size) {
        this.size = size;
        this.parent = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    private int getRoot(int u) {
        if (parent[u] == u) {
            return u;
        } else {
            return parent[u] = getRoot(parent[u]);
        }
    }

    public void merge(int u, int v) {
        parent[getRoot(u)] = getRoot(v);
    }

    public boolean inSameSet(int u, int v) {
        return getRoot(u) == getRoot(v);
    }
}
