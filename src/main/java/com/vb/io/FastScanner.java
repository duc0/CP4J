package com.vb.io;

import com.vb.nd.NDArray;
import com.vb.nd.NDShape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FastScanner {
    private final BufferedReader bufferedReader;

    public FastScanner(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public int nextLineAsInt() throws IOException {
        return Integer.parseInt(bufferedReader.readLine().trim());
    }

    private String[] readTokens() throws IOException {
        return bufferedReader.readLine().trim().split("\\s+");
    }

    public NDShape nextLineAsShape() throws IOException {
        String[] tokens = readTokens();
        int[] dims = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            dims[i] = Integer.parseInt(tokens[i]);
        }
        return new NDShape(dims);
    }

    public NDArray<Integer> nextLineAsIntArray() throws IOException {
        String[] tokens = readTokens();
        NDArray<Integer> result = NDArray.intArray(tokens.length);
        for (int i = 0; i < tokens.length; i++) {
            result.setI(i, Integer.parseInt(tokens[i]));
        }
        return result;
    }

    public NDArray<Integer> nextLinesAs2DIntArray(NDShape shape) throws IOException {
        assert(shape.getDimension() == 2);
        NDArray<Integer> result = NDArray.intArray((int) shape.size());
        result.reshape(shape);
        for (int row = 0; row < shape.dim(0); row++) {
            String[] tokens = readTokens();
            for (int col = 0; col < tokens.length; col++) {
                result.setI(row, col, Integer.parseInt(tokens[col]));
            }
        }
        return result;
    }
}
