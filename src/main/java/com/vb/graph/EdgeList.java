package com.vb.graph;

public class EdgeList {
    private final int[] start;
    private final int[] end;

    public EdgeList(int[] start, int[] end) {
        this.start = start;
        this.end = end;
    }

    public int getStart(int i) {
        return start[i];
    }

    public int getEnd(int i) {
        return end[i];
    }
}
