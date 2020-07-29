package com.vb.graph;

import com.vb.nd.DoubleNDArray;

import com.vb.number.DoubleArithmetic;

import java.util.Arrays;

public final class DoubleGraphWeight {
    private final DoubleArithmetic arithmetic;
    private final Graph graph;
    private final DoubleNDArray weight;

    public DoubleGraphWeight(DoubleArithmetic arithmetic, Graph graph) {
        this.arithmetic = arithmetic;
        this.graph = graph;
        this.weight = new DoubleNDArray(arithmetic, graph.edgesCapacity());
    }

    public void setWeight(int u, int v, double w) {
        weight.set(graph.getEdgeIndex(u, v), w);
    }

    public double getWeight(int u, int v) {
        return weight.get(graph.getEdgeIndex(u, v));
    }

    public int[] getEdgesSortedByWeight() {
        Integer[] edge = new Integer[graph.numEdges()];
        for (int i = 0; i < graph.numEdges(); i++) {
            edge[i] = i;
        }
        Arrays.sort(edge, (o1, o2) -> arithmetic.compare(weight.get(o1), weight.get(o2)));
        int[] result = new int[graph.numEdges()];
        for (int i = 0; i < graph.numEdges(); i++) {
            result[i] = edge[i];
        }
        return result;
    }

    public double getWeight(int edgeId) {
        return weight.get(edgeId);
    }
}
