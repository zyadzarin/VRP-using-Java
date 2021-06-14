package com.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;
import java.util.function.Supplier;

public class DepthFirstSearch {
    private List<List<Vertex>> solutions;

    private double total_tour_cost;
    private int vehicle_count;

    List<Vertex> vertexList;
    Graph<Vertex, DefaultWeightedEdge> graph;
    Vertex startVertex;

    int nodeCount;
    int defaultCapacity;

    public DepthFirstSearch(List<Vertex> vertexList, int nodeCount, int defaultCapacity) {
        this.solutions = new ArrayList<>();

        this.vertexList = vertexList;
        this.graph = generateCompleteGraph(nodeCount, vertexList);

        Set<Vertex> graphVertices = graph.vertexSet();
        Iterator<Vertex> vertexIterator = graphVertices.iterator();

        this.startVertex = vertexIterator.next();
        this.nodeCount = nodeCount;
        this.defaultCapacity = defaultCapacity;
    }

    public void run() {
        int vehicle_cap = this.defaultCapacity;
        double curr_tour_cost = 0.0f;

        List<Vertex> solution = new ArrayList<>();

        Stack<Vertex> stack = new Stack<>();

        stack.push(this.startVertex);

        while (stack.size() > 0) {
            Vertex curr = stack.pop();

            if (!curr.isVisited()) {
                if (curr.getDemandSize() <= vehicle_cap) {
                    curr.visit();

                    if (solution.size() != 0) {
                        Vertex lastVisited = solution.get(solution.size() - 1);

                        DefaultWeightedEdge edge = graph.getEdge(curr, lastVisited);

                        double weight = graph.getEdgeWeight(edge);

                        curr_tour_cost += weight;
                        total_tour_cost += weight;
                    }

                    solution.add(curr);
                    vehicle_cap -= curr.getDemandSize();

                    List<Vertex> neighbours = Graphs.neighborListOf(graph, curr);

                    for (Vertex n : neighbours) {
                        if (!n.isVisited()) {
                            stack.push(n);
                        }
                    }
                } else {
                    System.out.println("Current tour cost: " + curr_tour_cost);
//                    stack.push(curr);

//                    System.out.println("Adding total tour cost: " + curr_tour_cost);
//                    total_tour_cost += curr_tour_cost;

//                    solution.add(v);

                    DefaultWeightedEdge returningEdge = graph.getEdge(curr, this.startVertex);
                    double returningEdgeWeight = graph.getEdgeWeight(returningEdge);

//                    System.out.println("Curr: " + curr);
//                    System.out.println("Depot: " + v);
//                    System.out.println("Returning edge weight: " + returningEdgeWeight);

                    total_tour_cost += returningEdgeWeight;
                    this.vehicle_count++;

                    this.solutions.add(new ArrayList<>(solution));

                    solution.clear();

                    this.startVertex.unvisit();

                    solution.add(this.startVertex);
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
        System.out.println("Total tour cost: " + this.total_tour_cost);
        System.out.println("Vehicle count: " + this.vehicle_count);
        System.out.println("Solutions: ");

        for(List<Vertex> sol: solutions) {
            for (Vertex v: sol) {
                System.out.print(v.getID() + " ---> ");
            }
            System.out.println();
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

        Supplier<DefaultWeightedEdge> edgeSupplier = DefaultWeightedEdge::new;

        Graph<Vertex, DefaultWeightedEdge> completeGraph =
                new DefaultUndirectedWeightedGraph<>(vSupplier, edgeSupplier);

        CompleteGraphGenerator<Vertex, DefaultWeightedEdge> completeGenerator = new CompleteGraphGenerator<>(N);

        completeGenerator.generateGraph(completeGraph);

        Set<Vertex> vertices = completeGraph.vertexSet();

        // set edge weight to distance between 2 vertices
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
