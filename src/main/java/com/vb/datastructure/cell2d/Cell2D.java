/*
 * Copyright (c) CP4J Project
 */

package com.vb.datastructure.cell2d;

// Mutable for performance reason.
public class Cell2D {
  public int row;
  public int col;

  public Cell2D(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public void applyInline(Direction direction) {
    row = row + direction.dRow;
    col = col + direction.dCol;
  }

  public Cell2D apply(Direction direction) {
    return new Cell2D(row + direction.dRow, col + direction.dCol);
  }

  public boolean next(CellIterationOrder order, Size2D size) {
    if (order == CellIterationOrder.ROW_MAJOR) {
      if (col + 1 < size.numColumns()) {
        col++;
      } else {
        col = 0;
        row++;
      }
      return row < size.numRows();
    } else if (order == CellIterationOrder.DIAG_UP_RIGHT) {
      boolean isEnd = row == size.numRows() - 1 && col == size.numColumns() - 1;
      if (size.inside(row - 1, col + 1)) {
        row--;
        col++;
      } else {
        int nextSum = row + col + 1;
        int firstCol = nextSum - (size.numRows() - 1);
        if (0 <= firstCol && firstCol < size.numColumns()) {
          row = size.numRows() - 1;
          col = firstCol;
        } else {
          col = 0;
          row = nextSum;
        }
      }
      return !isEnd;
    } else {
      throw new RuntimeException("Invalid ordering");
    }
  }
}
