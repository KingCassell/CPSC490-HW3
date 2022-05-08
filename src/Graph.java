/* 
 * File: Graph.java
 * Date: Spring 2022
 * Auth: S. Bowers
 * Desc: Basic graph interface with (integer) labeled nodes (i.e.,
 *       vertices) and (generically) labeled edges. We allow
 *       self-loops but only allow at most one edge (regardless of
 *       label) between any two nodes (vertices).
 */

import java.util.List;


public interface Graph<T> {

  // Returns true if the graph is directed and false if undirected.
  public boolean directed();

  // Adds a labeled edge to the graph from node1 to node2. Only adds
  // the edge if valid nodes are given and the edge doesn't already
  // exist.
  public void add(int node1, T label, int node2);

  // Removes an edge from the graph if it exists
  public void remove(int node1, int node2);

  // Updates the label for a given edge. If the edge does not exist,
  // the edge is not added.
  public void set(int node1, T label, int node2);

  // Returns true if the graph contains an edge from node1 to node2
  // and false otherwise. Note that if the graph is undirected, no
  // directionality is implied by the edge.
  public boolean hasEdge(int node1, int node2);

  // Returns the label on the give edge.
  // Pre: the edge exists
  public T label(int node1, int node2);

  // Returns all nodes adjacent to the given node. In a directed
  // graph, this includes the set of nodes on outgoing and incoming
  // edges, i.e., that lie on out-edges and in-edges. 
  public List<Integer> adjacent(int node);

  // Returns all nodes on an outgoing edge (out-edge) from the given
  // node. For an undirected graph, this method may not be the same as
  // calling adjacent, depending on the underlying graph
  // implementation.
  public List<Integer> outNodes(int node);

  // Returns all nodes on an incoming edge (in-edge) from the given
  // node. For an undirected graph, this method may not be the same as
  // calling adjacent, depending on the underlying graph
  // implementation.
  public List<Integer> inNodes(int node);

  // Returns the number of nodes in the graph
  public int nodeCount();

  // Returns the number of edges in the graph. In a directed graph, it
  // returns the total number of directed edges. In an undirected
  // graph, it returns the number of undirected edges.
  public int edgeCount(); 

}