package com.vb.io;

import com.vb.nd.NDArray;

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

    public NDArray<Integer> nextLineAsIntArray() throws IOException {
        String line = bufferedReader.readLine();
        String[] lineS = line.split(" ");
        NDArray<Integer> result = new NDArray<>(lineS.length, Integer.class);
        for (int i = 0; i < lineS.length; i++) {
            result.setI(i, Integer.parseInt(lineS[i]));
        }
        return result;
    }
}
