/*
 * Copyright (c) CP4J Project
 */

package com.vb.datastructure;

import com.vb.number.DoubleArithmetic;


public interface DoubleCumulativeTable2D {
    int getSize();

    DoubleArithmetic getArithmetic();

    // Add a number of a cell.
    void add(int i0, int i1, double value);

    // Sum of rectangle from origin to a cell.
    double sumFromOrigin(int i0, int i1);

    default double sum(int i0, int i1, int j0, int j1) {
        int n = getSize();
        assert(0 <= i0 && i0 <= j0 && j0 < n);
        assert(0 <= i1 && i1 <= j1 && j1 < n);
        DoubleArithmetic ar = getArithmetic();
        return ar.add(ar.subtract(ar.subtract(
                sumFromOrigin(j0, j1), sumFromOrigin(i0 - 1, j1)),
                sumFromOrigin(j0, i1 - 1)),
                sumFromOrigin(i0 - 1, i1 - 1));
    }
}
