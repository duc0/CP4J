package com.vb.graph;

public interface GraphWeight<T extends Number> {
    int[] getEdgesSortedByWeight();

    T getWeightBoxed(int u, int v);

    T getWeightBoxed(int edgeId);
}
