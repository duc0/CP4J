import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static void main(String[] args) throws IOException  {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastWriter out = new FastWriter(outputStream);
        VNOI_CF_QBMAX solver = new VNOI_CF_QBMAX();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_QBMAX {
        public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
            NDShape size = in.nextLineAsShape();
            NDArray<Integer> matrix = in.nextLinesAs2DIntArray(size);

            int result = Integer.MIN_VALUE;
            for (int col = 1; col < size.dim(1); col++) {
                for (int row = 0; row < size.dim(0); row++) {
                    int best = matrix.getI(row, col - 1);
                    if (row > 0) {
                        best = Math.max(best, matrix.getI(row - 1, col - 1));
                    }
                    if (row < size.dim(0) - 1) {
                        best = Math.max(best, matrix.getI(row + 1, col - 1));
                    }
                    int newBest = best + matrix.getI(row, col);
                    matrix.setI(row, col, newBest);

                    if (col == size.dim(1) - 1) {
                        result = Math.max(result, matrix.getI(row, col));
                    }
                }
            }

            out.write(result);
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

        public NDShape nextLineAsShape() throws IOException {
            String[] tokens = readTokens();
            int[] dims = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                dims[i] = Integer.parseInt(tokens[i]);
            }
            return new NDShape(dims);
        }

        public NDArray<Integer> nextLinesAs2DIntArray(NDShape shape) throws IOException {
            assert (shape.getDimension() == 2);
            NDArray<Integer> result = NDArray.intArray((int) shape.size());
            result.reshape(shape);
            for (int row = 0; row < shape.dim(0); row++) {
                String[] tokens = readTokens();
                for (int col = 0; col < tokens.length; col++) {
                    result.setI(row, col, Integer.parseInt(tokens[col]));
                }
            }
            return result;
        }

    }

    static class NDArray<T extends Number> {
        private final int[] intBuffer;
        private final long[] longBuffer;
        private final double[] doubleBuffer;
        private final int capacity;
        private final Class<T> clz;
        private NDShape shape;

        private NDArray(int capacity, Class<T> clz) {
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

        public static NDArray<Integer> intArray(int capacity) {
            return new NDArray<>(capacity, Integer.class);
        }

        public void reshape(NDShape shape) {
            if (shape.size() > capacity) {
                throw new RuntimeException("Capacity is not enough for size " + shape.size());
            }
            this.shape = shape;
        }

        public int getI(int i0, int i1) {
            assert (shape.getDimension() == 2);
            return intBuffer[shape.d2(i0, i1)];
        }

        public void setI(int i0, int i1, int val) {
            assert (shape.getDimension() == 2);
            intBuffer[shape.d2(i0, i1)] = val;
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

        public void write(int token) throws IOException {
            bufferedWriter.write(String.valueOf(token));
            bufferedWriter.flush();
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

        public int getDimension() {
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
}

