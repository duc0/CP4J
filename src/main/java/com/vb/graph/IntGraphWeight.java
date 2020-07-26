package com.vb.graph;

import com.vb.nd.IntNDArray;

import java.util.Comparator;

public final class IntGraphWeight implements GraphWeight<Integer> {
    private final Graph graph;
    private final IntNDArray weight;

    public IntGraphWeight(Graph graph) {
        this.graph = graph;
        this.weight = new IntNDArray(graph.edgesCapacity());
    }

    public void setWeight(int u, int v, int w) {
        weight.set(graph.getEdgeIndex(u, v), w);
    }

    public int getWeight(int u, int v) {
        return weight.get(graph.getEdgeIndex(u, v));
    }

    @Override
    public Integer getWeight(int edgeId) {
        return weight.get(edgeId);
    }

    @Override
    public int[] getEdgesSortedByWeight() {
        return GraphWeightUtils.getEdgesSorted(graph, Comparator.comparingInt(this::getWeight));
    }
}
