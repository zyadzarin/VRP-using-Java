package com.example;

import java.util.ArrayList;

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
class Vertex extends Location {
    boolean checked;
    public int ID;
    public boolean visited = false;
    public ArrayList<Edge> EdgeList;

    public Vertex() {}

    public Vertex(Double x, Double y, int demandSize, int ID) {
        super(x, y, demandSize);
        this.ID = ID;
        EdgeList = new ArrayList<>();
    }

    public Vertex visit() {
        visited = true;
        return this;
    }

    public Vertex unvisit() {
        visited = false;
        return this;
    }

    public Vertex check() {
        checked = true;
        return this;
    }

    public Vertex uncheck() {
        checked = false;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public int getID() {
        return ID;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "ID=" + ID +
                ", visited=" + visited +
                ", EdgeList=" + EdgeList +
                ", demand=" + demandSize +
                '}';
    }
}

