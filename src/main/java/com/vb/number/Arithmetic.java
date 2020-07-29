package com.vb.number;

public interface Arithmetic {
    int compare(NumberGeneric n1, NumberGeneric n2);

    NumberGeneric add(NumberGeneric n1, NumberGeneric n2);

    NumberGeneric zero();

    NumberGeneric maxValue();

    NumberGeneric min(NumberGeneric n1, NumberGeneric n2);

    default boolean isEqual(NumberGeneric n1, NumberGeneric n2) {
        return compare(n1, n2) == 0;
    }
}

