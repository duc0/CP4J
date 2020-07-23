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
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastWriter out = new FastWriter(outputStream);
        VNOI_CF_NKPALIN solver = new VNOI_CF_NKPALIN();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_NKPALIN {
        String trace(String s, IntNDArray f, int l, int left) {
            if (l <= 0) {
                return "";
            }
            if (l == 1) {
                return String.valueOf(s.charAt(left));
            }
            int right = l + left - 1;
            if (s.charAt(left) == s.charAt(right)) {
                return s.charAt(left) + trace(s, f, l - 2, left + 1) + s.charAt(right);
            } else if (f.get(l, left) == f.get(l - 1, left)) {
                return trace(s, f, l - 1, left);
            } else {
                return trace(s, f, l - 1, left + 1);
            }
        }

        public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
            String s = in.nextLineAsString();
            int n = s.length();
            IntNDArray f = new IntNDArray((n + 1) * n);
            f.reshape(new NDShape(n + 1, n));
            for (int l = 1; l <= n; l++) {
                for (int left = 0; left + l - 1 < n; left++) {
                    int right = left + l - 1;
                    int value;
                    if (left == right) {
                        value = 1;
                    } else {
                        if (s.charAt(left) == s.charAt(right)) {
                            if (l == 1) {
                                value = 2;
                            } else {
                                value = f.get(l - 2, left + 1) + 2;
                            }
                        } else {
                            value = Math.max(f.get(l - 1, left), f.get(l - 1, left + 1));
                        }
                    }
                    f.set(l, left, value);
                }
            }
            String result = trace(s, f, n, 0);
            out.write(result);
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

        public void reshape(NDShape shape) {
            if (shape.size() > capacity) {
                throw new RuntimeException("Capacity is not enough for size " + shape.size());
            }
            this.shape = shape;
        }

        int getI(int i0, int i1) {
            assert (shape.rank() == 2);
            return intBuffer[shape.d2(i0, i1)];
        }

        void setI(int i0, int i1, int val) {
            assert (shape.rank() == 2);
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

    static class FastScanner {
        private final BufferedReader bufferedReader;

        public FastScanner(InputStream inputStream) {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public String nextLineAsString() throws IOException {
            return bufferedReader.readLine();
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

        public void write(String token) throws IOException {
            bufferedWriter.write(token);
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

        public int rank() {
            return dims.length;
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

    static class IntNDArray extends NDArray<Integer> {
        public IntNDArray(int capacity) {
            super(capacity, Integer.class);
        }

        public int get(int i0, int i1) {
            return getI(i0, i1);
        }

        public void set(int i0, int i1, int val) {
            setI(i0, i1, val);
        }

    }
}

