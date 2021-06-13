package com.example;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);

	    System.out.print("Number of nodes, N: ");

	    int N = sc.nextInt();

		System.out.print("Vehicle capacity, C: ");

		int C = sc.nextInt();

		WeightedGraph VRP = new WeightedGraph();
        GreedySearch search = new GreedySearch();
		BreadthSearch searchBFS = new BreadthSearch();

	    for(int i=0; i < N; i++) {
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

	    System.out.println("Complete");
	}
}
