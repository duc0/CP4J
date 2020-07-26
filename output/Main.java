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
import java.util.Comparator;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author duc
 */
public class Main {
    public static void main(String[] args) throws IOException  {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastWriter out = new FastWriter(outputStream);
        VNOI_CF_QBMST solver = new VNOI_CF_QBMST();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_QBMST {
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

    static class DisjointSet {
        private final int size;
        private final int[] parent;

        public DisjointSet(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        private int getRoot(int u) {
            if (parent[u] == u) {
                return u;
            } else {
                return parent[u] = getRoot(parent[u]);
            }
        }

        public void merge(int u, int v) {
            parent[getRoot(u)] = getRoot(v);
        }

        public boolean inSameSet(int u, int v) {
            return getRoot(u) == getRoot(v);
        }

    }

    static interface Algorithm<I, O> {
        long getComplexity(I input);

        O run(I input);

    }

    static interface GraphWeight<T extends Number> {
        int[] getEdgesSortedByWeight();

        T getWeight(int edgeId);

    }

    static interface Graph {
        int numVertices();

        int numEdges();

        int edgesCapacity();

        int addEdge(int u, int v);

        int getEdgeIndex(int u, int v);

        int getEdgeStart(int edgeId);

        int getEdgeEnd(int edgeId);

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

        public int numEdges() {
            return numEdges;
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

        public int getEdgeStart(int edgeId) {
            return start[edgeId];
        }

        public int getEdgeEnd(int edgeId) {
            return end[edgeId];
        }

    }

    static class KruskalAlgorithm<T extends Number> implements Algorithm<KruskalAlgorithm.Input<T>, KruskalAlgorithm.Output> {
        public long getComplexity(KruskalAlgorithm.Input<T> input) {
            return (long) (input.graph.numEdges() * Math.log(input.graph.numVertices()));
        }

        public KruskalAlgorithm.Output run(KruskalAlgorithm.Input<T> input) {
            DisjointSet dj = new DisjointSet(input.graph.numVertices());
            int[] sortedEdges = input.weight.getEdgesSortedByWeight();
            KruskalAlgorithm.Output output = new KruskalAlgorithm.Output();
            for (int edgeId : sortedEdges) {
                int u = input.graph.getEdgeStart(edgeId);
                int v = input.graph.getEdgeEnd(edgeId);
                if (!dj.inSameSet(u, v)) {
                    dj.merge(u, v);
                    output.minimumWeight = output.minimumWeight + input.weight.getWeight(edgeId).doubleValue();
                }
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

    static class IntNDArray extends NDArray<Integer> {
        public IntNDArray(int capacity) {
            super(capacity, Integer.class);
        }

        public int get(int i0) {
            return getI(i0);
        }

        public void set(int i0, int val) {
            setI(i0, val);
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

    static class NDArray<T extends Number> {
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

        int getI(int i0) {
            assert (shape.rank() == 1);
            return intBuffer[i0];
        }

        void setI(int i0, int val) {
            assert (shape.rank() == 1);
            intBuffer[i0] = val;
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

        public String toString() {
            return "NDShape{" +
                    "dims=" + Arrays.toString(dims) +
                    ", size=" + size +
                    '}';
        }

    }

    static final class GraphWeightUtils {
        static int[] getEdgesSorted(Graph graph, Comparator<Integer> edgeComparison) {
            Integer[] edge = new Integer[graph.numEdges()];
            for (int i = 0; i < graph.numEdges(); i++) {
                edge[i] = i;
            }
            Arrays.sort(edge, edgeComparison);
            int[] result = new int[graph.numEdges()];
            for (int i = 0; i < graph.numEdges(); i++) {
                result[i] = edge[i];
            }
            return result;
        }

    }

    static class FastScanner {
        private final BufferedReader bufferedReader;

        public FastScanner(InputStream inputStream) {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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

        public Integer getWeight(int edgeId) {
            return weight.get(edgeId);
        }

        public int[] getEdgesSortedByWeight() {
            return GraphWeightUtils.getEdgesSorted(graph, Comparator.comparingInt(this::getWeight));
        }

    }
}

