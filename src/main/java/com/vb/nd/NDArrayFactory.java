package com.vb.nd;

public interface NDArrayFactory<T extends Number> {
    NDArray<T> create(int capacity);
}
