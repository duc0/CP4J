package com.vb.main;

import com.vb.algorithm.AlgorithmRunner;
import com.vb.graph.Graph;
import com.vb.graph.GraphAdjList;
import com.vb.graph.IntGraphWeight;
import com.vb.graph.KruskalAlgorithm;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;

import java.io.IOException;

public class VNOI_CF_QBMST {
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        int[] firstLine = in.readTokensAsIntArray(2);
        int nNodes = firstLine[0];
        int nEdges = firstLine[1];
        Graph g = new GraphAdjList(nNodes, nEdges, true);
        IntGraphWeight weight = new IntGraphWeight(g);
        for (int i = 0; i < nEdges; i++) {
            int[] line = in.readTokensAsIntArray(3);
            int u = line[0] - 1;
            int v = line[1] - 1;
            g.addEdge(u, v);
            weight.setWeight(u, v, line[2]);
        }
        KruskalAlgorithm<Integer> kruskal = new KruskalAlgorithm<>();
        KruskalAlgorithm.Input<Integer> input = new KruskalAlgorithm.Input(g, weight);
        out.write((int) AlgorithmRunner.runAlgorithm(kruskal, input).getResult().getMinimumWeight());
        out.flush();
    }
}
