/*
 * Copyright (c) CP4J Project
 */

package com.vb.datastructure;

import com.vb.datastructure.cell2d.Cell2D;
import com.vb.datastructure.cell2d.Size2D;

public final class StringBoard {
  private final Size2D size;
  private final String[] board;

  public StringBoard(int nRow, int nCol) {
    this.size = new Size2D(nRow, nCol);
    board = new String[nRow];
  }

  public boolean inBoard(int row, int col) {
    return size.inside(row, col);
  }

  public boolean inBoard(Cell2D cell) {
    return size.inside(cell.row, cell.col);
  }

  public char get(int row, int col) {
    return board[row].charAt(col);
  }

  public char get(Cell2D cell) {
    return get(cell.row, cell.col);
  }

  public int numColumns() {
    return size.numColumns();
  }

  public int numRows() {
    return size.numRows();
  }

  public void setRow(int row, String s) {
    assert(s.length() == numColumns());
    assert(0 <= row && row < size.numRows());
    board[row] = s;
  }

  public Size2D getSize() {
    return size;
  }
}
