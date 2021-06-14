package com.example;

import java.util.Arrays;

public class BreadthSearch {

    /**Breadth First Seach
     * finding the simplest route to reach all placess in unweighted graph
     */

    private static WeightedGraph graph;
    private static int capacity;
    private static double tourCost = 0;
    //private static LinkedList<WeightedGraph> path[]; //= new LinkedList<WeightedGraph>[vertex.vertexArrayList.size()];

    public BreadthSearch(){
    }

//    public static void work(WeightedGraph stopNum, int capacity) {
//        double startTime = System.nanoTime();
//        BreadthSearch.graph = stopNum;
//        BreadthSearch.capacity = capacity;
//
//        String result;
//
//        try {
//            result = deliver();
//        }
//        catch (OutOfMemoryError e) {
//            result = "OUT OF MEMORY";
//        }
//
//        double finishTime = System.nanoTime();
//
//        System.out.println(result);
//        System.out.println("Execution time: " + (finishTime - startTime) * Math.pow(10, -6) + "ms\n");
//    }
//
//    private static String deliver() {
//        /*
//        for (int i = 0; i < path.length; i++) {
//            path[i] = new LinkedList<WeightedGraph>();
//        }
//        */
//        graph.unvisitAll();
//        double[] cost = new double[graph.size() + 1];
//        double distanceTravelled = 0;
//
//        StringBuilder route = new StringBuilder();
//        Vertex depot = graph.getVertex(0);
//        Vertex customerStop;
//        //route.append(depot.getID());
//        //vertex.vertexArrayList.size()
//        while (!graph.isAllVisited()) {
//            int numVehicle = 1;
//            int customerID = 1;
//
//            route.append("Vehicle " + numVehicle);
//            route.append("\n" + depot.getID()); //the depot
//            customerStop = graph.getVertex(customerID); //to i customer
//            route.append(" --> ").append(customerStop.getID()); //it will print Depot --> 1
//            cost[customerID] = depot.getCoordinate().distanceTo(customerStop.getCoordinate());
//            route.append(customerStop.getDemandSize());
//            route.append("\n Path Cost: " + cost[customerID]);
//            distanceTravelled += cost[customerID];
//
//            if (customerStop.demandSize > capacity) {
//                customerStop.visit(); //checked as done
//                customerID++;
//            } else {
//                customerStop.unvisit(); //need two vehicle to comes
//                route.append(customerStop.getDemandSize() - capacity);
//            }
//
//            numVehicle++;
//        }
//        route.append("\nTotal distance travelled = " + distanceTravelled);
//        return route.toString();
//    }

    public static void run(WeightedGraph G, int C) {
        BreadthSearch.graph = G;
        BreadthSearch.capacity = C;

        System.out.println("---Best-Path Search---\n");

        long start = System.nanoTime();
        String result = search();
        long end = System.nanoTime();

        System.out.println("Total tour cost: " + tourCost);
        System.out.println(result);
        System.out.println("Execution time: " + (double) (end - start) * Math.pow(10, -6) + "ms\n");
    }

    private static String search() {
        double[] cost = new double[graph.size()];

        StringBuilder outString = new StringBuilder();
        graph.unvisitAll();

        while (!graph.isAllVisited()) { //everytime this loop is iterated, a new vehicle is sent
            double distanceTravelled = 0;

            int tempC = capacity;
            graph.getHead().unvisit(); //unvisit depot

            Vertex currentVertex = graph.getHead();
            Vertex nextVertex = graph.getHead();

            outString.append(currentVertex.getID());

            for (int i = 0; i < graph.size(); i++) { //traverse through each vertex(customer)

                for (Edge currentEdge : currentVertex.EdgeList) { //traverse through each edge of each vertex

                    if (tempC >= currentEdge.toVertex.demandSize

                            && !currentEdge.toVertex.isVisited()
                    ) {
                        nextVertex = currentEdge.toVertex;
                        cost[i] = currentEdge.distance;
                    }

                }

                nextVertex.visit(); //visited
                outString.append(" --> ").append(nextVertex.getID());

                distanceTravelled += cost[i];
                tempC -= nextVertex.demandSize;
                currentVertex = nextVertex;

                //if the vehicle returns to the depot, break the loop/go to the next vehicle
                if (currentVertex.ID == 0) {
                    break;
                }
            }

            outString.append("\nCapacity: ").append(capacity - tempC);
            outString.append("\nCost: ").append(distanceTravelled).append("\n");

            tourCost += distanceTravelled;
        }

        return outString.toString();
    }
}
