package com.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.*;
import org.jgrapht.util.SupplierUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Supplier;

public class DepthFirstSearchTest {

    /**
     * Main demo entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws IOException {
        final String filePath = "/home/ad/Desktop/Delivery-Instances/instances/n3-c27.txt";
//        final String filePath = "/home/ad/Desktop/Delivery-Instances/instances/n5-c10.txt";
        File file = new File(filePath);

        try (Scanner fileReader = new Scanner(file)) {

            List<Vertex> vertexList = new ArrayList<>();
            String NC = fileReader.nextLine();
            String[] NC_info = NC.split(" ");

            int N = Integer.parseInt(NC_info[0]);
            int C = Integer.parseInt(NC_info[1]);

            System.out.println("Number of nodes=" + N);
            System.out.println("Vehicle capacity=" + C + "\n");

            // read & create nodes
            int i = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] info = line.split(" ");

                double x = Double.parseDouble(info[0]);
                double y = Double.parseDouble(info[1]);
                int demand = Integer.parseInt(info[2]);

                vertexList.add(new Vertex(x, y, demand, i++));
            }

            Graph<Vertex, DefaultWeightedEdge> graph = generateCompleteGraph(N, vertexList);

            Set<Vertex> graphVertices = graph.vertexSet();

            Iterator<Vertex> vertexIterator = graphVertices.iterator();

            Vertex depot = vertexIterator.next();

            run(graph, depot, C);

        }
    }

    private static void run(Graph<Vertex, DefaultWeightedEdge> graph, Vertex v, int defaultCapacity) {
        int vehicle_cap = defaultCapacity;
        int vehicle_count = 0;
        double total_tour_cost = 0.0f;
        double curr_tour_cost = 0.0f;

        List<List<Vertex>> solutions = new ArrayList<>();
        List<Vertex> solution = new ArrayList<>();

        Stack<Vertex> stack = new Stack<>();

        stack.push(v);

        while (stack.size() > 0) {
            Vertex curr = stack.pop();

            if (!curr.isVisited()) {
                if (curr.getDemandSize() <= vehicle_cap) {
                    curr.visit();

                    if (solution.size() == 0) {

                    } else {
                        Vertex lastVisited = solution.get(solution.size() - 1);

                        DefaultWeightedEdge edge = graph.getEdge(curr, lastVisited);

                        double weight = graph.getEdgeWeight(edge);

//                        System.out.println("Edge weight: " + weight);
                        curr_tour_cost += weight;
                        total_tour_cost += weight;
                    }

                    solution.add(curr);
                    vehicle_cap -= curr.getDemandSize();

                    List<Vertex> neighbours = Graphs.neighborListOf(graph, curr);
                    Iterator<Vertex> iter = neighbours.iterator();

                    while (iter.hasNext()) {
                        Vertex n = iter.next();

                        if (!n.isVisited()) {
                            stack.push(n);
                        }
                    }
                } else {
                    // mcm betul lol
//                    stack.push(curr);

//                    System.out.println("Adding total tour cost: " + curr_tour_cost);
//                    total_tour_cost += curr_tour_cost;

//                    solution.add(v);

                    DefaultWeightedEdge returningEdge = graph.getEdge(curr, v);
                    double returningEdgeWeight = graph.getEdgeWeight(returningEdge);

//                    System.out.println("Curr: " + curr);
//                    System.out.println("Depot: " + v);
//                    System.out.println("Returning edge weight: " + returningEdgeWeight);

                    total_tour_cost += returningEdgeWeight;
                    vehicle_count++;

                    solutions.add(new ArrayList<>(solution));

                    solution.clear();

                    v.unvisit();

                    solution.add(v);
//                    solution.add(curr);

//                    stack.clear();
//                    stack.add(curr);

                    vehicle_cap = defaultCapacity;

//                    System.out.println("curr demand size: " + curr.getDemandSize());
//                    System.out.println("vehicle cap: " + vehicle_cap);
//                    System.out.println("next curr: " + curr);
//                    System.out.println("graph: " + graph.vertexSet());

                    curr_tour_cost = 0;
                }
            }
        }

        System.out.println();
        System.out.println("Total tour cost: " + total_tour_cost);

        System.out.println("Solutions: ");
        for(List<Vertex> sol: solutions) {
            System.out.println(sol);
        }

    }

    private static Graph<Vertex, DefaultWeightedEdge> generateCompleteGraph(int N, List<Vertex> vertexList) {
        Supplier<Vertex> vSupplier = new Supplier<Vertex>() {
            private int id = 0;

            @Override
            public Vertex get()
            {
                return vertexList.get(id++);
            }
        };

        Supplier<DefaultWeightedEdge> edgeSupplier = new Supplier<DefaultWeightedEdge>() {
            @Override
            public DefaultWeightedEdge get() {
                return new DefaultWeightedEdge();
            }
        };

        Graph<Vertex, DefaultWeightedEdge> completeGraph =
                new DefaultUndirectedWeightedGraph<>(vSupplier, edgeSupplier);

        CompleteGraphGenerator<Vertex, DefaultWeightedEdge> completeGenerator = new CompleteGraphGenerator<>(N);

        completeGenerator.generateGraph(completeGraph);

        Set<Vertex> vertices = completeGraph.vertexSet();

        for (Vertex v: vertices)
        {
            List<Vertex> neighbours = Graphs.neighborListOf(completeGraph, v);

            for (Vertex n: neighbours)
            {
                double distance = v.getCoordinate().distanceTo(n.getCoordinate());

                DefaultWeightedEdge e = completeGraph.getEdge(v, n);

                completeGraph.setEdgeWeight(e, distance);
            }
        }

        return completeGraph;
    }

}
