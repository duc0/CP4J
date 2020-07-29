package com.vb.main;

import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.nd.IntNDArray;
import com.vb.nd.NDShape;
import com.vb.nd.NDSliceRanges;

import java.io.IOException;

public class VNOI_CF_QBMAX {
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        NDShape size = in.nextLineAsShape();
        IntNDArray matrix = in.nextLinesAs2DIntArray(size);

        for (int col = 1; col < size.dim(1); col++) {
            for (int row = 0; row < size.dim(0); row++) {
                int best =  matrix.get(row, col - 1);
                if (row > 0) {
                    best = Math.max(best, matrix.get(row - 1, col - 1));
                }
                if (row < size.dim(0) - 1) {
                    best = Math.max(best, matrix.get(row + 1, col - 1));
                }
                int newBest = best + matrix.get(row, col);
                matrix.set(row, col, newBest);
            }
        }

        out.write(matrix.max(NDSliceRanges.col2D(-1)));
    }
}
