package com.vb.tasks;

import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.nd.IntNDArray;
import com.vb.nd.NDShape;
import com.vb.number.DefaultIntArithmetic;
import com.vb.task.CPTaskSolver;

import java.io.IOException;

public class VNOI_CF_NKPALIN implements CPTaskSolver {

    String trace(String s, IntNDArray f, int l, int left) {
        if (l <= 0) {
            return "";
        }
        if (l == 1) {
            return String.valueOf(s.charAt(left));
        }
        int right = l + left - 1;
        if (s.charAt(left) == s.charAt(right)) {
            return s.charAt(left) + trace(s, f, l - 2, left + 1) + s.charAt(right);
        } else if (f.get(l, left) == f.get(l - 1, left)) {
            return trace(s, f, l - 1, left);
        } else {
            return trace(s, f, l - 1, left + 1);
        }
    }

    @Override
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        String s = in.nextLineAsString();
        int n = s.length();
        IntNDArray f = new IntNDArray(new DefaultIntArithmetic(), (n + 1) * n);
        f.reshape(new NDShape(n + 1, n));
        for (int l = 1; l <= n; l++) {
            for (int left = 0; left + l - 1 < n; left++) {
                int right = left + l - 1;
                int value;
                if (left == right) {
                    value = 1;
                } else {
                    if (s.charAt(left) == s.charAt(right)) {
                        if (l == 1) {
                            value = 2;
                        } else {
                            value = f.get(l - 2, left + 1) + 2;
                        }
                    } else {
                        value = Math.max(f.get(l - 1, left), f.get(l - 1, left + 1));
                    }
                }
                f.set(l, left, value);
            }
        }
        String result = trace(s, f, n, 0);
        out.write(result);
        out.flush();
    }
}
