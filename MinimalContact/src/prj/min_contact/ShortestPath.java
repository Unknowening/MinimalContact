package prj.min_contact;

import java.util.*;
import java.io.*;

public class ShortestPath {
	
	static final Scanner in = new Scanner(System.in);
	static final PrintWriter out = new PrintWriter(System.out);
	
	public static void main(String[] args) {
		new Solve();
		out.close();
	}
	
	static class Solve {
		Solve() {
			
			//build graph 
			int edges = in.nextInt();
			int nodes = in.nextInt();
			int classNum = in.nextInt();
			int[] classArr = new int[classNum];
			int[][] graph = new int[nodes][nodes];
			
			for(int i = 0; i < classNum; i++) {
				classArr[i] = in.nextInt();
			}
			
			
			for(int i = 0; i < edges; i++) {
				int nodeOne = in.nextInt();
				int nodeTwo = in.nextInt();
				int avgStudents = in.nextInt();
				graph[nodeOne][nodeTwo] = avgStudents;
				graph[nodeTwo][nodeOne] = avgStudents;
			}
			
			//calculate best path to take and people passed
			int sum = 0;
			out.print("Path:");
			for(int i = 0; i < classNum - 1; i++) {
				sum += dijkstra(graph, classArr[i], classArr[i + 1], nodes);
				if (i < classNum - 2) {
					out.print(",");
				}
			}
			out.print("\nPeople Passed: " + sum);
		}
	}
	
	//Dijkstra’s Shortest Path Algorithm
	static int dijkstra(int graph[][], int start, int end, int nodes) {
		
		//stores least people passed from start to other nodes
		//initializing to max integer (substitute for infinity)
		int[] pop = new int[nodes];
		for (int i = 0; i < nodes; i++) {
			pop[i] = Integer.MAX_VALUE;
		}
		pop[start] = 0;
		
		//stores permanent nodes
		boolean[] perm = new boolean[nodes];
		
		//stores parent of each node for shortest path
		int[] parents = new int[nodes];
		parents[start] = -1;
		
		//find paths that pass the lead number of people
		for (int i = 0; i < nodes - 1; i++) {
			
			//selects closest node to start that is not yet permanent
			int close = -1;
			int closePop = Integer.MAX_VALUE;
			for (int j = 0; j < nodes; j++) {
				
				if (!perm[j] && pop[j] < closePop) {
					close = j;
					closePop = pop[j];
				}
				
			}
			//makes selected node permanent
			perm[close] = true;
			 
			//updates people passed and parents
			for (int j = 0; j < nodes; j++) {
				
				int edgePop = graph[close][j];
				
				if (edgePop > 0 && (closePop + edgePop) < pop[j]) {
					parents[j] = close;
					pop[j] = closePop + edgePop;
				}
			}
			
		}
		
		printPath(end, parents);
		
		return pop[end];
    }
	
	//prints path
	static void printPath(int node, int[] parents) {
		if (node == -1) {
			return;
		}
		printPath(parents[node], parents);
        out.print(" " + node);
	}
	
}
