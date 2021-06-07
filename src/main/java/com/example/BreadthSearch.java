package com.example;

public class BreadthSearch {

    /**Breadth First Seach
     * finding the simplest route to reach all placess in unweighted graph
     */

    private static WeightedGraph vertex;
    private static int capacity;
    //private static double travelCost;
    //private static LinkedList<WeightedGraph> path[]; //= new LinkedList<WeightedGraph>[vertex.vertexArrayList.size()];
    
    public BreadthSearch(){
    }
    
    public static void work(WeightedGraph stopNum, int capacity) {
        double startTime = System.nanoTime();
        BreadthSearch.vertex = stopNum;
        BreadthSearch.capacity = capacity;
        
        System.out.println("Basic Simulation\n");
        
        String result = deliver();
        double finishTime = System.nanoTime();
        System.out.println(result);
        System.out.println("Execution time: " + (finishTime - startTime) * Math.pow(10, -6) + "ms\n");
        
    }
    
    private static String deliver() {
        /*
        for (int i = 0; i < path.length; i++) {
            path[i] = new LinkedList<WeightedGraph>();
        }
        */
        vertex.unvisitAll();
        double[] cost = new double[vertex.size() + 1];
        double distanceTravelled = 0;

        StringBuilder route = new StringBuilder();
        Vertex depot = vertex.getVertex(0);
        Vertex customerStop;
        //route.append(depot.getID());
        //vertex.vertexArrayList.size()
        while (!vertex.isAllVisited()) {
            int numVehicle = 1;
            int customerID = 1;

            route.append("Vehicle " + numVehicle);
            route.append("\n" + depot.getID()); //the depot
            customerStop = vertex.getVertex(customerID); //to i customer
            route.append(" --> ").append(customerStop.getID()); //it will print Depot --> 1
            cost[customerID] = depot.getCoordinate().distanceTo(customerStop.getCoordinate());
            route.append(customerStop.getDemandSize());
            route.append("\n Path Cost: " + cost[customerID]);
            distanceTravelled += cost[customerID];
            if (customerStop.demandSize > capacity) {
                customerStop.visit(); //checked as done
                customerID++;
            } else {
                customerStop.unvisit(); //need two vehicle to comes
                route.append(customerStop.getDemandSize() - capacity);
            }
            numVehicle++;
        }
        route.append("\nTotal distanca travelled = " + distanceTravelled);
        return route.toString();
    }
}
