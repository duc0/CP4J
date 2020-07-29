# CP4J

## Introduction

CP4J is a high performant algorithms and data structures Java library designed for programming contests.

## Overall Design

* All algorithms and data structures are designed to work on primitive number types (int, long, double). Code duplication is 
avoided through a lightweight code generation process.
* Interface is designed based on the problem rather than specific algorithms/data structures.
* Highly modularized and low coupling.
* Can be used with IntelliJ's CHelper plugin to inline library code to submit solution as a single file.
* Focus on developing tools and utils to assist with finding a solution.

## Supported data structures and algorithms
Data Structures
* NDArray
* Graph (undirected/directed)
* Weighted Graph
* Fenwick Tree
* Disjoint Set

Algorithms
* Finding Connected Components
* Binary Search
* Minimum Spanning Tree
* Finding Hamiltonian Path O(2^N)

## To be supported
Data Structures
* Segment Tree
* Geometric Data Structures (Point/Segment/Polygon/...)
* Suffix Arrays
* Suffix Automaton
* Binary Search Tree
* ...

Algorithms
* Matrix Multiplication
* Network Flow
* Minimum Cost Network Flow
* Convex Hull
* Finding Eulerian Path
* Bipartite Matching
* ...

