package com.vb.nd;

public class NDArray<T extends Number> {
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

    public void reshape(NDShape shape) {
        if (shape.size() > capacity) {
            throw new RuntimeException("Capacity is not enough for size " + shape.size());
        }
        this.shape = shape;
    }

    public long getL(int i0) {
        assert(shape.getDimension() == 1);
        return longBuffer[i0];
    }

    public long getL(int i0, int i1) {
        assert(shape.getDimension() == 2);
        return longBuffer[shape.d2(i0, i1)];
    }

    public void setL(int i0, long val) {
        assert(shape.getDimension() == 1);
        longBuffer[i0] = val;
    }

    public void setL(int i0, int i1, long val) {
        assert(shape.getDimension() == 2);
        longBuffer[i0 * shape.dim(0) + i1] = val;
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
        assert(shape.getDimension() == 1);
        if (val > longBuffer[i0]) {
            longBuffer[i0] = val;
        }
    }
}
