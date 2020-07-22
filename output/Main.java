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
        VNOI_CF_NKTICK solver = new VNOI_CF_NKTICK();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_NKTICK {
        public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
            int len = in.nextLineAsInt();
            NDArray<Integer> timeTaken = in.nextLineAsIntArray();
            NDArray<Integer> timeCostForTwo = in.nextLineAsIntArray();
            NDArray<Integer> f = new NDArray<>(len, Integer.class);
            f.setI(0, timeTaken.getI(0));
            for (int i = 1; i < len; i++) {
                f.setI(i, Math.min(
                        f.getI(i - 1) + timeTaken.getI(i),
                        (i >= 2 ? f.getI(i - 2) : 0) + timeCostForTwo.getI(i - 1)));
            }
            out.write(f.getI(len - 1));
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

        public NDArray<Integer> nextLineAsIntArray() throws IOException {
            String line = bufferedReader.readLine();
            String[] lineS = line.split(" ");
            NDArray<Integer> result = new NDArray<>(lineS.length, Integer.class);
            for (int i = 0; i < lineS.length; i++) {
                result.setI(i, Integer.parseInt(lineS[i]));
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

        public NDArray(int capacity, Class<T> clz) {
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

        public int getI(int i0) {
            assert (shape.getDimension() == 1);
            return intBuffer[i0];
        }

        public void setI(int i0, int val) {
            assert (shape.getDimension() == 1);
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

        public String toString() {
            return "NDShape{" +
                    "dims=" + Arrays.toString(dims) +
                    ", size=" + size +
                    '}';
        }

    }
}

