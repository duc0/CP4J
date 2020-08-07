/*
 * Copyright (c) CP4J Project
 */

package com.vb.datastructure.cell2d;

public class Size2D {
    private final int nRow;
    private final int nCol;

    public Size2D(int nRow, int nCol) {
        this.nRow = nRow;
        this.nCol = nCol;
    }

    public int numRows() {
        return nRow;
    }

    public int numColumns() {
        return nCol;
    }

    public boolean inside(int row, int col) {
        return 0 <= row && row < nRow && 0 <= col && col < nCol;
    }

    public long size() {
        return nRow * nCol;
    }
}
