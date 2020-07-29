/*
 * Copyright (c) CP4J Project
 */

package com.vb.io;

import java.io.*;

public class FastWriter {
    private final BufferedWriter bufferedWriter;

    public FastWriter(OutputStream outputStream) {
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public FastWriter(Writer writer) {
        bufferedWriter = new BufferedWriter(writer);
    }

    public void close() throws IOException {
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void write(String token) throws IOException {
        bufferedWriter.write(token);
        //bufferedWriter.flush();
    }

    public void write(int token) throws IOException {
        bufferedWriter.write(String.valueOf(token));
        //bufferedWriter.flush();
    }

    public void write(long token) throws IOException {
        bufferedWriter.write(String.valueOf(token));
        //bufferedWriter.flush();
    }

    public void flush() throws IOException {
        bufferedWriter.flush();
    }
}
