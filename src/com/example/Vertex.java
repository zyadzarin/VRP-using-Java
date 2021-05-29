package com.example;

/**
 * WIA/WIB1002 Data Structures
 * part of Graphs implementation using List
 *
 * This is a linked-list implementation of the Vertex object
 *
 * Parameters:
 * T - type of data contained inside the vertex
 * N - type of data for the weight (float, integer, double, etc)
 */
class Vertex<
    T extends Comparable<T>,
    N extends Comparable<N>
> {
    T vertexInfo;
    int indeg;
    int outdeg;
    Vertex<T, N> nextVertex;
    Edge<T, N> firstEdge;

    public Vertex() {
        this.vertexInfo = null;
        this.indeg = 0;
        this.outdeg = 0;
        this.nextVertex = null;
        this.firstEdge = null;
    }

    public Vertex(T vertexInfo, Vertex<T, N> next) {
        this.vertexInfo = vertexInfo;
        this.indeg = 0;
        this.outdeg = 0;
        this.nextVertex = next;
        this.firstEdge = null;
    }
}

