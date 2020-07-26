package com.vb.graph;

public interface GraphWeight<T extends Number> {
    int[] getEdgesSortedByWeight();

    T getWeight(int edgeId);
}
