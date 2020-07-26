import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.util.Map;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author duc
 */
public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastWriter out = new FastWriter(outputStream);
        VNOI_CF_LEM3 solver = new VNOI_CF_LEM3();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_LEM3 {
        public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
            int n = in.nextLineAsInt();
            Graph g = new GraphAdjList(n, n * n, false);
            IntGraphWeight weight = new IntGraphWeight(g);
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

    static class NDSliceRanges {
        private final int[] sliceStart;
        private final int[] sliceEnd;

        public static NDSliceRanges singleDimension(int rank, int dimension, int start, int end) {
            int[] sliceStart = new int[rank];
            int[] sliceEnd = new int[rank];
            sliceStart[dimension] = start;
            sliceEnd[dimension] = end;
            return new NDSliceRanges(sliceStart, sliceEnd);
        }

        public static NDSliceRanges col2D(int start) {
            return singleDimension(2, 1, start, start + 1);
        }

        private NDSliceRanges(int[] sliceStart, int[] sliceEnd) {
            assert (sliceStart.length == sliceEnd.length);
            this.sliceStart = sliceStart;
            this.sliceEnd = sliceEnd;
        }

        public int rank() {
            return sliceStart.length;
        }

        public int getSliceStart(NDShape shape, int i) {
            int result = sliceStart[i];
            if (result < 0) {
                result = shape.dim(i) + result;
            }
            return result;
        }

        public int getSliceEnd(NDShape shape, int i) {
            int result = sliceEnd[i];
            if (result <= 0) {
                result = shape.dim(i) + result;
            }
            return result;
        }

        public String toString() {
            return "NDSliceRanges{" +
                    "sliceStart=" + Arrays.toString(sliceStart) +
                    ", sliceEnd=" + Arrays.toString(sliceEnd) +
                    '}';
        }

    }

    static interface Algorithm<I, O> {
        long getComplexity(I input);

        O run(I input);

    }

    static class SmallBitSet {
        public static int fullSet(int n) {
            return (1 << n) - 1;
        }

        public static boolean contains(int set, int i) {
            return BitUtils.testBit(set, i);
        }

        public static int union(int set, int i) {
            return BitUtils.setBit(set, i);
        }

        public static int singleElement(int i) {
            return 1 << i;
        }

    }

    static interface GraphWeight<T extends Number> {
    }

    static class NDIndex {
        private final int[] indexes;

        public NDIndex(int... indexes) {
            this.indexes = indexes;
        }

        public int rank() {
            return indexes.length;
        }

        public int index(int index) {
            return indexes[index];
        }

        public static NDIndex startIndex(NDShape ndShape, NDSliceRanges ndSliceRanges) {
            int[] indexes = new int[ndSliceRanges.rank()];
            for (int i = 0; i < ndSliceRanges.rank(); i++) {
                indexes[i] = ndSliceRanges.getSliceStart(ndShape, i);
            }
            return new NDIndex(indexes);
        }

        public boolean next(NDShape ndShape, NDSliceRanges ndSliceRanges) {
            int i = ndSliceRanges.rank() - 1;
            for (; i >= 0; i--) {
                if (indexes[i] < ndSliceRanges.getSliceEnd(ndShape, i) - 1) {
                    indexes[i]++;
                    break;
                }
                indexes[i] = ndSliceRanges.getSliceStart(ndShape, i);
            }
            return i >= 0;
        }

        public String toString() {
            return "NDIndex{" +
                    "indexes=" + Arrays.toString(indexes) +
                    '}';
        }

    }

    static interface Graph {
        int numVertices();

        int edgesCapacity();

        int addEdge(int u, int v);

        int getEdgeIndex(int u, int v);

        boolean hasEdge(int u, int v);

    }

    static class GraphAdjList implements Graph {
        private final int numVertices;
        private final int edgeCapacity;
        private int numEdges;
        private final int[] start;
        private final int[] end;
        private final int[] lastEdgeIndexWithStart;
        private final int[] prevEdgeIndexSameStart;
        private final int[] lastEdgeIndexWithEnd;
        private final int[] prevEdgeIndexSameEnd;
        private final int[] degree;
        private final Map<Integer, Integer> edgeIndexes = new HashMap<>();
        private final boolean undirected;

        public GraphAdjList(int numVertices, int edgeCapacity, boolean undirected) {
            this.numVertices = numVertices;
            this.edgeCapacity = edgeCapacity;
            this.start = new int[edgeCapacity];
            this.end = new int[edgeCapacity];

            this.lastEdgeIndexWithStart = new int[numVertices];
            this.lastEdgeIndexWithEnd = new int[numVertices];
            this.prevEdgeIndexSameStart = new int[edgeCapacity];
            this.prevEdgeIndexSameEnd = new int[edgeCapacity];
            this.degree = new int[numVertices];
            this.undirected = undirected;
            for (int u = 0; u < numVertices; u++) {
                lastEdgeIndexWithStart[u] = -1;
                lastEdgeIndexWithEnd[u] = -1;
                degree[u] = 0;
                prevEdgeIndexSameStart[u] = -1;
                prevEdgeIndexSameEnd[u] = -1;
            }
        }

        public boolean isUndirected() {
            return undirected;
        }

        public int numVertices() {
            return numVertices;
        }

        public int edgesCapacity() {
            return edgeCapacity;
        }

        private int getMatrixIndex(int u, int v) {
            return u * numVertices + v;
        }

        private int addEdgeRaw(int u, int v) {
            start[numEdges] = u;
            end[numEdges] = v;
            prevEdgeIndexSameStart[numEdges] = lastEdgeIndexWithStart[u];
            lastEdgeIndexWithStart[u] = numEdges;
            prevEdgeIndexSameEnd[numEdges] = lastEdgeIndexWithEnd[v];
            lastEdgeIndexWithEnd[v] = numEdges;
            degree[u]++;
            int index = numEdges;
            edgeIndexes.put(getMatrixIndex(u, v), numEdges);
            if (isUndirected()) {
                degree[v]++;
                edgeIndexes.put(getMatrixIndex(v, u), numEdges);
            }
            numEdges++;
            return index;
        }

        public int addEdge(int u, int v) {
            if (isUndirected()) {
                if (u < v) {
                    return addEdgeRaw(u, v);
                } else {
                    return addEdgeRaw(v, u);
                }
            } else {
                return addEdgeRaw(u, v);
            }
        }

        public int getEdgeIndex(int u, int v) {
            Integer res = edgeIndexes.get(getMatrixIndex(u, v));
            if (res == null) {
                throw new RuntimeException("Unable to find indexes for edge " + u + ", " + v);
            }
            return res;
        }

        public boolean hasEdge(int u, int v) {
            return edgeIndexes.containsKey(getMatrixIndex(u, v));
        }

    }

    static class IntNDArray extends NDArray<Integer> {
        public IntNDArray(int capacity) {
            super(capacity, Integer.class);
        }

        public int get(int i0) {
            return getI(i0);
        }

        public int get(int i0, int i1) {
            return getI(i0, i1);
        }

        public void set(int i0, int val) {
            setI(i0, val);
        }

        public void set(int i0, int i1, int val) {
            setI(i0, i1, val);
        }

        public int min(NDSliceRanges ndSliceRanges) {
            return minI(ndSliceRanges);
        }

    }

    static final class AlgorithmRunner {
        private AlgorithmRunner() {
        }

        public static <I, O> AlgorithmRunner.AlgorithmResult<O> runAlgorithm(Algorithm<I, O> algorithm, I input) {
            return runAlgorithm(algorithm, input, 1000000000);
        }

        public static <I, O> AlgorithmRunner.AlgorithmResult<O> runAlgorithm(Algorithm<I, O> algorithm, I input, long complexityLimit) {
            long complexity = algorithm.getComplexity(input);
            if (complexity > complexityLimit) {
                throw new RuntimeException("This algorithm might be too slow!");
            }
            long startTime = System.currentTimeMillis();
            O output = algorithm.run(input);
            long elapsed = System.currentTimeMillis() - startTime;
            return new AlgorithmRunner.AlgorithmResult<>(output, elapsed, complexity);
        }

        public static final class AlgorithmResult<O> {
            final O result;
            final long runTime;
            final long complexity;

            AlgorithmResult(O result, long runTime, long complexity) {
                this.result = result;
                this.runTime = runTime;
                this.complexity = complexity;
            }

            public O getResult() {
                return result;
            }

        }

    }

    static abstract class NDArray<T extends Number> {
        protected final int[] intBuffer;
        private final long[] longBuffer;
        private final double[] doubleBuffer;
        private final int capacity;
        private final Class<T> clz;
        protected NDShape shape;

        NDArray(int capacity, Class<T> clz) {
            this.clz = clz;
            this.capacity = capacity;
            if (clz == Double.class) {
                this.doubleBuffer = new double[capacity];
                this.longBuffer = null;
                this.intBuffer = null;
            } else if (clz == Long.class) {
                this.longBuffer = new long[capacity];
                this.doubleBuffer = null;
                this.intBuffer = null;
            } else if (clz == Integer.class) {
                this.intBuffer = new int[capacity];
                this.doubleBuffer = null;
                this.longBuffer = null;
            } else {
                throw new RuntimeException("Unsupported number types");
            }
            this.shape = new NDShape(capacity);
        }

        public void reshape(NDShape shape) {
            if (shape.size() > capacity) {
                throw new RuntimeException("Capacity is not enough for size " + shape.size());
            }
            this.shape = shape;
        }

        int getI(int i0) {
            assert (shape.rank() == 1);
            return intBuffer[i0];
        }

        int getI(int i0, int i1) {
            assert (shape.rank() == 2);
            assert (0 <= i0 && i0 < shape.dim(0)) : "i0 = " + i0;
            assert (0 <= i1 && i1 < shape.dim(1)) : "i1 = " + i1;
            return intBuffer[shape.d2(i0, i1)];
        }

        int getI(NDIndex index) {
            if (index.rank() == 1) {
                return getI(index.index(0));
            } else if (index.rank() == 2) {
                return getI(index.index(0), index.index(1));
            } else {
                throw new RuntimeException("Unimplemented");
            }
        }

        void setI(int i0, int val) {
            assert (shape.rank() == 1);
            intBuffer[i0] = val;
        }

        void setI(int i0, int i1, int val) {
            assert (shape.rank() == 2);
            intBuffer[shape.d2(i0, i1)] = val;
        }

        int minI(NDSliceRanges ndSliceRanges) {
            NDIndex index = NDIndex.startIndex(shape, ndSliceRanges);
            int result = getI(index);
            do {
                int value = getI(index);
                if (value < result) {
                    result = value;
                }
            } while (index.next(shape, ndSliceRanges));
            return result;
        }

        public String toString() {
            String array = "";
            if (clz == Double.class) {
                array = Arrays.toString(doubleBuffer);
            } else if (clz == Long.class) {
                array = Arrays.toString(longBuffer);
            } else if (clz == Integer.class) {
                array = Arrays.toString(intBuffer);
            }
            return "NDArray{" +
                    "buffer=" + array +
                    ", capacity=" + capacity +
                    ", clz=" + clz +
                    ", shape=" + shape +
                    '}';
        }

    }

    static class NDShape {
        private final int[] dims;
        private final long size;

        public NDShape(int... dims) {
            this.dims = dims;
            long sz = 1;
            for (int x : dims) {
                sz *= x;
            }
            this.size = sz;
        }

        public int rank() {
            return dims.length;
        }

        public int dim(int index) {
            return dims[index];
        }

        public long size() {
            return size;
        }

        public int d2(int i0, int i1) {
            assert (dims.length == 2);
            return i0 * dims[1] + i1;
        }

        public String toString() {
            return "NDShape{" +
                    "dims=" + Arrays.toString(dims) +
                    ", size=" + size +
                    '}';
        }

    }

    static class FastScanner {
        private final BufferedReader bufferedReader;

        public FastScanner(InputStream inputStream) {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public int nextLineAsInt() throws IOException {
            return Integer.parseInt(bufferedReader.readLine().trim());
        }

        public int[] readTokensAsIntArray(int capacity) throws IOException {
            String line = bufferedReader.readLine();
            int[] result = new int[capacity];
            int cur = 0;
            int pos = 0;
            for (int i = 0; i <= line.length(); i++) {
                char c = i == line.length() ? ' ' : line.charAt(i);
                if ('0' <= c && c <= '9') {
                    cur = cur * 10 + (c - '0');
                } else {
                    result[pos] = cur;
                    pos++;
                    cur = 0;
                    if (pos >= capacity) {
                        break;
                    }
                }
            }
            return result;
        }

    }

    static class FastWriter {
        private final BufferedWriter bufferedWriter;

        public FastWriter(OutputStream outputStream) {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        }

        public FastWriter(Writer writer) {
            bufferedWriter = new BufferedWriter(writer);
        }

        public void close() throws IOException {
            bufferedWriter.flush();
            bufferedWriter.close();
        }

        public void write(int token) throws IOException {
            bufferedWriter.write(String.valueOf(token));
            //bufferedWriter.flush();
        }

        public void flush() throws IOException {
            bufferedWriter.flush();
        }

    }

    static class HamiltonPathFinder<T extends Number> implements Algorithm<HamiltonPathFinder.Input<T>, HamiltonPathFinder.Output> {
        private final Class<T> clz;

        public HamiltonPathFinder(Class<T> clz) {
            this.clz = clz;
        }

        public long getComplexity(HamiltonPathFinder.Input<T> input) {
            return (long) Math.pow(2, input.graph.numVertices()) * input.graph.numVertices() * input.graph.numVertices();
        }

        public HamiltonPathFinder.Output run(HamiltonPathFinder.Input<T> input) {
            HamiltonPathFinder.Output output = new HamiltonPathFinder.Output();

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

    static final class IntGraphWeight implements GraphWeight<Integer> {
        private final Graph graph;
        private final IntNDArray weight;

        public IntGraphWeight(Graph graph) {
            this.graph = graph;
            this.weight = new IntNDArray(graph.edgesCapacity());
        }

        public void setWeight(int u, int v, int w) {
            weight.set(graph.getEdgeIndex(u, v), w);
        }

        public int getWeight(int u, int v) {
            return weight.get(graph.getEdgeIndex(u, v));
        }

    }

    static final class BitUtils {
        public static boolean testBit(int value, int i) {
            return (value & (1 << i)) != 0;
        }

        public static int setBit(int value, int i) {
            return value | (1 << i);
        }

    }
}

