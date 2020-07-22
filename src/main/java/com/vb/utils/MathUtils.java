package com.vb.utils;

public class MathUtils {
    public static int max(int... values) {
        int result = values[0];
        for (int x : values) {
            if (x > result) {
                result = x;
            }
        }
        return result;
    }
}
