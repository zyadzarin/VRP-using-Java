package com.example;

import java.util.ArrayList;
import java.util.Arrays;

public class GreedySearch {
    private static WeightedGraph G;
    private static int C;
    private static double tourCost=0;

    public GreedySearch() {
    }

    public static void run(WeightedGraph G, int C) {
        GreedySearch.G = G;
        GreedySearch.C = C;
        System.out.println("---Best-Path Search---\n");
        long start = System.nanoTime();
        String result = search();
        long end = System.nanoTime();

        System.out.println("Total tour cost: " + tourCost);
        System.out.println(result);
        System.out.println("Execution time: " + (double) (end - start) * Math.pow(10, -6) + "ms\n");
    }

    private static String search() {

        double[] cost = new double[G.size()];


        StringBuilder outString = new StringBuilder();
        G.unvisitAll();

        while(!G.isAllVisited()) { //everytime this loop is iterated, a new vehicle is sent
            double distanceTravelled = 0;
            Arrays.fill(cost, Double.POSITIVE_INFINITY);    //
            int tempC = C;
            G.getHead().unvisit(); //unvisit depot
            Vertex currentVertex = G.getHead();
            Vertex nextVertex = G.getHead();


            outString.append(currentVertex.getID());
            for(int i=0; i < G.size(); i++) { //traverse through each vertex(customer)

                for(Edge currentEdge : currentVertex.EdgeList) { //traverse through each edge of each vertex

                    if(tempC >= currentEdge.toVertex.demandSize && currentEdge.distance < cost[i] && !currentEdge.toVertex.isVisited()) {


                        nextVertex = currentEdge.toVertex;
                        cost[i] = currentEdge.distance;
                    }

                }
                nextVertex.visit(); //visited
                outString.append(" --> ").append(nextVertex.getID());

                distanceTravelled += cost[i];
                tempC -= nextVertex.demandSize;
                currentVertex = nextVertex;

                if (currentVertex.ID == 0)
                    //if the vehicle returns to the depot, break the loop/go to the next vehicle

                    break;
            }

            outString.append("\nCapacity: ").append(C - tempC);
            outString.append("\nCost: ").append(distanceTravelled).append("\n");
            tourCost += distanceTravelled;
        }
        return outString.toString();
    }
}
