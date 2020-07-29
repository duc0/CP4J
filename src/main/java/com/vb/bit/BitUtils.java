/*
 * Copyright (c) CP4J Project
 */

package com.vb.bit;

public final class BitUtils {
    public static boolean testBit(int value, int i) {
        return (value & (1 << i)) != 0;
    }

    public static int setBit(int value, int i) {
        return value | (1 << i);
    }
}
