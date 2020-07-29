/*
 * Copyright (c) CP4J Project
 */

package com.vb.datastructure;

import com.vb.nd.LongNDArray;
import com.vb.nd.NDShape;
import com.vb.number.LongArithmetic;


public class LongFenwickTree2D implements LongCumulativeTable2D {
    private final LongArithmetic arithmetic;
    private final LongNDArray array;
    private final int size;

    public LongFenwickTree2D(LongArithmetic arithmetic, int size) {
        this.arithmetic = arithmetic;
        this.array = new LongNDArray(arithmetic, (size + 1) * (size + 1));
        this.array.reshape(new NDShape(size + 1, size + 1));
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public LongArithmetic getArithmetic() {
        return arithmetic;
    }

    @Override
    public void add(int i0, int i1, long value) {
        for (int x0 = i0 + 1; x0 <= size; x0 += x0 & -x0) {
            for (int x1 = i1 + 1; x1 <= size; x1 += x1 & -x1) {
                this.array.add(x0, x1, value);
            }
        }
    }

    @Override
    public long sumFromOrigin(int i0, int i1) {
        long sum = arithmetic.zero();
        if (i0 < 0 || i1 < 0) {
            return sum;
        }
        for (int x0 = i0 + 1; x0 > 0; x0 -= x0 & -x0) {
            for (int x1 = i1 + 1; x1 > 0; x1 -= x1 & -x1) {
                sum = arithmetic.add(sum, array.get(x0, x1));
            }
        }
        return sum;
    }
}
