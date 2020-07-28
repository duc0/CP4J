package com.vb.graph;

import com.vb.nd.NDArray;
import com.vb.number.GenericNumber;
import com.vb.number.Arithmetic;

import java.util.Arrays;

public final class GraphWeight {
    private final Arithmetic arithmetic;
    private final Graph graph;
    private final NDArray weight;

    public GraphWeight(Arithmetic arithmetic, Graph graph) {
        this.arithmetic = arithmetic;
        this.graph = graph;
        this.weight = new NDArray(arithmetic, graph.edgesCapacity());
    }

    public void setWeight(int u, int v, GenericNumber w) {
        weight.set(graph.getEdgeIndex(u, v), w);
    }

    public GenericNumber getWeight(int u, int v) {
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

    public GenericNumber getWeight(int edgeId) {
        return weight.get(edgeId);
    }
}
