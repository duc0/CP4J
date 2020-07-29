package com.vb.number;

public class DefaultIntArithmetic implements com.vb.number.IntArithmetic {
    @Override
    public int compare(int n1, int n2) {
        return Integer.compare(n1, n2);
    }

    @Override
    public int add(int n1, int n2) {
        return n1 + n2;
    }

    @Override
    public int zero() {
        return 0;
    }

    @Override
    public int maxValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int min(int n1, int n2) {
        return Math.min(n1, n2);
    }
}
