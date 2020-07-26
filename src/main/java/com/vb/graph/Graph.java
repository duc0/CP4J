package com.vb.graph;

public interface Graph {
    boolean isUndirected();

    int numVertices();

    int numEdges();

    int edgesCapacity();

    int[] next(int vertex);

    int addEdge(int u, int v);

    int getEdgeIndex(int u, int v);

    boolean hasEdge(int u, int v);

    int getEdgeStart(int edgeId);

    int getEdgeEnd(int edgeId);
}
