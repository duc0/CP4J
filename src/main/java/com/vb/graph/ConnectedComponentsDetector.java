package com.vb.graph;

public class ConnectedComponentsDetector {
    private final Graph graph;
    private final int[] component;
    private final EdgePredicate edgePredicate;
    private int numComponents;

    public ConnectedComponentsDetector(Graph graph, EdgePredicate edgePredicate) {
        this.graph = graph;
        this.edgePredicate = edgePredicate;
        assert(graph.isBidirected());
        component = new int[graph.numVertices()];
        run();
    }

    private void dfs(int u) {
        component[u] = numComponents;
        for (int v : graph.next(u)) {
            if (edgePredicate.test(u, v) && component[v] == 0) {
                dfs(v);
            }
        }
    }

    private void run() {
        for (int u = 0; u < graph.numVertices(); u++) {
            if (component[u] == 0) {
                numComponents++;
                dfs(u);
            }
        }
    }

    public int getNumComponents() {
        return numComponents;
    }

    public boolean isConnected() {
        return getNumComponents() == 1;
    }
}
