package com.vb.main;

import com.vb.algorithm.AlgorithmRunner;
import com.vb.graph.*;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.nd.IntNDArray;
import com.vb.search.BinarySearch;

import java.io.IOException;

public class VNOI_CF_NKCITY {
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        IntNDArray size = in.nextLineAsIntArray();
        int n = size.get(0);
        int m = size.get(1);
        Graph graph = new GraphAdjList(n, m * 2, true);
        IntGraphWeight cost = new IntGraphWeight(graph);
        int minWeight = Integer.MAX_VALUE;
        int maxWeight = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            IntNDArray row = in.nextLineAsIntArray();
            int u = row.get(0);
            int v = row.get(1);
            int t = row.get(2);
            graph.addEdge(u - 1, v - 1);
            cost.setWeight(u - 1, v - 1, t);
            minWeight = Math.min(minWeight, t);
            maxWeight = Math.max(maxWeight, t);
        }

        ConnectedComponentsDetector cc = new ConnectedComponentsDetector();
        out.write(BinarySearch.findMinSatisfy(minWeight, maxWeight, w ->
                AlgorithmRunner.runAlgorithm(cc, new ConnectedComponentsDetector.Input(graph, (u, v) -> cost.getWeight(u, v) <= w)).getResult().isConnected()));
    }
}
