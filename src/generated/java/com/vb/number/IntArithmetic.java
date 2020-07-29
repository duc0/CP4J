package com.vb.number;

public interface IntArithmetic {
    int compare(int n1, int n2);

    int add(int n1, int n2);

    int subtract(int n1, int n2);

    int multiply(int n1, int n2);

    int divide(int n1, int n2);

    int zero();

    int maxValue();

    int min(int n1, int n2);

    default boolean isEqual(int n1, int n2) {
        return compare(n1, n2) == 0;
    }
}

