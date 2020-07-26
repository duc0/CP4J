package com.vb.graph;

import com.vb.nd.NDArray;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public final class GraphWeightUtils {
    static int[] getEdgesSorted(Graph graph, Comparator<Integer> edgeComparison) {
        Integer[] edge = new Integer[graph.numEdges()];
        for (int i = 0; i < graph.numEdges(); i++) {
            edge[i] = i;
        }
        Arrays.sort(edge, edgeComparison);
        int[] result = new int[graph.numEdges()];
        for (int i = 0; i < graph.numEdges(); i++) {
            result[i] = edge[i];
        }
        return result;
    }
}
