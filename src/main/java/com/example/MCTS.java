package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MCTS {
    private static WeightedGraph G;
    private static int C,N;
    private static double tourCost = 0;


    private static double alpha;

    private static double[][] globalPolicy;
    private static double[][][] policy;
    private static Tour bestTour;


    public MCTS() {
    }


    public static void run(WeightedGraph G, int N, int C, int level, int iterations, double alpha) {
        StringBuilder outString = new StringBuilder();
        MCTS.G = G;
        MCTS.N = N;
        MCTS.C = C;
        MCTS.alpha = alpha;

        int routeCapacity;
        double totalCost = 0;
        bestTour = new Tour();


        policy = new double[level][N][N];
        globalPolicy = new double[N][N];

        for(double[] row : globalPolicy) {
            Arrays.fill(row, 0);
        }

        for (double[][] tubes : policy) {
            Arrays.stream(tubes).forEach(row -> Arrays.fill(row, 0));
        }

        outString.append("---MCTS Search---\n");
        long start = System.nanoTime();
        Tour tour = search(level, iterations);

        int deeznuts =0;
        for(ArrayList<Vertex> route: tour.getListOfRoutes()) {
            routeCapacity=0;

            for(Vertex vertex : route) {
                outString.append(vertex.getID() + " --> ");
                routeCapacity += vertex.getDemandSize();
            }

            int indexStr = outString.lastIndexOf("-->");
            outString.delete(indexStr, outString.length()-1);

            totalCost += tour.distanceOfRoute(route);
            outString.append("\n" +routeCapacity);
            outString.append("\nCost: " + tour.distanceOfRoute(route) + "\n");

        }

        long end = System.nanoTime();
        outString.append("Tour Cost: " + totalCost);
        outString.append("\nExecution time: " + (double) (end - start)  + "s\n");
        System.out.println(outString);


    }


    public static Tour search(int level, int iterations) {
        bestTour.setCost(Double.POSITIVE_INFINITY);

        long start = System.nanoTime();

        if(level == 0)
            return rollout();
        else {
            policy[level-1] = globalPolicy;

            for(int i=0; i < iterations; i++) {
                Tour newTour = search(level-1, i);
                if(newTour.getCost() < bestTour.getCost()); {
                    bestTour = newTour;
                    adapt(bestTour, level);
                }
                long end = System.nanoTime();
                double duration_seconds = (end - start) * Math.pow(10, -9);

                if (duration_seconds > 60)
                    return bestTour;
            }
            globalPolicy = policy[level-1];
        }
        return bestTour;
    }

    private static void adapt(Tour a_tour, int level) {
        ArrayList<Vertex> visitedVertexList = new ArrayList<>();

        for(int i=0; i < a_tour.getListOfRoutes().size(); i++) {
            ArrayList<Vertex> routes = a_tour.getListOfRoutes().get(i);

            for(int j =0; j < routes.size() -1 ; j++) {
                Vertex stops = routes.get(j);
                Vertex nextStop = routes.get(j+1);
                policy[level-1][stops.getID()][nextStop.getID()] += alpha;
                double z = 0.0;

                for(Edge possibleMove: stops.EdgeList) {
                    if(!possibleMove.toVertex.isVisited())
                        z += Math.exp(globalPolicy[stops.getID()][possibleMove.toVertex.getID()]);
                }
                for(Edge possibleMove: stops.EdgeList) {
                    if(!possibleMove.toVertex.isVisited()) {
                        policy[level-1][stops.getID()][possibleMove.toVertex.getID()] -=
                                alpha * (Math.exp(globalPolicy[stops.getID()][possibleMove.toVertex.getID()])/z); }
                }
//                stops.visit();
                visitedVertexList.add(stops);
            }
        }
//        G.unvisitAll();
    }

    private static Tour rollout() {


        ArrayList<Vertex> newRoute = new ArrayList<>();
        Tour newTour = new Tour();
        newRoute.add(G.getHead());
        newTour.getListOfRoutes().add(newRoute);

        ArrayList<Vertex> possibleSuccessors = new ArrayList<>();
        ArrayList<Integer> visitedVertexList = new ArrayList<>();
        ArrayList<Integer> checkedVertexList = new ArrayList<>();


        int tempC = C;
        double totalCost = 0;


        while(true) {

            ArrayList<Vertex> LastRoute = newTour.getLastRoute();
            Vertex currentStop = LastRoute.get(LastRoute.size()-1);

            for(Edge edge: currentStop.EdgeList) {  //find possible successors
                Vertex uncheckedSuccessor = edge.toVertex;
                if(uncheckedSuccessor.getID()!= 0 && !uncheckedSuccessor.isVisited() && !uncheckedSuccessor.isChecked())
                    possibleSuccessors.add(uncheckedSuccessor);
            }
            if(possibleSuccessors.isEmpty()) {
                newRoute.add(G.getHead());  //currentRoute completed and return to depot
                if(G.isAllVisitedExceptDepot()) {
                    break; }
                newRoute = new ArrayList<>();
                newRoute.add(G.getHead());
                newTour.getListOfRoutes().add(newRoute); //add new route
//                G.getHead().unvisit();
                G.uncheckAll();
                tempC = C;
                continue;
            }

            Vertex nextStop = selectNextMove(currentStop, possibleSuccessors);
//            if(nextStop.getID() == 0) {
//                G.getHead().visit();
//                newRoute.add(nextStop);
//                if(G.isAllVisited()) {
//                    break; }
//
//                newRoute = new ArrayList<>();
//                newRoute.add(G.getHead());
//                newTour.getListOfRoutes().add(newRoute); //add new route
//                G.getHead().unvisit();
//                possibleSuccessors.clear();
//                G.uncheckAll();
//                tempC = C;
//                continue;
//            }

            if(tempC >= nextStop.demandSize){
                newRoute.add(nextStop);
                nextStop.visit();
                tempC -=nextStop.demandSize;

            }
            else{
                nextStop.check(); }

            possibleSuccessors.clear();


        }
        G.unvisitAll();
        G.uncheckAll();

        for(ArrayList<Vertex> routes : newTour.getListOfRoutes()) {
            totalCost += newTour.distanceOfRoute(routes);
        }
        newTour.setCost(totalCost);
        return newTour;
    }

    private static Vertex selectNextMove(Vertex currentStop, ArrayList<Vertex> possibleSuccessors) {
        double[] probability = new double[possibleSuccessors.size()];
        double sum = 0;


        for(int i=0; i<possibleSuccessors.size(); i++) {
            probability[i] = Math.exp(globalPolicy[currentStop.getID()][possibleSuccessors.get(i).getID()]);
            sum += probability[i];
        }

        double mrand = new Random().nextDouble()*sum;
        int i=0;
        sum = probability[0];
        while(sum < mrand) {
            sum += probability[++i];
        }
        return possibleSuccessors.get(i);
    }

}

