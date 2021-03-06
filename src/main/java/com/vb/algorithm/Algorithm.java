/*
 * Copyright (c) CP4J Project
 */

package com.vb.algorithm;

public interface Algorithm<I, O> {
    long getComplexity(I input);

    O run(I input);
}
