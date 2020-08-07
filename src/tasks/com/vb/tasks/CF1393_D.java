/*
 * Copyright (c) CP4J Project
 */

package com.vb.tasks;

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

  private static final class Board {

    final int nRow;
    final int nCol;
    final String[] board;

    Board(int nRow, int nCol) {
      this.nRow = nRow;
      this.nCol = nCol;
      board = new String[nRow];
    }

    boolean inBoard(int row, int col) {
      return 0 <= row && row < nRow && 0 <= col && col < nCol;
    }

    char get(int row, int col) {
      return board[row].charAt(col);
    }
  }

  long solveBruteForce(Board board) {
    long result = 0;
    for (int row = 0; row < board.nRow; row++) {
      for (int col = 0; col < board.nCol; col++) {
        int len = 1;
        while (true) {
          result++;
          len++;
          if (!isOuterSquare(board, row, col, len)) {
            break;
          }
        }
      }
    }
    return result;
  }

  private boolean isOuterSquare(Board board, int row, int col, int len) {
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

  long solveDp(Board board) {
    int firstRow = 0;
    int firstCol = 0;
    IntArithmetic arithmetic = new DefaultIntArithmetic();
    IntNDArray longestDiag1 = new IntNDArray(arithmetic, board.nRow * board.nCol);
    longestDiag1.reshape(new NDShape(board.nRow, board.nCol));

    IntNDArray longestDiag2 = new IntNDArray(arithmetic, board.nRow * board.nCol);
    longestDiag2.reshape(new NDShape(board.nRow, board.nCol));

    IntNDArray longestSquare = new IntNDArray(arithmetic, board.nRow * board.nCol);
    longestSquare.reshape(new NDShape(board.nRow, board.nCol));

    long result = 0;
    while (true) {
      int curRow = firstRow;
      int curCol = firstCol;
      while (true) {
        if (!board.inBoard(curRow, curCol)) {
          break;
        }
        char ch = board.get(curRow, curCol);
        int prevRowDiag1 = curRow + 1;
        int prevColDiag1 = curCol - 1;
        boolean hasDiag1 = false;
        if (board.inBoard(prevRowDiag1, prevColDiag1) && board.get(prevRowDiag1, prevColDiag1) == ch) {
          hasDiag1 = true;
          longestDiag1.set(curRow, curCol, longestDiag1.get(prevRowDiag1, prevColDiag1) + 1);
        } else {
          longestDiag1.set(curRow, curCol, 1);
        }

        int prevRowDiag2 = curRow - 1;
        int prevColDiag2 = curCol - 1;
        boolean hasDiag2 = false;
        if (board.inBoard(prevRowDiag2, prevColDiag2) && board.get(prevRowDiag2, prevColDiag2) == ch) {
          hasDiag2 = true;
          longestDiag2.set(curRow, curCol, longestDiag2.get(prevRowDiag2, prevColDiag2) + 1);
        } else {
          longestDiag2.set(curRow, curCol, 1);
        }

        int prevRowSquare = curRow ;
        int prevColSquare = curCol - 1;
        if (board.inBoard(prevRowSquare, prevColSquare) &&
            board.get(prevRowSquare, prevColSquare) == ch &&
            hasDiag1 &&
            hasDiag2) {
          int candidate =
              MathUtils.min(
                  longestSquare.get(prevRowSquare, prevColSquare),
                  longestDiag1.get(curRow, curCol) - 1,
                  longestDiag2.get(curRow, curCol) - 1);
          if (candidate > 0 && board.inBoard(curRow, curCol - 2 * candidate) &&
              longestSquare.get(prevRowDiag1, prevColDiag1) >= candidate &&
              longestSquare.get(prevRowDiag2, prevColDiag2) >= candidate &&
              board.get(curRow, curCol - 2 * candidate) == ch) {
            candidate++;
          }
          longestSquare.set(curRow, curCol, candidate);
        } else {
          longestSquare.set(curRow, curCol, 1);
        }
        result += longestSquare.get(curRow, curCol);
        Log.d("Value {0} {1} {2}", curRow + 1, curCol + 1, longestSquare.get(curRow, curCol));

        curRow -= 1;
        curCol += 1;
      }

      if (firstRow + 1 < board.nRow) {
        firstRow++;
      } else if (firstCol + 1 < board.nCol) {
        firstCol++;
      } else {
        break;
      }
    }
    return result;
  }

  @Override
  public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
    //Log.setDebug(true);
    int[] header = in.readTokensAsIntArray();
    Board board = new Board(header[0], header[1]);
    for (int row = 0; row < board.nRow; row++) {
      board.board[row] = in.nextLineAsString();
    }
    long result = solveDp(board);
    /*
    long solveBf = solveBruteForce(board);
    if (solveBf != result) {
      throw new RuntimeException("Bad bruteforce = " + solveBf);
    }*/
    out.write(result);
    out.flush();
  }
}
