package com.vb.graph;

import com.vb.nd.LongNDArray;

import com.vb.number.LongArithmetic;

import java.util.Arrays;

public final class LongGraphWeight {
    private final LongArithmetic arithmetic;
    private final Graph graph;
    private final LongNDArray weight;

    public LongGraphWeight(LongArithmetic arithmetic, Graph graph) {
        this.arithmetic = arithmetic;
        this.graph = graph;
        this.weight = new LongNDArray(arithmetic, graph.edgesCapacity());
    }

    public void setWeight(int u, int v, long w) {
        weight.set(graph.getEdgeIndex(u, v), w);
    }

    public long getWeight(int u, int v) {
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

    public long getWeight(int edgeId) {
        return weight.get(edgeId);
    }
}
