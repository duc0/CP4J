package com.vb.graph;

import com.vb.algorithm.Algorithm;
import com.vb.datastructure.DisjointSet;

public class KruskalAlgorithm<T extends Number>
        implements Algorithm<KruskalAlgorithm.Input<T>, KruskalAlgorithm.Output> {
    @Override
    public long getComplexity(Input<T> input) {
        return (long) (input.graph.numEdges() * Math.log(input.graph.numVertices()));
    }

    @Override
    public Output run(Input<T> input) {
        assert(input.graph.isUndirected());
        DisjointSet dj = new DisjointSet(input.graph.numVertices());
        int[] sortedEdges = input.weight.getEdgesSortedByWeight();
        Output output = new Output();
        for (int edgeId : sortedEdges) {
            int u = input.graph.getEdgeStart(edgeId);
            int v = input.graph.getEdgeEnd(edgeId);
            if (!dj.inSameSet(u, v)) {
                dj.merge(u, v);
                output.minimumWeight = output.minimumWeight + input.weight.getWeightBoxed(edgeId).doubleValue();
            }
        }
        return output;
    }

    public static final class Input<T extends Number> {
        final Graph graph;
        final GraphWeight<T> weight;

        public Input(Graph graph, GraphWeight<T> weight) {
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
