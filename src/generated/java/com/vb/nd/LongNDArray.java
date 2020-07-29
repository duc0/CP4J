package com.vb.nd;


import com.vb.number.LongArithmetic;

import java.util.Arrays;

public class LongNDArray {
    private final LongArithmetic arithmetic;
    private final int capacity;
    protected final long[] buffer;
    protected NDShape shape;

    public LongNDArray(LongArithmetic arithmetic, int capacity) {
        this.arithmetic = arithmetic;
        this.capacity = capacity;
        this.buffer = new long[capacity];
        this.shape = new NDShape(capacity);
    }

    public void reshape(NDShape shape) {
        if (shape.size() > capacity) {
            throw new RuntimeException("Capacity is not enough for size " + shape.size());
        }
        this.shape = shape;
    }

    public long get(int i0) {
        assert (shape.rank() == 1);
        return buffer[i0];
    }

    public long get(int i0, int i1) {
        assert (shape.rank() == 2);
        assert (0 <= i0 && i0 < shape.dim(0)) : "i0 = " + i0;
        assert (0 <= i1 && i1 < shape.dim(1)) : "i1 = " + i1;
        return buffer[shape.d2(i0, i1)];
    }

    long get(NDIndex index) {
        if (index.rank() == 1) {
            return get(index.index(0));
        } else if (index.rank() == 2) {
            return get(index.index(0), index.index(1));
        } else {
            throw new RuntimeException("Unimplemented");
        }
    }

    public void set(int i0, long val) {
        assert (shape.rank() == 1);
        buffer[i0] = val;
    }

    public void set(int i0, int i1, long val) {
        assert (shape.rank() == 2);
        buffer[shape.d2(i0, i1)] = val;
    }

    public long max() {
        return max(NDSliceRanges.all(shape.rank()));
    }

    public long max(NDSliceRanges ndSliceRanges) {
        NDIndex index = NDIndex.startIndex(shape, ndSliceRanges);
        long result = get(index);
        do {
            long value = get(index);
            if (arithmetic.compare(value, result) > 0) {
                result = value;
            }
        } while (index.next(shape, ndSliceRanges));
        return result;
    }

    public long min() {
        return min(NDSliceRanges.all(shape.rank()));
    }

    public long min(NDSliceRanges ndSliceRanges) {
        NDIndex index = NDIndex.startIndex(shape, ndSliceRanges);
        long result = get(index);
        do {
            long value = get(index);
            if (arithmetic.compare(value, result) < 0) {
                result = value;
            }
        } while (index.next(shape, ndSliceRanges));
        return result;
    }

    public void setIfMore(int i0, long val) {
        assert (shape.rank() == 1);
        if (arithmetic.compare(val, buffer[i0]) > 0) {
            buffer[i0] = val;
        }
    }

    @Override
    public String toString() {
        return "NDArray{" +
                "buffer=" + Arrays.toString(buffer) +
                ", capacity=" + capacity +
                ", shape=" + shape +
                '}';
    }

    public void add(int i0, int i1, long value) {
        assert (shape.rank() == 2);
        int idx = shape.d2(i0, i1);
        buffer[idx] = arithmetic.add(buffer[idx], value);
    }
}
