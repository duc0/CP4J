/*
 * Copyright (c) CP4J Project
 */

package com.vb.datastructure;

import java.util.Arrays;

public class SegmentArray {
    private final int[] left;
    private final int[] right;
    private final int nSegment;

    public SegmentArray(int nSegment) {
        this.nSegment = nSegment;
        left = new int[nSegment];
        right = new int[nSegment];
    }

    public void setSegment(int idx, int left, int right) {
        this.left[idx] = left;
        this.right[idx] = right;
    }

    public int getLeft(int id) {
        return left[id];
    }

    public int getRight(int id) {
        return right[id];
    }

    public int[] sortByLeft() {
        Integer[] result = new Integer[nSegment];
        for (int i = 0; i < nSegment; i++) {
            result[i] = i;
        }
        Arrays.sort(result, (o1, o2) -> {
            int compareLeft = Integer.compare(left[o1], left[o2]);
            if (compareLeft != 0) {
                return compareLeft;
            }
            return Integer.compare(right[o1], right[o2]);
        });
        int[] rInt = new int[nSegment];
        for (int i = 0; i < nSegment; i++) {
            rInt[i] = result[i];
        }
        return rInt;
    }

    @Override
    public String toString() {
        return "SegmentArray{" +
                "left=" + Arrays.toString(left) +
                ", right=" + Arrays.toString(right) +
                ", nSegment=" + nSegment +
                '}';
    }

    public int getSize() {
        return nSegment;
    }

    public boolean intersect(int i, int j) {
        return !(right[j] < left[i]) && !(left[j] > right[i]);
    }
}
