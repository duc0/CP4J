package com.vb.number;

public interface Arithmetic {
    int compare(GenericNumber n1, GenericNumber n2);

    GenericNumber add(GenericNumber n1, GenericNumber n2);

    GenericNumber zero();

    GenericNumber maxValue();

    GenericNumber min(GenericNumber n1, GenericNumber n2);

    default boolean isEqual(GenericNumber n1, GenericNumber n2) {
        return compare(n1, n2) == 0;
    }
}

