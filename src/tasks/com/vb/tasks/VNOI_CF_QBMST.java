/*
 * Copyright (c) CP4J Project
 */

package com.vb.tasks;

import com.vb.algorithm.AlgorithmRunner;
import com.vb.graph.Graph;
import com.vb.graph.GraphAdjList;
import com.vb.graph.IntGraphWeight;
import com.vb.graph.IntKruskalAlgorithm;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.number.DefaultIntArithmetic;
import com.vb.number.IntArithmetic;
import com.vb.task.CPTaskSolver;

import java.io.IOException;

public class VNOI_CF_QBMST implements CPTaskSolver {
    @Override
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        int[] firstLine = in.readTokensAsIntArray();
        int nNodes = firstLine[0];
        int nEdges = firstLine[1];
        Graph g = new GraphAdjList(nNodes, nEdges, true);
        IntArithmetic arithmetic = new DefaultIntArithmetic();
        IntGraphWeight weight = new IntGraphWeight(arithmetic, g);
        for (int i = 0; i < nEdges; i++) {
            int[] line = in.readTokensAsIntArray();
            int u = line[0] - 1;
            int v = line[1] - 1;
            g.addEdge(u, v);
            weight.setWeight(u, v, line[2]);
        }
        IntKruskalAlgorithm kruskal = new IntKruskalAlgorithm(arithmetic);
        IntKruskalAlgorithm.Input input = new IntKruskalAlgorithm.Input(g, weight);
        out.write(AlgorithmRunner.runAlgorithm(kruskal, input).getResult().getMinimumWeight());
        out.flush();
    }
}
