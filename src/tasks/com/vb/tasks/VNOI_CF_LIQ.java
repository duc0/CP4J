package com.vb.tasks;

import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.nd.IntNDArray;
import com.vb.number.DefaultIntArithmetic;
import com.vb.task.CPTaskSolver;

import java.io.IOException;

public class VNOI_CF_LIQ implements CPTaskSolver {
    @Override
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        int len = in.nextLineAsInt();
        int[] inp = in.readTokensAsIntArray();
        IntNDArray f = new IntNDArray(new DefaultIntArithmetic(), len);
        for (int idx = 0; idx < len; idx++) {
            f.set(idx, 1);
            for (int prev = 0; prev < idx; prev++) {
                if (inp[prev] < inp[idx]) {
                    f.setIfMore(idx, f.get(prev) + 1);
                }
            }
        }
        out.write(f.max());
        out.flush();
    }
}
