package com.example;

/**
 * WIA/WIB1002 Data Structures
 * part of Graphs implementation using List
 *
 * Parameters:
 * T - type of data contained inside the vertex
 * N - type of data for the weight (float, integer, double, etc)
 */
class Edge<
    T extends Comparable<T>,
    N extends Comparable<N>
> {
    // kenapa connect macam ni?
    Vertex<T, N> toVertex;
    N weight;
    Edge<T, N> nextEdge;

    public Edge() {
        this.toVertex = null;
        this.weight = null;
        this.nextEdge = null;
    }

    public Edge(Vertex<T, N> destination, N weight, Edge<T, N> nextEdge) {
        this.toVertex = destination;
        this.weight = weight;
        this.nextEdge = nextEdge;
    }
}
