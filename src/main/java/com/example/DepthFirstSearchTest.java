package com.example;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DepthFirstSearchTest {

    /**
     * Main demo entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner fileNameScanner = new Scanner(System.in);

        System.out.print("Enter input file=");

        String filePath = fileNameScanner.nextLine();

//        final String filePath = "/home/ad/Desktop/Delivery-Instances/instances/n3-c27.txt";
//        final String filePath = "/home/ad/Desktop/Delivery-Instances/instances/n5-c10.txt";

        File file = new File(filePath);

        try (Scanner fileReader = new Scanner(file)) {

            List<Vertex> vertexList = new ArrayList<>();
            String NC = fileReader.nextLine();
            String[] NC_info = NC.split(" ");

            int N = Integer.parseInt(NC_info[0]);
            int C = Integer.parseInt(NC_info[1]);

//            System.out.println("Number of nodes=" + N);
//            System.out.println("Vehicle capacity=" + C + "\n");

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

            DepthFirstSearch dfs = new DepthFirstSearch(vertexList, N, C);

            dfs.run();

//            Graph<Vertex, DefaultWeightedEdge> graph = generateCompleteGraph(N, vertexList);
//
//            Set<Vertex> graphVertices = graph.vertexSet();
//
//            Iterator<Vertex> vertexIterator = graphVertices.iterator();
//
//            Vertex depot = vertexIterator.next();


//            run(graph, depot, C);

        }
    }

}
