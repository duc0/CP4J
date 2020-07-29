/*
 * Copyright (c) CP4J Project
 */

package com.vb.number;

public interface DoubleArithmetic {
    int compare(double n1, double n2);

    double add(double n1, double n2);

    double subtract(double n1, double n2);

    double multiply(double n1, double n2);

    double divide(double n1, double n2);

    double zero();

    double maxValue();

    double min(double n1, double n2);

    default boolean isEqual(double n1, double n2) {
        return compare(n1, n2) == 0;
    }
}

