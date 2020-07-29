package com.vb.main;

import com.vb.algorithm.AlgorithmRunner;
import com.vb.graph.Graph;
import com.vb.graph.GraphAdjList;
import com.vb.graph.IntGraphWeight;
import com.vb.graph.IntHamiltonPathFinder;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.number.DefaultIntArithmetic;
import com.vb.number.IntArithmetic;
import com.vb.task.CPTaskSolver;

import java.io.IOException;

public class VNOI_CF_LEM3 implements CPTaskSolver {
    @Override
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {

        int n = in.nextLineAsInt();
        Graph g = new GraphAdjList(n, n * n, false);
        IntArithmetic arithmetic = new DefaultIntArithmetic();
        IntGraphWeight weight = new IntGraphWeight(arithmetic, g);
        for (int i = 0; i < n; i++) {
            int[] row = in.readTokensAsIntArray(n);
            for (int j = 0; j < n; j++) {
                if (i != j && row[j] > 0) {
                    g.addEdge(i, j);
                    weight.setWeight(i, j, row[j]);
                }
            }
        }
        IntHamiltonPathFinder hpf = new IntHamiltonPathFinder(arithmetic);
        IntHamiltonPathFinder.Input input = new IntHamiltonPathFinder.Input(g, weight);
        out.write(AlgorithmRunner.runAlgorithm(hpf, input).getResult().getMinimumWeight());
        out.flush();
    }
}
