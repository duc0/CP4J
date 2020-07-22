package com.vb.main;

import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.nd.IntNDArray;

import java.io.IOException;

public class VNOI_CF_NKTICK {
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        int len = in.nextLineAsInt();
        IntNDArray timeTaken = in.nextLineAsIntArray();
        IntNDArray timeCostForTwo = in.nextLineAsIntArray();
        IntNDArray f = new IntNDArray(len);
        f.set(0, timeTaken.get(0));
        for (int i = 1; i < len; i++) {
            f.set(i, Math.min(
                    f.get(i - 1) + timeTaken.get(i),
                    (i >= 2 ? f.get(i - 2) : 0) + timeCostForTwo.get(i - 1)));
        }
        out.write(f.get(len - 1));
    }
}
