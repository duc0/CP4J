package com.vb.graph;

public interface Graph {
    boolean isBidirected();

    int numVertices();

    int numEdges();

    int edgesCapacity();

    int[] next(int vertex);

    int addEdge(int u, int v);

    int getEdgeIndex(int u, int v);
}
