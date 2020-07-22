package com.vb.nd;

import java.util.Arrays;

public class NDArray<T extends Number> {
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


    long getL(int i0) {
        assert(shape.rank() == 1);
        return longBuffer[i0];
    }

    long getL(int i0, int i1) {
        assert(shape.rank() == 2);
        return longBuffer[shape.d2(i0, i1)];
    }

    void setL(int i0, long val) {
        assert(shape.rank() == 1);
        longBuffer[i0] = val;
    }

    void setL(int i0, int i1, long val) {
        assert(shape.rank() == 2);
        longBuffer[shape.d2(i0, i1)] = val;
    }

    void setIfMoreL(int i0, long val) {
        assert(shape.rank() == 1);
        if (val > longBuffer[i0]) {
            longBuffer[i0] = val;
        }
    }

    int getI(int i0) {
        assert(shape.rank() == 1);
        return intBuffer[i0];
    }

    int getI(int i0, int i1) {
        assert(shape.rank() == 2);
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
        assert(shape.rank() == 1);
        intBuffer[i0] = val;
    }

    void setI(int i0, int i1, int val) {
        assert(shape.rank() == 2);
        intBuffer[shape.d2(i0, i1)] = val;
    }

    int maxI() {
        return maxI(NDSliceRanges.all(shape.rank()));
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

    void setIfMoreI(int i0, int val) {
        assert(shape.rank() == 1);
        if (val > intBuffer[i0]) {
            intBuffer[i0] = val;
        }
    }

    @Override
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
