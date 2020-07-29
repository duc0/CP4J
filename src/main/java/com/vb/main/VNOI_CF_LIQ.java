package com.vb.main;

import com.vb.nd.IntNDArray;
import com.vb.number.DefaultIntArithmetic;

import java.io.PrintWriter;
import java.util.Scanner;

public class VNOI_CF_LIQ {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int len = in.nextInt();
        int[] inp = new int[len];
        for (int idx = 0; idx < len; idx++) {
            inp[idx] = in.nextInt();
        }
        IntNDArray f = new IntNDArray(new DefaultIntArithmetic(), len);
        for (int idx = 0; idx < len; idx++) {
            f.set(idx, 1);
            for (int prev = 0; prev < idx; prev++) {
                if (inp[prev] < inp[idx]) {
                    f.setIfMore(idx, f.get(prev) + 1);
                }
            }
        }
        out.print(f.max());
        out.flush();
    }
}
