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
        VNOI_CF_QBMAX solver = new VNOI_CF_QBMAX();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_QBMAX {
        public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
            NDShape size = in.nextLineAsShape();
            IntNDArray matrix = in.nextLinesAs2DIntArray(size);

            for (int col = 1; col < size.dim(1); col++) {
                for (int row = 0; row < size.dim(0); row++) {
                    int best = matrix.get(row, col - 1);
                    if (row > 0) {
                        best = Math.max(best, matrix.get(row - 1, col - 1));
                    }
                    if (row < size.dim(0) - 1) {
                        best = Math.max(best, matrix.get(row + 1, col - 1));
                    }
                    int newBest = best + matrix.get(row, col);
                    matrix.set(row, col, newBest);
                }
            }

            out.write(matrix.max(NDSliceRanges.col2D(-1)));
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

        int getI(int i0) {
            assert (shape.rank() == 1);
            return intBuffer[i0];
        }

        int getI(int i0, int i1) {
            assert (shape.rank() == 2);
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

        void setI(int i0, int i1, int val) {
            assert (shape.rank() == 2);
            intBuffer[shape.d2(i0, i1)] = val;
        }

        int maxI(NDSliceRanges ndSliceRanges) {
            NDIndex index = NDIndex.startIndex(shape, ndSliceRanges);
            int result = getI(index);
            do {
                int value = getI(index);
                if (value > result) {
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

        public IntNDArray nextLinesAs2DIntArray(NDShape shape) throws IOException {
            assert (shape.rank() == 2);
            IntNDArray result = new IntNDArray((int) shape.size());
            result.reshape(shape);
            for (int row = 0; row < shape.dim(0); row++) {
                String[] tokens = readTokens();
                for (int col = 0; col < tokens.length; col++) {
                    result.set(row, col, Integer.parseInt(tokens[col]));
                }
            }
            return result;
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

        public int max(NDSliceRanges ndSliceRanges) {
            return maxI(ndSliceRanges);
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
}

