package com.example;

import java.util.ArrayList;

class WeightedGraph {
    public ArrayList<Vertex> vertexArrayList; //holds vertices arraylist

    public WeightedGraph() {
        vertexArrayList = new ArrayList<>();
    }

    public int size() {
        return vertexArrayList.size();
    }

    public void addVertex(Vertex vertex) {
        vertexArrayList.add(vertex);
    }

    public Vertex getHead() {
        return vertexArrayList.get(0);
    }

    public Vertex getVertex(int idx) {
        if(idx < 0 || idx >= size())
            return null;

        return vertexArrayList.get(idx);
    }

    public Vertex getLast() {
        return vertexArrayList.get(vertexArrayList.size() - 1);
    }

    public void unvisitAll(){
        for(Vertex V: vertexArrayList){
            V.unvisit();
        }
    }

    public void uncheckAll() {
        for(Vertex V: vertexArrayList) {
            V.uncheck();
        }
    }

    public boolean isAllVisited() {
        boolean out = true;
        for(Vertex V: vertexArrayList) {
            out = V.isVisited();
            if (!out)
                return false;
        }
        return out;
    }

    public boolean isAllVisitedExceptDepot() {
        boolean out = true;

        for(int i=0; i < vertexArrayList.size(); i++) {
            out = vertexArrayList.get(i).isVisited();
            if( vertexArrayList.get(i).getID() != 0 && !out)
                return false;
        }
        return out;
    }

    public void completeGraph() {
        //to make a complete graph
        for(int i=0; i <vertexArrayList.size(); i++) {
            Vertex sourceVertex = vertexArrayList.get(i);
            for(Vertex current : vertexArrayList) {
                double distance = sourceVertex.getCoordinate().distanceTo(
                        current.getCoordinate());

                if(sourceVertex.ID != current.ID) {
                    Edge newEdge = new Edge(current, distance);
                    sourceVertex.EdgeList.add(newEdge);
                }

            }
        }
    }

    public void printEdges() {
        System.out.println(vertexArrayList.get(0));
        for (Vertex current : vertexArrayList) {
            System.out.println("Vertex ID" + current.ID + " " + current.EdgeList.toString());
        }
    }


}
