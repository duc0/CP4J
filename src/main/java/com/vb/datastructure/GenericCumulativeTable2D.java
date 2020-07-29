package com.vb.datastructure;

import com.vb.number.GenericArithmetic;
import com.vb.number.NumberGeneric;

public interface GenericCumulativeTable2D {
    int getSize();

    GenericArithmetic getArithmetic();

    // Add a number of a cell.
    void add(int i0, int i1, NumberGeneric value);

    // Sum of rectangle from origin to a cell.
    NumberGeneric sumFromOrigin(int i0, int i1);

    default NumberGeneric sum(int i0, int i1, int j0, int j1) {
        int n = getSize();
        assert(0 <= i0 && i0 <= j0 && j0 < n);
        assert(0 <= i1 && i1 <= j1 && j1 < n);
        GenericArithmetic ar = getArithmetic();
        return ar.add(ar.subtract(ar.subtract(
                sumFromOrigin(j0, j1), sumFromOrigin(i0 - 1, j1)),
                sumFromOrigin(j0, i1 - 1)),
                sumFromOrigin(i0 - 1, i1 - 1));
    }
}
