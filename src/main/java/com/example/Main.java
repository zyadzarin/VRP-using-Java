package com.example;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	private final static int level = 3;
	private final static int iterations = 10;
	private final static int alpha = 1;
	public static int N;

    public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    int N = sc.nextInt(); //N: number of customers + 1 depot
        int C = sc.nextInt(); //C: vehicle capacity
        WeightedGraph VRP = new WeightedGraph();
        GreedySearch search = new GreedySearch();
		BreadthSearch searchBFS = new BreadthSearch();
		MCTS mcts = new MCTS();

	    for(int i=0; i < N; i++) {
            VRP.addVertex(new Vertex(sc.nextDouble(), sc.nextDouble(), sc.nextInt(), i));
	        }

	    VRP.completeGraph();
//	    search.run(VRP, C);


		mcts.run(VRP, N, C, level, iterations, alpha);

//	    BreadthSearch.work(VRP, C);

        }


}



