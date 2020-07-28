package com.vb.graph;

import com.vb.algorithm.Algorithm;
import com.vb.bit.SmallBitSet;
import com.vb.nd.NDArray;
import com.vb.nd.NDShape;
import com.vb.nd.NDSliceRanges;
import com.vb.number.GenericNumber;
import com.vb.number.Arithmetic;

public class HamiltonPathFinder
        implements Algorithm<HamiltonPathFinder.Input, HamiltonPathFinder.Output> {
    private final Arithmetic arithmetic;

    public HamiltonPathFinder(Arithmetic arithmetic) {
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
        NDArray best = new NDArray(arithmetic, (int)shape.size());
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
                            GenericNumber current = best.get(end, set);
                            if (arithmetic.isEqual(current, arithmetic.maxValue())) {
                                GenericNumber weight = input.weight.getWeight(end, next);
                                GenericNumber nextBest = best.get(next, nextSet);
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
        final GraphWeight weight;

        public Input(Graph graph, GraphWeight weight) {
            this.graph = graph;
            this.weight = weight;
        }
    }

    public static final class Output {
        private GenericNumber minimumWeight;
        public GenericNumber getMinimumWeight() {
            return minimumWeight;
        }
    }
}
