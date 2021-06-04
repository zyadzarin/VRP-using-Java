package com.example;

/**
 * WIA/WIB1002 Data Structures
 * part of Graphs implementation using List
 *
 * Parameters:
 * T - type of data contained inside the vertex
 * N - type of data for the weight (float, integer, double, etc)
 */
public class Edge implements Comparable<Edge>{
    public Vertex toVertex;
    public double distance;

    public Edge() {}

    public Edge(Vertex toVertex, double distance) {
        this.toVertex = toVertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(Edge e) {
        return Double.compare(this.distance, e.distance);
    }
}
