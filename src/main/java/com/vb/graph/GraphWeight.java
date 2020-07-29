package com.vb.graph;

import com.vb.nd.GenericNDArray;
import com.vb.number.NumberGeneric;
import com.vb.number.Arithmetic;

import java.util.Arrays;

public final class GraphWeight {
    private final Arithmetic arithmetic;
    private final Graph graph;
    private final GenericNDArray weight;

    public GraphWeight(Arithmetic arithmetic, Graph graph) {
        this.arithmetic = arithmetic;
        this.graph = graph;
        this.weight = new GenericNDArray(arithmetic, graph.edgesCapacity());
    }

    public void setWeight(int u, int v, NumberGeneric w) {
        weight.set(graph.getEdgeIndex(u, v), w);
    }

    public NumberGeneric getWeight(int u, int v) {
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

    public NumberGeneric getWeight(int edgeId) {
        return weight.get(edgeId);
    }
}
