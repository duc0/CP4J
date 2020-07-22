package com.vb.nd;

import java.util.Arrays;

public class NDIndex {
    private final int[] dims;

    public NDIndex(int... dims) {
        this.dims = dims;
    }

    public int getDimension() {
        return dims.length;
    }
    public int dim(int index) {
        return dims[index];
    }

}
