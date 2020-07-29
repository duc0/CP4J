/*
 * Copyright (c) CP4J Project
 */

package com.vb.graph;

public class Edge {
    private final int start;
    private final int end;

    public Edge(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
