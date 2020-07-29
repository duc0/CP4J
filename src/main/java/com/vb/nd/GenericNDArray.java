package com.vb.nd;

import com.vb.number.NumberGeneric;
import com.vb.number.GenericArithmetic;

import java.util.Arrays;

public class GenericNDArray {
    private final GenericArithmetic arithmetic;
    private final int capacity;
    protected final NumberGeneric[] buffer;
    protected NDShape shape;

    public GenericNDArray(GenericArithmetic arithmetic, int capacity) {
        this.arithmetic = arithmetic;
        this.capacity = capacity;
        this.buffer = new NumberGeneric[capacity];
        this.shape = new NDShape(capacity);
    }

    public void reshape(NDShape shape) {
        if (shape.size() > capacity) {
            throw new RuntimeException("Capacity is not enough for size " + shape.size());
        }
        this.shape = shape;
    }

    public NumberGeneric get(int i0) {
        assert (shape.rank() == 1);
        return buffer[i0];
    }

    public NumberGeneric get(int i0, int i1) {
        assert (shape.rank() == 2);
        assert (0 <= i0 && i0 < shape.dim(0)) : "i0 = " + i0;
        assert (0 <= i1 && i1 < shape.dim(1)) : "i1 = " + i1;
        return buffer[shape.d2(i0, i1)];
    }

    NumberGeneric get(NDIndex index) {
        if (index.rank() == 1) {
            return get(index.index(0));
        } else if (index.rank() == 2) {
            return get(index.index(0), index.index(1));
        } else {
            throw new RuntimeException("Unimplemented");
        }
    }

    public void set(int i0, NumberGeneric val) {
        assert (shape.rank() == 1);
        buffer[i0] = val;
    }

    public void set(int i0, int i1, NumberGeneric val) {
        assert (shape.rank() == 2);
        buffer[shape.d2(i0, i1)] = val;
    }

    public NumberGeneric max() {
        return max(NDSliceRanges.all(shape.rank()));
    }

    public NumberGeneric max(NDSliceRanges ndSliceRanges) {
        NDIndex index = NDIndex.startIndex(shape, ndSliceRanges);
        NumberGeneric result = get(index);
        do {
            NumberGeneric value = get(index);
            if (arithmetic.compare(value, result) > 0) {
                result = value;
            }
        } while (index.next(shape, ndSliceRanges));
        return result;
    }

    public NumberGeneric min() {
        return min(NDSliceRanges.all(shape.rank()));
    }

    public NumberGeneric min(NDSliceRanges ndSliceRanges) {
        NDIndex index = NDIndex.startIndex(shape, ndSliceRanges);
        NumberGeneric result = get(index);
        do {
            NumberGeneric value = get(index);
            if (arithmetic.compare(value, result) < 0) {
                result = value;
            }
        } while (index.next(shape, ndSliceRanges));
        return result;
    }

    public void setIfMore(int i0, NumberGeneric val) {
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

    public void add(int i0, int i1, NumberGeneric value) {
        assert (shape.rank() == 2);
        int idx = shape.d2(i0, i1);
        buffer[idx] = arithmetic.add(buffer[idx], value);
    }
}
