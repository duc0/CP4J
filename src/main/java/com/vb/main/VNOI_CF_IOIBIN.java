package com.vb.main;

import com.vb.datastructure.DisjointSet;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;

import java.io.IOException;

public class VNOI_CF_IOIBIN {
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        /*int q = in.nextLineAsInt();
        IntNDArray input = in.nextLinesAs2DIntArray(new NDShape(q, 3));
        int n = 0;
        for (int i = 0; i < q; i++) {
            n = Math.max(n, input.get(i, 0));
            n = Math.max(n, input.get(i, 1));
        }
        DisjointSet ds = new DisjointSet(n);
        for (int i = 0; i < q; i++) {
            int u = input.get(i, 0) - 1;
            int v = input.get(i, 1) - 1;
            if (input.get(i, 2) == 1) {
                ds.merge(u, v);
            } else {
                out.write(ds.inSameSet(u, v) ? 1 : 0);
                out.write("\n");
            }
        }*/


        int q = in.nextLineAsInt();
        DisjointSet ds = new DisjointSet(10000);
        for (int i = 0; i < q; i++) {
            int[] line = in.readTokensAsIntArray(3);
            int u = line[0] - 1;
            int v = line[1] - 1;
            int t = line[2];
            if (t == 1) {
                ds.merge(u, v);
            } else {
                out.write(ds.inSameSet(u, v) ? 1 : 0);
                out.write("\n");
            }
        }
        out.flush();
    }
}
