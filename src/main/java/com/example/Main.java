package com.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int level = 3;
    public static int iterations = 100;
    public static double alpha = 1;

    public static void main(String[] args) {
        readFromFile();
    }

    public static void readFromFile() {
        WeightedGraph VRP = new WeightedGraph();
//        GreedySearch search = new GreedySearch();
//        BreadthSearch searchBFS = new BreadthSearch();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter file path=");

        String filePath = scanner.nextLine();

        try {
            Scanner fileReader = new Scanner(new File(filePath));
            int i = 0;

            String NC_line = fileReader.nextLine();
            String[] NC_parts = NC_line.split(" ");

            int N = Integer.parseInt(NC_parts[0]);
            int C = Integer.parseInt(NC_parts[1]);

            String depot_line = fileReader.nextLine();
            String[] depot_parts = depot_line.split(" ");

            // only take the x and y values
            double depot_x = Double.parseDouble(depot_parts[0]);
            double depot_y = Double.parseDouble(depot_parts[1]);

            VRP.addVertex(new Vertex(depot_x, depot_y, 0, i++));

            System.out.println("Number of nodes=" + N);
            System.out.println("Vehicle capacity=" + C);
//			System.out.printf("Depot at (%d, %d)\n", depot_x, depot_y);

            while (fileReader.hasNext()) {
                String customer_line = fileReader.nextLine();
                String[] customer_info = customer_line.split(" ");

                double cust_x = Double.parseDouble(customer_info[0]);
                double cust_y = Double.parseDouble(customer_info[1]);
                int cust_demand = Integer.parseInt(customer_info[2]);

//				System.out.printf("Customer at (%d, %d) with demand (%d)\n",
//						cust_x, cust_y, cust_demand
//				);

                VRP.addVertex(new Vertex(cust_x, cust_y, cust_demand, i++));
            }

            System.out.println("Constructing complete graph...");
            VRP.completeGraph();

            System.out.println("Running greedy search...");
            GreedySearch.run(VRP, C);

            System.out.println("Running breadth first search...");
            BreadthSearch.work(VRP, C);

            System.out.println("Complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void manualInput() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Number of nodes, N: ");

        int N = sc.nextInt();

        System.out.print("Vehicle capacity, C: ");

        int C = sc.nextInt();

        WeightedGraph VRP = new WeightedGraph();
//        GreedySearch search = new GreedySearch();
//        BreadthSearch searchBFS = new BreadthSearch();

        for (int i = 0; i < N; i++) {
            System.out.println("Supplying info for node " + i);
            System.out.print("Enter x=");
            double x = sc.nextDouble();

            System.out.print("Enter y=");
            double y = sc.nextDouble();

            System.out.print("Enter demand=");
            int demand = sc.nextInt();

            VRP.addVertex(new Vertex(x, y, demand, i));

            System.out.println();
        }

        System.out.println("Constructing complete graph...");
        VRP.completeGraph();

        System.out.println("Running greedy search...");
        GreedySearch.run(VRP, C);

        System.out.println("Running breadth first search...");
        BreadthSearch.work(VRP, C);

        MCTS.run(VRP, N, C, level, iterations, alpha);

        System.out.println("Complete");
    }
}
