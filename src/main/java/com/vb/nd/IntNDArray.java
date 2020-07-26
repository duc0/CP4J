package com.vb.nd;

public class IntNDArray extends NDArray<Integer> {
    public IntNDArray(int capacity) {
        super(capacity, Integer.class);
    }

    public int get(int i0) {
        return getI(i0);
    }

    public int get(int i0, int i1) {
        return getI(i0, i1);
    }

    public int get(NDIndex index) {
        return getI(index);
    }

    public void set(int i0, int val) {
        setI(i0, val);
    }

    public void set(int i0, int i1, int val) {
        setI(i0, i1, val);
    }

    public int max() {
        return maxI();
    }

    public int max(NDSliceRanges ndSliceRanges) {
        return maxI(ndSliceRanges);
    }

    public int min() {
        return minI();
    }

    public int min(NDSliceRanges ndSliceRanges) {
        return minI(ndSliceRanges);
    }

    public void setIfMore(int i0, int val) {
        setIfMoreI(i0, val);
    }
}
