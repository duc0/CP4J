/*
 * Copyright (c) CP4J Project
 */

package com.vb.graph;

import com.vb.algorithm.Algorithm;
import com.vb.datastructure.DisjointSet;

import com.vb.number.DoubleArithmetic;

public class DoubleKruskalAlgorithm
        implements Algorithm<DoubleKruskalAlgorithm.Input, DoubleKruskalAlgorithm.Output> {
    private final DoubleArithmetic arithmetic;

    public DoubleKruskalAlgorithm(DoubleArithmetic arithmetic) {
        this.arithmetic = arithmetic;
    }

    @Override
    public long getComplexity(Input input) {
        return (long) (input.graph.numEdges() * Math.log(input.graph.numVertices()));
    }

    @Override
    public Output run(Input input) {
        assert(input.graph.isUndirected());
        DisjointSet dj = new DisjointSet(input.graph.numVertices());
        int[] sortedEdges = input.weight.getEdgesSortedByWeight();
        Output output = new Output();
        for (int edgeId : sortedEdges) {
            int u = input.graph.getEdgeStart(edgeId);
            int v = input.graph.getEdgeEnd(edgeId);
            if (!dj.inSameSet(u, v)) {
                dj.merge(u, v);
                output.minimumWeight = arithmetic.add(output.minimumWeight, input.weight.getWeight(edgeId));
            }
        }
        return output;
    }

    public static final class Input {
        final Graph graph;
        final DoubleGraphWeight weight;

        public Input(Graph graph, DoubleGraphWeight weight) {
            this.graph = graph;
            this.weight = weight;
        }
    }

    public static final class Output {
        private double minimumWeight;
        public double getMinimumWeight() {
            return minimumWeight;
        }
    }
}
