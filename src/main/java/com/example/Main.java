package com.example;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    int N = sc.nextInt(); //N: number of customers + 1 depot
        int C = sc.nextInt(); //C: vehicle capacity
        WeightedGraph VRP = new WeightedGraph();
        GreedySearch search = new GreedySearch();
	BreadthSearch searchBFS = new BreadthSearch();

	    for(int i=0; i < N; i++) {
            VRP.addVertex(new Vertex(sc.nextDouble(), sc.nextDouble(), sc.nextInt(), i));
	        }

	    VRP.completeGraph();
	    search.run(VRP, C);
	    BreadthSearch.work(VRP, C);

        }

    }



