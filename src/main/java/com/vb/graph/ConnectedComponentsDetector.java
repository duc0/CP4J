package com.vb.graph;

import com.vb.algorithm.Algorithm;

public class ConnectedComponentsDetector implements Algorithm<ConnectedComponentsDetector.Input, ConnectedComponentsDetector.Output> {

    public ConnectedComponentsDetector() {
    }

    private void dfs(Input input, Output output, int u) {
        output.component[u] = output.numComponents;
        for (int v : input.graph.next(u)) {
            if (input.edgePredicate.test(u, v) && output.component[v] == 0) {
                dfs(input, output, v);
            }
        }
    }

    @Override
    public long getComplexity(Input input) {
        return input.graph.numVertices() + input.graph.numEdges();
    }

    @Override
    public Output run(Input input) {
        assert(input.graph.isBidirected());
        Output output = new Output();
        output.component = new int[input.graph.numVertices()];
        for (int u = 0; u < input.graph.numVertices(); u++) {
            if (output.component[u] == 0) {
                output.numComponents++;
                dfs(input, output, u);
            }
        }
        return output;
    }

    public static final class Input {
        final Graph graph;
        final EdgePredicate edgePredicate;

        public Input(Graph graph, EdgePredicate edgePredicate) {
            this.graph = graph;
            this.edgePredicate = edgePredicate;
        }
    }

    public static final class Output {
        private int[] component;
        private int numComponents;

        public int getNumComponents() {
            return numComponents;
        }

        public boolean isConnected() {
            return getNumComponents() == 1;
        }
    }
}
