/*
 * Copyright (c) CP4J Project
 */

package com.vb.tasks;

import com.vb.datastructure.StringBoard;
import com.vb.datastructure.cell2d.Cell2D;
import com.vb.datastructure.cell2d.CellIterationOrder;
import com.vb.datastructure.cell2d.Direction;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.logging.Log;
import com.vb.nd.IntNDArray;
import com.vb.nd.NDShape;
import com.vb.number.DefaultIntArithmetic;
import com.vb.number.IntArithmetic;
import com.vb.task.CPTaskSolver;
import com.vb.utils.MathUtils;

import java.io.IOException;

// Powered by CP4J - https://github.com/duc0/CP4J
public class CF1393_D implements CPTaskSolver {

  long solveBruteForce(StringBoard board) {
    long result = 0;
    Cell2D cell = new Cell2D(0, 0);
    do {
      int len = 1;
      while (true) {
        result++;
        len++;
        if (!isOuterSquare(board, cell.row, cell.col, len)) {
          break;
        }
      }
    } while (cell.next(CellIterationOrder.ROW_MAJOR, board.getSize()));
    return result;
  }

  private boolean isOuterSquare(StringBoard board, int row, int col, int len) {
    char ch = board.get(row, col);
    if (!board.inBoard(row, col - 1) || board.get(row, col - 1) != ch) {
      return false;
    }
    for (int i = 0, curRow = row, curCol = col; i < len; i++) {
      if (!board.inBoard(curRow, curCol) || board.get(curRow, curCol) != ch) {
        return false;
      }
      curRow -= 1;
      curCol -= 1;
    }
    for (int i = 0, curRow = row, curCol = col; i < len; i++) {
      if (!board.inBoard(curRow, curCol) || board.get(curRow, curCol) != ch) {
        return false;
      }
      curRow += 1;
      curCol -= 1;
    }
    int firstCol = col - 2 * (len - 1);
    for (int i = 0, curRow = row, curCol = firstCol; i < len; i++) {
      if (!board.inBoard(curRow, curCol) || board.get(curRow, curCol) != ch) {
        return false;
      }
      curRow += 1;
      curCol += 1;
    }
    for (int i = 0, curRow = row, curCol = firstCol; i < len; i++) {
      if (!board.inBoard(curRow, curCol) || board.get(curRow, curCol) != ch) {
        return false;
      }
      curRow -= 1;
      curCol += 1;
    }
    return true;
  }

  long solveDp(StringBoard board) {
    IntArithmetic arithmetic = new DefaultIntArithmetic();
    NDShape shape = new NDShape(board.numRows(), board.numColumns());
    IntNDArray longestDiag1 = new IntNDArray(arithmetic, shape);
    IntNDArray longestDiag2 = new IntNDArray(arithmetic, shape);
    IntNDArray longestSquare = new IntNDArray(arithmetic, shape);

    long result = 0;
    Cell2D cur = new Cell2D(0, 0);
    do {
      char ch = board.get(cur);
      Cell2D prevDiag1 = cur.apply(Direction.DOWN_LEFT);
      boolean hasDiag1 = false;
      if (board.inBoard(prevDiag1) && board.get(prevDiag1) == ch) {
        hasDiag1 = true;
        longestDiag1.set(cur, longestDiag1.get(prevDiag1) + 1);
      } else {
        longestDiag1.set(cur, 1);
      }

      Cell2D prevDiag2 = cur.apply(Direction.UP_LEFT);
      boolean hasDiag2 = false;
      if (board.inBoard(prevDiag2) && board.get(prevDiag2) == ch) {
        hasDiag2 = true;
        longestDiag2.set(cur, longestDiag2.get(prevDiag2) + 1);
      } else {
        longestDiag2.set(cur, 1);
      }

      Cell2D prevSquare = cur.apply(Direction.LEFT);
      if (board.inBoard(prevSquare) && board.get(prevSquare) == ch && hasDiag1 && hasDiag2) {
        int candidate =
            MathUtils.min(
                longestSquare.get(prevSquare),
                longestDiag1.get(cur) - 1,
                longestDiag2.get(cur) - 1);
        if (candidate > 0
            && board.inBoard(cur.row, cur.col - 2 * candidate)
            && longestSquare.get(prevDiag1) >= candidate
            && longestSquare.get(prevDiag2) >= candidate
            && board.get(cur.row, cur.col - 2 * candidate) == ch) {
          candidate++;
        }
        longestSquare.set(cur, candidate);
      } else {
        longestSquare.set(cur, 1);
      }
      result += longestSquare.get(cur);
      Log.d("Value {0} {1} {2}", cur.row + 1, cur.col + 1, longestSquare.get(cur));
    } while (cur.next(CellIterationOrder.DIAG_UP_RIGHT, board.getSize()));
    return result;
  }

  @Override
  public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
    // Log.setDebug(true);
    int[] header = in.readTokensAsIntArray();
    StringBoard board = new StringBoard(header[0], header[1]);
    for (int row = 0; row < board.numRows(); row++) {
      board.setRow(row, in.nextLineAsString());
    }
    long result = solveDp(board);

    if (board.getSize().size() <= 100) {
      long solveBf = solveBruteForce(board);
      if (solveBf != result) {
        throw new RuntimeException("Bad bruteforce = " + solveBf);
      }
    }
    out.write(result);
    out.flush();
  }
}
