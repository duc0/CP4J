/*
 * Copyright (c) CP4J Project
 */

package com.vb.tasks;

import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.logging.Log;
import com.vb.task.CPTaskSolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Powered by CP4J - https://github.com/duc0/CP4J
public class CF_1388_D implements CPTaskSolver {
    private long solve(int[] a, int[] next, int[] prev, long[] f, int i, List<Integer> operation) {
        if (f[i] != -1) {
            return f[i];
        }
        long result;
        if (next[i] == -1) {
            operation.add(i);
            result = f[i] = a[i];
        } else {
            int pos = i;
            long sum = a[pos];
            result = sum;
            while (sum >= 0) {
                pos = next[pos];
                if (pos == -1) {
                    break;
                }
                sum += a[pos];
                result += sum;
            }
            if (pos != -1) {
                pos = next[pos];
            }
            if (pos != -1) {
                solve(a, next, prev, f, pos, operation);
                result += f[pos];
            }
            int cur = i;
            while (cur != pos) {
                operation.add(cur);
                cur = next[cur];
            }
        }
        Log.d("Solving i = {0} result = {1}", i + 1, result);
        f[i] = result;
        return result;
    }

    private long verify(int[] a, int[] next, List<Integer> operation) {
        int n = a.length;
        long result = 0;
        int[] a2 = a.clone();
        for (int i = 0; i < n; i++) {
            int pos = operation.get(i);
            result += a2[pos];
            if (next[pos] != -1) {
                a2[next[pos]] += a2[pos];
            }
        }
        return result;
    }

    @Override
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        Log.setDebug(true);
        int n = in.nextLineAsInt();
        int[] a = in.readTokensAsIntArray();
        int[] next = in.readTokensAsIntArray();
        int[] prev = new int[n];
        long[] f =  new long[n];
        for (int i = 0; i < n; i++) {
            prev[i] = -1;
            f[i] = -1;
        }
        for (int i = 0; i < n; i++) {
            if (next[i] != -1) {
                next[i]--;
                prev[next[i]] = i;
            }
        }
        long ans = 0;
        List<Integer> operation = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            if (prev[i] == -1) {
                ans += solve(a, next, prev, f, i, operation);
            }
        }
        //Log.d("{0}", verify(a, next, operation));
        //assert(verify(a, next, operation) == ans);
        out.write(ans);
        out.write("\n");
        for (int i = 0; i < n; i++) {
            out.write(operation.get(i) + 1);
            out.write(" ");
        }
        out.flush();
    }
}
