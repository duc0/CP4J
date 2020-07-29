/*
 * Copyright (c) CP4J Project
 */

package main;

import com.vb.datastructure.SegmentArray;
import com.vb.tasks.CF_1389_F;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class CF1398FTest {
    @Test
    public void test() {
        Random random = new Random(42);
        for (int test = 0; test < 30; test++) {
            int n = random.nextInt( 8)+ 1;
            SegmentArray segmentArray = new SegmentArray(n);
            int[] color = new int[n];
            for (int i = 0; i < n; i++) {
                int left = random.nextInt(10)+ 1;
                int len = random.nextInt(5) + 1;
                int c = random.nextInt(2) + 1;
                segmentArray.setSegment(i, left, left + len - 1);
                color[i] = c;
            }
            System.out.println("Test: " + test + " array=" + segmentArray + ", color=" + Arrays.toString(color));
            assertEquals(CF_1389_F.solve(test, segmentArray, color), CF_1389_F.solveBruteForce(test, segmentArray, color));
        }
    }
}
