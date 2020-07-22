package com.vb.main;

import com.vb.nd.NDArray;

import java.io.PrintWriter;
import java.util.Scanner;

public class VNOI_CF_LIQ {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int len = in.nextInt();
        int[] inp = new int[len];
        for (int idx = 0; idx < len; idx++) {
            inp[idx] = in.nextInt();
        }
        NDArray<Long> f = NDArray.longArray(len);
        for (int idx = 0; idx < len; idx++) {
            f.setL(idx, 1);
            for (int prev = 0; prev < idx; prev++) {
                if (inp[prev] < inp[idx]) {
                    f.setIfMoreL(idx, f.getL(prev) + 1);
                }
            }
        }
        out.print(f.maxL());
    }
}
