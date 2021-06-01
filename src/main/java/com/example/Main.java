package com.example;

import org.jgrapht.*;
import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import org.jgrapht.util.*;

import java.util.*;
import java.util.function.*;

public class Main {

    // number of vertices
    private static final int SIZE = 4;

    /**
     * Main demo entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        // Create the VertexFactory so the generator can create vertices
        Supplier<String> vSupplier = new Supplier<String>()
        {
            private int id = 0;

            @Override
            public String get()
            {
                String name = id == 0 ? "D" : "C";

                return name + id++;
            }
        };

        // Create the graph object
        Graph<String, DefaultEdge> completeGraph =
                new SimpleGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);

        // Create the CompleteGraphGenerator object
        CompleteGraphGenerator<String, DefaultEdge> completeGenerator =
                new CompleteGraphGenerator<>(SIZE);

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph);

//        for (DefaultEdge edge: completeGraph.edgeSet()) {
//            System.out.println(completeGraph.getEdgeSource(edge).toString() + "<---->" + completeGraph.getEdgeTarget(edge).toString());
//        }

        Set<String> vertices = completeGraph.vertexSet();

        for (String v: vertices)
        {
            List<String> neighbours = Graphs.neighborListOf(completeGraph, v);

            System.out.println("Neighbours for " + v);

            for (String n: neighbours)
            {
                System.out.println(n);
            }
        }

//        Set<String> vertices = completeGraph.vertexSet();
//
//        for (String v: vertices)
//        {
//            Set<DefaultEdge> outgoingEdges = completeGraph.outgoingEdgesOf(v);
//
//            System.out.println("Current vertex: " + v);
//
//            for (DefaultEdge edge: outgoingEdges)
//            {
//                System.out.println(completeGraph.getEdgeTarget(edge));
//            }
//
//            System.out.println("====");
//        }

//         Print out the graph to be sure it's really complete
//        Iterator<String> iter = new DepthFirstIterator<>(completeGraph);
//
//        while (iter.hasNext()) {
//            String vertex = iter.next();
//            System.out
//                    .println(
//                            "Vertex " + vertex + " is connected to: "
//                                    + completeGraph.edgesOf(vertex).toString());
//        }
    }
}
