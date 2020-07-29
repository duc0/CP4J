/*
 * Copyright (c) CP4J Project
 */

package com.vb.datastructure;

import com.vb.nd.GenericNDArray;
import com.vb.nd.NDShape;
import com.vb.number.GenericArithmetic;
import com.vb.number.NumberGeneric;

public class GenericFenwickTree2D implements GenericCumulativeTable2D {
    private final GenericArithmetic arithmetic;
    private final GenericNDArray array;
    private final int size;

    public GenericFenwickTree2D(GenericArithmetic arithmetic, int size) {
        this.arithmetic = arithmetic;
        this.array = new GenericNDArray(arithmetic, (size + 1) * (size + 1));
        this.array.reshape(new NDShape(size + 1, size + 1));
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public GenericArithmetic getArithmetic() {
        return arithmetic;
    }

    @Override
    public void add(int i0, int i1, NumberGeneric value) {
        for (int x0 = i0 + 1; x0 <= size; x0 += x0 & -x0) {
            for (int x1 = i1 + 1; x1 <= size; x1 += x1 & -x1) {
                this.array.add(x0, x1, value);
            }
        }
    }

    @Override
    public NumberGeneric sumFromOrigin(int i0, int i1) {
        NumberGeneric sum = arithmetic.zero();
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
