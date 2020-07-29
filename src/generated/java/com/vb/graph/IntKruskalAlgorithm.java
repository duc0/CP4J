package com.vb.graph;

import com.vb.algorithm.Algorithm;
import com.vb.datastructure.DisjointSet;

import com.vb.number.IntArithmetic;

public class IntKruskalAlgorithm
        implements Algorithm<IntKruskalAlgorithm.Input, IntKruskalAlgorithm.Output> {
    private final IntArithmetic arithmetic;

    public IntKruskalAlgorithm(IntArithmetic arithmetic) {
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
        final IntGraphWeight weight;

        public Input(Graph graph, IntGraphWeight weight) {
            this.graph = graph;
            this.weight = weight;
        }
    }

    public static final class Output {
        private int minimumWeight;
        public int getMinimumWeight() {
            return minimumWeight;
        }
    }
}
