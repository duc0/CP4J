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


## Examples
Dynamic Programming to find a path with maximum sum from left column to right column of a matrix.

```java
        NDShape size = in.nextLineAsShape();
        IntNDArray matrix = in.nextLinesAs2DIntArray(size);

        for (int col = 1; col < size.dim(1); col++) {
            for (int row = 0; row < size.dim(0); row++) {
                int best =  matrix.get(row, col - 1);
                if (row > 0) {
                    best = Math.max(best, matrix.get(row - 1, col - 1));
                }
                if (row < size.dim(0) - 1) {
                    best = Math.max(best, matrix.get(row + 1, col - 1));
                }
                int newBest = best + matrix.get(row, col);
                matrix.set(row, col, newBest);
            }
        }

        out.write(matrix.max(NDSliceRanges.col2D(-1)));
```
     
2D Fenwick Tree
```java
        IntCumulativeTable2D table = null;
        while (true) {
            int[] row = in.readTokensAsIntArray();
            int command = row[0];
            if (command == 3) {
                break;
            }
            if (command == 0) {
                int n = row[1];
                table = new IntFenwickTree2D(new DefaultIntArithmetic(), n);
            } else if (command == 1) {
                table.add(row[1], row[2] , row[3]);
            } else if (command == 2) {
                out.write(table.sum(row[1] , row[2] , row[3] , row[4]));
                out.write("\n");
            }
        }        
```

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

## License
[MIT License](https://opensource.org/licenses/MIT)

## Installation
You can clone this repository and use it in programming contests. It should work well with IntelliJ+CHelper plugin.

If you want to use the library directly in your project, you can install it from Github Packages MVN Repo by adding the dependency into your project's Maven pom.xml. You'll also need to config the repo and Github authentication according
to [instructions](https://docs.github.com/pt/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages).


    <dependencies>
        <dependency>
            <groupId>com.vb.cp4j</groupId>
            <artifactId>cp4j</artifactId>
            <version>0.1</version>
        </dependency>
    </dependencies>