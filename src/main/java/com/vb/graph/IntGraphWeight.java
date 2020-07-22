package com.vb.graph;

import com.vb.nd.IntNDArray;

public final class IntGraphWeight {
    private final Graph graph;
    private final IntNDArray weight;

    public IntGraphWeight(Graph graph) {
        this.graph = graph;
        weight = new IntNDArray(graph.edgesCapacity());
    }

    public void setWeight(int u, int v, int w) {
        weight.set(graph.getEdgeIndex(u, v), w);
    }

    public int getWeight(int u, int v) {
        return weight.get(graph.getEdgeIndex(u, v));
    }
}
