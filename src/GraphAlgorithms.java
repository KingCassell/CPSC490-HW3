
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;


public class GraphAlgorithms {


  // NOTE: This file is included just so you have the signatures for
  // the three functions you are implementing. You should add the
  // functions to your HW2 GraphAlgorithms.java file and replace this
  // file with yours (with the signatures added).

  // SEE: tests/DFSTest.java for additional instructions. 
  
  
  /**
   * Computes the depth first search of the given graph.
   * @param g the graph, either directed or undirected
   * @param src the starting node to search from
   * @return A search tree (node to parent node mapping)
   */
  public static Map<Integer,Integer> dfs(Graph g, int src) {
    // TODO
  }

  /**
   * Checks if a graph contains cycles.
   * @param g the graph, either directed or undirected
   * @return true if the graph is acyclic, false if it contains cycles
   */
  public static boolean acyclic(Graph g) {
    // TODO
  }

  /**
   * Computes a topological sort over a directed graph. 
   * @param g a directed, acyclic graph
   * @return the components for each node (node to component number
   * mapping)
   */
  public static Map<Integer,Integer> topologicalSort(Graph g) {
    // TODO
  }

  
}
