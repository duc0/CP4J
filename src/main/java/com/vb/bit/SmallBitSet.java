/*
 * Copyright (c) CP4J Project
 */

package com.vb.bit;

import java.util.BitSet;

public class SmallBitSet {
    public static int fullSet(int n) {
        return (1 << n) - 1;
    }

    public static boolean contains(int set, int i) {
        return BitUtils.testBit(set, i);
    }

    public static int union(int set, int i) {
        return BitUtils.setBit(set, i);
    }

    public static int singleElement(int i) {
        return 1 << i;
    }
}
