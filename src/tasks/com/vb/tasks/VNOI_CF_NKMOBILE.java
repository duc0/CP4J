/*
 * Copyright (c) CP4J Project
 */

package com.vb.tasks;

import com.vb.datastructure.IntCumulativeTable2D;
import com.vb.datastructure.IntFenwickTree2D;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.number.DefaultIntArithmetic;
import com.vb.task.CPTaskSolver;

import java.io.IOException;

public class VNOI_CF_NKMOBILE implements CPTaskSolver {
    @Override
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        IntCumulativeTable2D table = null;
        while (true) {
            int[] row = in.readTokensAsIntArray();
            int command = row[0];
            if (command == 3) {
                break;
            }
            if (command == 0) {
                int n = row[1];
                table = new IntFenwickTree2D(new DefaultIntArithmetic(), n);
            } else if (command == 1) {
                table.add(row[1], row[2] , row[3]);
            } else if (command == 2) {
                out.write(table.sum(row[1] , row[2] , row[3] , row[4] ));
                out.write("\n");
            }
        }
        out.flush();
    }
}
