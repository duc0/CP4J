/*
 * Copyright (c) CP4J Project
 */

package com.vb.search;

import java.util.function.Predicate;

public class BinarySearch {
    public static Long findMinSatisfy(long min, long max, Predicate<Long> predicate) {
        long left = min;
        long right = max;
        Long result = null;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (predicate.test(mid)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }
}
