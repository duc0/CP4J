import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.function.Predicate;
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
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastWriter out = new FastWriter(outputStream);
        VNOI_CF_NKCITY solver = new VNOI_CF_NKCITY();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_NKCITY {
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

            out.write(BinarySearch.findMinSatisfy(minWeight, maxWeight, w -> new ConnectedComponentsDetector(graph, (u, v) -> cost.getWeight(u, v) <= w).getNumComponents() == 1));
        }

    }

    static class BinarySearch {
        public static Long findMinSatisfy(long min, long max, Predicate<Long> predicate) {
            long left = min;
            long right = max;
            Long result = null;
            while (left <= right) {
                long mid = left + (right - left) / 2;
                if (predicate.test(mid)) {
                    result = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return result;
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

        public void write(long token) throws IOException {
            bufferedWriter.write(String.valueOf(token));
            bufferedWriter.flush();
        }

    }

    static interface EdgePredicate {
        boolean test(int u, int v);

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
        private final boolean bidirected;

        public GraphAdjList(int numVertices, int edgeCapacity, boolean bidirected) {
            this.numVertices = numVertices;
            this.edgeCapacity = edgeCapacity;
            this.start = new int[edgeCapacity];
            this.end = new int[edgeCapacity];

            this.lastEdgeIndexWithStart = new int[numVertices];
            this.lastEdgeIndexWithEnd = new int[numVertices];
            this.prevEdgeIndexSameStart = new int[edgeCapacity];
            this.prevEdgeIndexSameEnd = new int[edgeCapacity];
            this.degree = new int[numVertices];
            this.bidirected = bidirected;
            for (int u = 0; u < numVertices; u++) {
                lastEdgeIndexWithStart[u] = -1;
                lastEdgeIndexWithEnd[u] = -1;
                degree[u] = 0;
                prevEdgeIndexSameStart[u] = -1;
                prevEdgeIndexSameEnd[u] = -1;
            }
        }

        public boolean isBidirected() {
            return bidirected;
        }

        public int numVertices() {
            return numVertices;
        }

        public int edgesCapacity() {
            return edgeCapacity;
        }

        public int[] next(int vertex) {
            int[] result = new int[degree[vertex]];
            int idx = lastEdgeIndexWithStart[vertex];
            int resultIdx = 0;
            while (idx >= 0) {
                result[resultIdx] = end[idx];
                idx = prevEdgeIndexSameStart[idx];
                resultIdx++;
            }
            if (isBidirected()) {
                idx = lastEdgeIndexWithEnd[vertex];
                while (idx >= 0) {
                    result[resultIdx] = start[idx];
                    idx = prevEdgeIndexSameEnd[idx];
                    resultIdx++;
                }
            }
            return result;
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
            if (isBidirected()) {
                degree[v]++;
                edgeIndexes.put(getMatrixIndex(v, u), numEdges);
            }
            numEdges++;
            return index;
        }

        public int addEdge(int u, int v) {
            if (isBidirected()) {
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

    }

    static class ConnectedComponentsDetector {
        private final Graph graph;
        private final int[] component;
        private final EdgePredicate edgePredicate;
        private int numComponents;

        public ConnectedComponentsDetector(Graph graph, EdgePredicate edgePredicate) {
            this.graph = graph;
            this.edgePredicate = edgePredicate;
            assert (graph.isBidirected());
            component = new int[graph.numVertices()];
            run();
        }

        private void dfs(int u) {
            component[u] = numComponents;
            for (int v : graph.next(u)) {
                if (edgePredicate.test(u, v) && component[v] == 0) {
                    dfs(v);
                }
            }
        }

        private void run() {
            for (int u = 0; u < graph.numVertices(); u++) {
                if (component[u] == 0) {
                    numComponents++;
                    dfs(u);
                }
            }
        }

        public int getNumComponents() {
            return numComponents;
        }

    }

    static class FastScanner {
        private final BufferedReader bufferedReader;

        public FastScanner(InputStream inputStream) {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        private String[] readTokens() throws IOException {
            return bufferedReader.readLine().trim().split("\\s+");
        }

        public IntNDArray nextLineAsIntArray() throws IOException {
            String[] tokens = readTokens();
            IntNDArray result = new IntNDArray(tokens.length);
            for (int i = 0; i < tokens.length; i++) {
                result.set(i, Integer.parseInt(tokens[i]));
            }
            return result;
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

    static final class IntGraphWeight {
        private final Graph graph;
        private final IntNDArray weight;

        public IntGraphWeight(Graph graph) {
            this.graph = graph;
            weight = new IntNDArray(graph.edgesCapacity());
        }

        public void setWeight(int u, int v, int w) {
            weight.set(graph.getEdgeIndex(u, v), w);
        }

        public int getWeight(int u, int v) {
            return weight.get(graph.getEdgeIndex(u, v));
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

    static interface Graph {
        boolean isBidirected();

        int numVertices();

        int edgesCapacity();

        int[] next(int vertex);

        int addEdge(int u, int v);

        int getEdgeIndex(int u, int v);

    }
}

