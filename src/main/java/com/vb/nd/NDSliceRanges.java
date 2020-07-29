/*
 * Copyright (c) CP4J Project
 */

package com.vb.nd;

import java.util.Arrays;

public class NDSliceRanges {
    private final int[] sliceStart;
    private final int[] sliceEnd;

    public static NDSliceRanges all(int rank) {
        int[] sliceStart = new int[rank];
        int[] sliceEnd = new int[rank];
        return new NDSliceRanges(sliceStart, sliceEnd);
    }

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
        assert(sliceStart.length == sliceEnd.length);
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

    @Override
    public String toString() {
        return "NDSliceRanges{" +
                "sliceStart=" + Arrays.toString(sliceStart) +
                ", sliceEnd=" + Arrays.toString(sliceEnd) +
                '}';
    }
}
