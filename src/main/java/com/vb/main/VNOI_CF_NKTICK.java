package com.vb.main;

import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.nd.NDArray;

import java.io.IOException;

public class VNOI_CF_NKTICK {
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        int len = in.nextLineAsInt();
        NDArray<Integer> timeTaken = in.nextLineAsIntArray();
        NDArray<Integer> timeCostForTwo = in.nextLineAsIntArray();
        NDArray<Integer> f = NDArray.intArray(len);
        f.setI(0, timeTaken.getI(0));
        for (int i = 1; i < len; i++) {
            f.setI(i, Math.min(
                    f.getI(i - 1) + timeTaken.getI(i),
                    (i >= 2 ? f.getI(i - 2) : 0) + timeCostForTwo.getI(i - 1)));
        }
        out.write(f.getI(len - 1));
    }
}
