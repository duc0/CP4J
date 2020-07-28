package com.vb.main;

import com.vb.algorithm.AlgorithmRunner;
import com.vb.graph.*;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.task.CPTaskSolver;

import java.io.IOException;

public class VNOI_CF_LEM3 implements CPTaskSolver {
    @Override
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        int n = in.nextLineAsInt();
        Graph g = new GraphAdjList(n, n * n, false);
        GraphWeight weight = new GraphWeight(g);
        for (int i = 0; i < n; i++) {
            int[] row = in.readTokensAsIntArray(n);
            for (int j = 0; j < n; j++) {
                if (i != j && row[j] > 0) {
                    g.addEdge(i, j);
                    weight.setWeight(i, j, row[j]);
                }
            }
        }
        HamiltonPathFinder<Integer> hpf = new HamiltonPathFinder<>(Integer.class);
        HamiltonPathFinder.Input<Integer> input = new HamiltonPathFinder.Input(g, weight);
        out.write((int) AlgorithmRunner.runAlgorithm(hpf, input).getResult().getMinimumWeight());
        out.flush();
        out.flush();
    }
}
