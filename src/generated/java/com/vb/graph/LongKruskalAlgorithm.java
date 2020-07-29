package com.vb.graph;

import com.vb.algorithm.Algorithm;
import com.vb.datastructure.DisjointSet;

import com.vb.number.LongArithmetic;

public class LongKruskalAlgorithm
        implements Algorithm<LongKruskalAlgorithm.Input, LongKruskalAlgorithm.Output> {
    private final LongArithmetic arithmetic;

    public LongKruskalAlgorithm(LongArithmetic arithmetic) {
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
        final LongGraphWeight weight;

        public Input(Graph graph, LongGraphWeight weight) {
            this.graph = graph;
            this.weight = weight;
        }
    }

    public static final class Output {
        private long minimumWeight;
        public long getMinimumWeight() {
            return minimumWeight;
        }
    }
}