class Tour {
    private double cost;
    public static ArrayList<ArrayList<Vertex>> listOfRoutes; //a list of list

    public Tour() {
        cost = 0;
        listOfRoutes = new ArrayList<>();
    }

    public Tour(ArrayList<ArrayList<Vertex>> listOfRoutes) {
        this.listOfRoutes = listOfRoutes;
    }

    public double getCost() {
        return cost;
    }

    public ArrayList<ArrayList<Vertex>> getListOfRoutes() {
        return listOfRoutes;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<Vertex> getLastRoute() {
        if(this.getListOfRoutes().isEmpty())
            return this.getListOfRoutes().get(0);
        else
            return this.getListOfRoutes().get(this.getListOfRoutes().size()-1);
    }

    public double distanceOfRoute(ArrayList<Vertex> route) {
        double distanceTravelled = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            Vertex currentVertex = route.get(i);
            Vertex nextVertex = route.get(i + 1);
//            double x = currentVertex.getCoordinate().x - next.coordinateX;
//            double y = currentVertex.coordinateY - next.coordinateY;
//            distance += Math.sqrt(dx * dx + dy * dy);
            distanceTravelled += currentVertex.getCoordinate().distanceTo(nextVertex.getCoordinate());
        }
        return distanceTravelled;
    }
}

