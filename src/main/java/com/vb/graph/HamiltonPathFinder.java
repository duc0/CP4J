package com.vb.graph;

import com.vb.algorithm.Algorithm;
import com.vb.bit.SmallBitSet;
import com.vb.nd.*;

public class HamiltonPathFinder<T extends Number>
        implements Algorithm<HamiltonPathFinder.Input<T>, HamiltonPathFinder.Output> {
    private final Class<T> clz;

    public HamiltonPathFinder(Class<T> clz) {
        this.clz = clz;
    }

    @Override
    public long getComplexity(Input<T> input) {
        return (long) Math.pow(2, input.graph.numVertices()) * input.graph.numVertices() * input.graph.numVertices();
    }

    @Override
    public Output run(Input<T> input) {
        Output output = new Output();

        int n = input.graph.numVertices();

        IntNDArray bestI = null;
        NDShape shape = new NDShape(n, SmallBitSet.fullSet(n) + 1);
        IntGraphWeight wI = null;
        if (clz == Integer.class) {
            bestI = new IntNDArray((int) shape.size());
            bestI.reshape(shape);
            wI = (IntGraphWeight) input.weight;
        }

        for (int set = 0; set <= SmallBitSet.fullSet(n); set++) {
            for (int end = 0; end < n; end++) {
                if (clz == Integer.class) {
                    bestI.set(end, set, set == SmallBitSet.singleElement(end) ? 0 : Integer.MAX_VALUE);
                }
            }
        }
        for (int set = 0; set <= SmallBitSet.fullSet(n); set++) {
            for (int end = 0; end < n; end++) {
                if (SmallBitSet.contains(set, end)) {
                    for (int next = 0; next < n; next++) {
                        if (!SmallBitSet.contains(set, next) && input.graph.hasEdge(end, next)) {
                            int nextSet = SmallBitSet.union(set, next);
                            if (clz == Integer.class) {
                                int current = bestI.get(end, set);
                                if (current != Integer.MAX_VALUE) {
                                    int weight = wI.getWeight(end, next);
                                    int nextBest = bestI.get(next, nextSet);
                                    bestI.set(next, nextSet,
                                            Math.min(nextBest, current + weight));
                                }
                            }
                        }
                    }
                }
            }
        }
        if (clz == Integer.class) {
            output.minimumWeight = bestI.min(NDSliceRanges.col2D(SmallBitSet.fullSet(n)));
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
