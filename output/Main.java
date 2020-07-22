import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

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
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        VNOI_CF_LIQ solver = new VNOI_CF_LIQ();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_LIQ {
        public void solve(int testNumber, Scanner in, PrintWriter out) {
            int len = in.nextInt();
            int[] inp = new int[len];
            for (int idx = 0; idx < len; idx++) {
                inp[idx] = in.nextInt();
            }
            NDArray<Long> f = new NDArray<>(len, Long.class);
            for (int idx = 0; idx < len; idx++) {
                f.setL(idx, 1);
                for (int prev = 0; prev < idx; prev++) {
                    if (inp[prev] < inp[idx]) {
                        f.setIfMoreL(idx, f.getL(prev) + 1);
                    }
                }
            }
            out.print(f.maxL());
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

    static class NDArray<T extends Number> {
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
            } else if (clz == Long.class) {
                this.longBuffer = new long[capacity];
                this.doubleBuffer = null;
            } else {
                throw new RuntimeException("Unsupported number types");
            }
            this.shape = new NDShape(capacity);
        }

        public long getL(int i0) {
            assert (shape.getDimension() == 1);
            return longBuffer[i0];
        }

        public void setL(int i0, long val) {
            assert (shape.getDimension() == 1);
            longBuffer[i0] = val;
        }

        public long maxL() {
            long result = longBuffer[0];
            for (long x : longBuffer) {
                if (x > result) {
                    result = x;
                }
            }
            return result;
        }

        public void setIfMoreL(int i0, long val) {
            assert (shape.getDimension() == 1);
            if (val > longBuffer[i0]) {
                longBuffer[i0] = val;
            }
        }

    }
}

