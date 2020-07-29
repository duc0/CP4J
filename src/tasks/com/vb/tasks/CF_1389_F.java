/*
 * Copyright (c) CP4J Project
 */

package com.vb.tasks;

import com.vb.bit.SmallBitSet;
import com.vb.datastructure.SegmentArray;
import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.task.CPTaskSolver;

import java.io.IOException;
import java.util.Arrays;

// Powered by CP4J - https://github.com/duc0/CP4J
public class CF_1389_F implements CPTaskSolver {
    public static int solveBruteForce(int testNumber, SegmentArray segmentArray, int[] color) {
        int n = segmentArray.getSize();
        int fullSet = SmallBitSet.fullSet(n);
        int result = 0;
        for (int s = 0; s <= fullSet; s++) {
            int size = 0;
            boolean bad = false;
            outer: for (int i = 0; i < n; i++) {
                if (SmallBitSet.contains(s, i)) {
                    size++;
                    for (int j = i + 1; j < n; j++) {
                        if (SmallBitSet.contains(s, j)) {
                            if (segmentArray.intersect(i, j) && color[i] != color[j]) {
                                bad = true;
                                break outer;
                            }
                        }
                    }
                }
            }
            if (!bad) {
                result = Math.max(result, size);
            }
        }
        return result;
    }

    public static int solve(int testNumber, SegmentArray segmentArray, int[] color) {
        int n = segmentArray.getSize();
        int[] sorted = segmentArray.sortByLeft();
        int[] f = new int[n];
        for (int i = 0; i < n; i++) {
            f[i] = 1;
        }
        int result = 0;
        for (int i = 0; i < n; i++) {
            int curLeft = segmentArray.getLeft(sorted[i]);
            int curRight = segmentArray.getRight(sorted[i]);
            System.out.println(curLeft + ", " + curRight + ", " + color[sorted[i]]);
            for (int j = i + 1; j < n; j++) {
                if (color[sorted[j]] == color[sorted[i]]) {
                    if (segmentArray.getRight(sorted[j]) <= curRight) {
                        if (testNumber == 16 && i == 4) {
                            System.out.println("here");
                        }
                        f[i] += 1;
                    }
                }
            }
            for (int j = i + 1; j < n; j++) {
                if (color[sorted[j]] != color[sorted[i]]) {
                    if (segmentArray.getLeft(sorted[j]) > curRight) {
                        f[j] = Math.max(f[i] + 1, f[j]);
                    }
                } else {
                    if (segmentArray.getRight(sorted[j]) > curRight) {
                        f[j] = Math.max(f[i] + 1, f[j]);
                    }
                }
            }
            result = Math.max(result, f[i]);
            System.out.println(Arrays.toString(f));
        }
        return result;
    }

    @Override
    public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        int n = in.nextLineAsInt();
        SegmentArray segmentArray = new SegmentArray(n);
        int[] color = new int[n];
        for (int i = 0; i < n; i++) {
            int[] row = in.readTokensAsIntArray();
            int l = row[0];
            int r = row[1];
            color[i] = row[2];
            segmentArray.setSegment(i, l, r);
        }
        out.write(solve(testNumber, segmentArray, color));
        out.flush();
    }
}
