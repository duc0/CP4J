/*
 * Copyright (c) CP4J Project
 */

package com.vb.nd;

import java.util.Arrays;
import java.util.List;

public class NDShape {
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
        assert(dims.length == 2);
        return i0 * dims[1] + i1;
    }

    @Override
    public String toString() {
        return "NDShape{" +
                "dims=" + Arrays.toString(dims) +
                ", size=" + size +
                '}';
    }
}
