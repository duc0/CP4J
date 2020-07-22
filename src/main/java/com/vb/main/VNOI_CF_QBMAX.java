package com.vb.main;

import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.nd.NDArray;
import com.vb.nd.NDShape;
import com.vb.utils.MathUtils;

import java.io.IOException;
import java.util.Collections;

public class VNOI_CF_QBMAX {
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        NDShape size = in.nextLineAsShape();
        NDArray<Integer> matrix = in.nextLinesAs2DIntArray(size);

        int result = Integer.MIN_VALUE;
        for (int col = 1; col < size.dim(1); col++) {
            for (int row = 0; row < size.dim(0); row++) {
                int best =  matrix.getI(row, col - 1);
                if (row > 0) {
                    best = Math.max(best, matrix.getI(row - 1, col - 1));
                }
                if (row < size.dim(0) - 1) {
                    best = Math.max(best, matrix.getI(row + 1, col - 1));
                }
                int newBest = best + matrix.getI(row, col);
                matrix.setI(row, col, newBest);

                if (col == size.dim(1) - 1) {
                    result = Math.max(result, matrix.getI(row, col));
                }
            }
        }

        out.write(result);
    }
}
