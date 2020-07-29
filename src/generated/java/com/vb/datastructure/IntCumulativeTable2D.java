/*
 * Copyright (c) CP4J Project
 */

package com.vb.datastructure;

import com.vb.number.IntArithmetic;


public interface IntCumulativeTable2D {
    int getSize();

    IntArithmetic getArithmetic();

    // Add a number of a cell.
    void add(int i0, int i1, int value);

    // Sum of rectangle from origin to a cell.
    int sumFromOrigin(int i0, int i1);

    default int sum(int i0, int i1, int j0, int j1) {
        int n = getSize();
        assert(0 <= i0 && i0 <= j0 && j0 < n);
        assert(0 <= i1 && i1 <= j1 && j1 < n);
        IntArithmetic ar = getArithmetic();
        return ar.add(ar.subtract(ar.subtract(
                sumFromOrigin(j0, j1), sumFromOrigin(i0 - 1, j1)),
                sumFromOrigin(j0, i1 - 1)),
                sumFromOrigin(i0 - 1, i1 - 1));
    }
}
