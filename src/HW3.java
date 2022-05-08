/*
 * File: HW3.java
 * Date: Spring 2022
 * Auth: S. Bowers
 * Desc: Basic performance test driver for HW-3
 */

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.List;


public class HW3 {
  public static int SEED = 2;
  public static int RUNS = 3;


  // private constructor
  private HW3() {
  }

  public static double timeSparseDFS(Graph<Integer> g) {
    GraphGenerator.loadSparse(g, SEED);
    double total = 0;
    for (int i = 0; i < RUNS; ++i) {
      Instant start = Instant.now();
      Map<Integer,Integer> tree = GraphAlgorithms.dfs(g, 0);
      Instant end = Instant.now();
      total += ChronoUnit.MILLIS.between(start, end);
    }
    // return ChronoUnit.MICROS.between(start, end);
    return total / RUNS;
  }
  
  // basic perf tests
  public static void performanceTests() {
    int start = 5; // must be > 0
    int step = 2;  
    int end = 40000;

    // print header
    System.out.println("# All times in milliseconds");
    System.out.println("# Column 1: input (node) size n");
    System.out.println("# Column 2: dfs sparse undirected adj list");
    System.out.println("# Column 3: dfs sparse directed adj list");
    System.out.println("# Column 4: dfs sparse undirected adj matrix");
    System.out.println("# Column 5: dfs sparse directed adj matrix");
    
    for (int n = start; n <= end; n *= step) {
      // directed adjacency matrix
      Graph<Integer> g1 = new AdjacencyList(n, false);
      Graph<Integer> g2 = new AdjacencyList(n, true);
      Graph<Integer> g3 = new AdjacencyMatrix(n, false);
      Graph<Integer> g4 = new AdjacencyMatrix(n, true);
      double c1 = timeSparseDFS(g1);
      double c2 = timeSparseDFS(g2);
      double c3 = timeSparseDFS(g3);
      double c4 = timeSparseDFS(g4);
      // skip start value (caching, etc)
      if (n == start) {
        continue;
      }
      // print results
      System.out.printf("%d %.2f %.2f %.2f %.2f \n", n, c1, c2, c3, c4);
    }
  }
  
  
  // helper to save graph to a dotfile
  // to generate graph: dot -Tpdf filename.dot > filename.pdf
  public static void save(Graph<Integer> g, String filename) throws Exception {
    FileWriter f = new FileWriter(filename);
    PrintWriter out = new PrintWriter(new BufferedWriter(f));
    int n = g.nodeCount();
    if (g.directed()) {
      out.println("digraph {");
      out.println(" rankdir=LR;");
      for (int i = 0; i < n; ++i) 
        for (int j = 0; j < n; ++j) 
          if (g.hasEdge(i, j))
            out.println(" " + i + " -> " + j + " [label=" + g.label(i, j) + "];");
      out.println("}");
    }
    else {
      out.println("graph {");
      out.println(" rankdir=LR;");
      for (int i = 0; i < n; ++i) 
        for (int j = i + 1; j < n; ++j) 
          if (g.hasEdge(i, j))
            out.println(" " + i + " -- " + j + " [label=" + g.label(i, j) + "];");
      out.println("}");
    }
    out.close();
  }

  
  public static void main(String[] args) throws Exception {
    performanceTests();
  }

  
}
