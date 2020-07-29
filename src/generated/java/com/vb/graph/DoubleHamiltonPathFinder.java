package com.vb.graph;

import com.vb.algorithm.Algorithm;
import com.vb.bit.SmallBitSet;
import com.vb.nd.DoubleNDArray;
import com.vb.nd.NDShape;
import com.vb.nd.NDSliceRanges;

import com.vb.number.DoubleArithmetic;

public class DoubleHamiltonPathFinder
        implements Algorithm<DoubleHamiltonPathFinder.Input, DoubleHamiltonPathFinder.Output> {
    private final DoubleArithmetic arithmetic;

    public DoubleHamiltonPathFinder(DoubleArithmetic arithmetic) {
        this.arithmetic = arithmetic;
    }

    @Override
    public long getComplexity(Input input) {
        return (long) Math.pow(2, input.graph.numVertices()) * input.graph.numVertices() * input.graph.numVertices();
    }

    @Override
    public Output run(Input input) {
        Output output = new Output();

        int n = input.graph.numVertices();

        NDShape shape = new NDShape(n, SmallBitSet.fullSet(n) + 1);
        DoubleNDArray best = new DoubleNDArray(arithmetic, (int)shape.size());
        best.reshape(shape);
        for (int set = 0; set <= SmallBitSet.fullSet(n); set++) {
            for (int end = 0; end < n; end++) {
                best.set(end, set, set == SmallBitSet.singleElement(end) ? arithmetic.zero() : arithmetic.maxValue());
            }
        }
        for (int set = 0; set <= SmallBitSet.fullSet(n); set++) {
            for (int end = 0; end < n; end++) {
                if (SmallBitSet.contains(set, end)) {
                    for (int next = 0; next < n; next++) {
                        if (!SmallBitSet.contains(set, next) && input.graph.hasEdge(end, next)) {
                            int nextSet = SmallBitSet.union(set, next);
                            double current = best.get(end, set);
                            if (!arithmetic.isEqual(current, arithmetic.maxValue())) {
                                double weight = input.weight.getWeight(end, next);
                                double nextBest = best.get(next, nextSet);
                                best.set(next, nextSet,
                                        arithmetic.min(nextBest, arithmetic.add(current, weight)));
                            }
                        }
                    }
                }
            }
        }
        output.minimumWeight = best.min(NDSliceRanges.col2D(SmallBitSet.fullSet(n)));
        return output;
    }

    public static final class Input {
        final Graph graph;
        final DoubleGraphWeight weight;

        public Input(Graph graph, DoubleGraphWeight weight) {
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
