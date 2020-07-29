/*
 * Copyright (c) CP4J Project
 */

package com.vb.graph;

import com.vb.nd.IntNDArray;

import com.vb.number.IntArithmetic;

import java.util.Arrays;

public final class IntGraphWeight {
    private final IntArithmetic arithmetic;
    private final Graph graph;
    private final IntNDArray weight;

    public IntGraphWeight(IntArithmetic arithmetic, Graph graph) {
        this.arithmetic = arithmetic;
        this.graph = graph;
        this.weight = new IntNDArray(arithmetic, graph.edgesCapacity());
    }

    public void setWeight(int u, int v, int w) {
        weight.set(graph.getEdgeIndex(u, v), w);
    }

    public int getWeight(int u, int v) {
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

    public int getWeight(int edgeId) {
        return weight.get(edgeId);
    }
}
