package com.vb.graph;

import java.util.HashMap;
import java.util.Map;

public class GraphAdjList implements Graph {
    private final int numVertices;
    private final int edgeCapacity;
    private int numEdges;

    private final int[] start;
    private final int[] end;
    private final int[] lastEdgeIndexWithStart;
    private final int[] prevEdgeIndexSameStart;
    private final int[] lastEdgeIndexWithEnd;
    private final int[] prevEdgeIndexSameEnd;
    private final int[] degree;
    private final Map<Integer, Integer> edgeIndexes = new HashMap<>();
    private final boolean bidirected;

    public GraphAdjList(int numVertices, int edgeCapacity, boolean bidirected) {
        this.numVertices = numVertices;
        this.edgeCapacity = edgeCapacity;
        this.start = new int[edgeCapacity];
        this.end = new int[edgeCapacity];

        this.lastEdgeIndexWithStart = new int[numVertices];
        this.lastEdgeIndexWithEnd = new int[numVertices];
        this.prevEdgeIndexSameStart = new int[edgeCapacity];
        this.prevEdgeIndexSameEnd = new int[edgeCapacity];
        this.degree = new int[numVertices];
        this.bidirected = bidirected;
        for (int u = 0; u < numVertices; u++) {
            lastEdgeIndexWithStart[u] = -1;
            lastEdgeIndexWithEnd[u] = -1;
            degree[u] = 0;
            prevEdgeIndexSameStart[u] = -1;
            prevEdgeIndexSameEnd[u] = -1;
        }
    }

    @Override
    public boolean isBidirected() {
        return bidirected;
    }

    @Override
    public int numVertices() {
        return numVertices;
    }

    @Override
    public int numEdges() {
        return numEdges;
    }

    @Override
    public int edgesCapacity() {
        return edgeCapacity;
    }

    @Override
    public int[] next(int vertex) {
        int[] result = new int[degree[vertex]];
        int idx = lastEdgeIndexWithStart[vertex];
        int resultIdx = 0;
        while (idx >= 0) {
            result[resultIdx] = end[idx];
            idx = prevEdgeIndexSameStart[idx];
            resultIdx++;
        }
        if (isBidirected()) {
            idx = lastEdgeIndexWithEnd[vertex];
            while (idx >= 0) {
                result[resultIdx] = start[idx];
                idx = prevEdgeIndexSameEnd[idx];
                resultIdx++;
            }
        }
        return result;
    }

    private int getMatrixIndex(int u, int v) {
        return u * numVertices + v;
    }

    private int addEdgeRaw(int u, int v) {
        start[numEdges] = u;
        end[numEdges] = v;
        prevEdgeIndexSameStart[numEdges] = lastEdgeIndexWithStart[u];
        lastEdgeIndexWithStart[u] = numEdges;
        prevEdgeIndexSameEnd[numEdges] = lastEdgeIndexWithEnd[v];
        lastEdgeIndexWithEnd[v] = numEdges;
        degree[u]++;
        int index = numEdges;
        edgeIndexes.put(getMatrixIndex(u, v), numEdges);
        if (isBidirected()) {
            degree[v]++;
            edgeIndexes.put(getMatrixIndex(v, u), numEdges);
        }
        numEdges++;
        return index;
    }

    @Override
    public int addEdge(int u, int v) {
        if (isBidirected()) {
            if (u < v) {
                return addEdgeRaw(u, v);
            } else {
                return addEdgeRaw(v, u);
            }
        } else {
            return addEdgeRaw(u, v);
        }
    }

    @Override
    public int getEdgeIndex(int u, int v) {
        Integer res = edgeIndexes.get(getMatrixIndex(u, v));
        if (res == null) {
            throw new RuntimeException("Unable to find indexes for edge " + u + ", " + v);
        }
        return res;
    }
}
