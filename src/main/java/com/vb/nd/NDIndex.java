/*
 * Copyright (c) CP4J Project
 */

package com.vb.nd;

import java.util.Arrays;

public class NDIndex {
    private final int[] indexes;

    public NDIndex(int... indexes) {
        this.indexes = indexes;
    }

    public int rank() {
        return indexes.length;
    }
    public int index(int index) {
        return indexes[index];
    }

    public static NDIndex startIndex(NDShape ndShape, NDSliceRanges ndSliceRanges) {
        int[] indexes = new int[ndSliceRanges.rank()];
        for (int i = 0; i < ndSliceRanges.rank(); i++) {
            indexes[i] = ndSliceRanges.getSliceStart(ndShape, i);
        }
        return new NDIndex(indexes);
    }

    public boolean next(NDShape ndShape, NDSliceRanges ndSliceRanges) {
        int i = ndSliceRanges.rank() - 1;
        for (; i >= 0; i--) {
            if (indexes[i] < ndSliceRanges.getSliceEnd(ndShape, i) - 1) {
                indexes[i]++;
                break;
            }
            indexes[i] = ndSliceRanges.getSliceStart(ndShape, i);
        }
        return i >= 0;
    }

    @Override
    public String toString() {
        return "NDIndex{" +
                "indexes=" + Arrays.toString(indexes) +
                '}';
    }
}
