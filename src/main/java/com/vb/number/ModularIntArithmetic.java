/*
 * Copyright (c) CP4J Project
 */

package com.vb.number;

public class ModularIntArithmetic implements IntArithmetic {
    private final int m;

    public ModularIntArithmetic(int m) {
        this.m = m;
    }

    @Override
    public int compare(int n1, int n2) {
        return Integer.compare(n1, n2);
    }

    @Override
    public int add(int n1, int n2) {
        return (n1 + n2) % m;
    }

    @Override
    public int subtract(int n1, int n2) {
        return (n1 - n2) % m;
    }

    @Override
    public int multiply(int n1, int n2) {
        return (n1 * n2) % m;
    }

    @Override
    public int divide(int n1, int n2) {
        return (n1 / n2) % m;
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
