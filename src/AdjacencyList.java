/*
 * File: AdjacencyList.java
 * Date: Spring 2022
 * Auth: Dustin Cassell
 * Desc: Class for a list implementation of a graph.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdjacencyList<T> implements Graph<T> {

    // the total number of nodes in the graph (numbered 0 to nodes-1)
    private int nodeCount;

    // the total number of edges in the graph
    private int edgeCount;

    // true if the graph is directed, false if undirected
    private boolean directed;

    // the adjacency list as an array of node-to-label mappings
    private Map<Integer,T>[] adjList;


    //--------------------------------------------------------------------
    // constructor
    //--------------------------------------------------------------------

    // Initializes the graph with the given number of nodes and the
    // given directionality.
    public AdjacencyList(int nodeCount, boolean directed) {
        this.nodeCount = nodeCount;
        this.directed = directed;
        adjList = new HashMap[nodeCount];
        // initialize each array element to an empty hashmap
        for (int i = 0; i < nodeCount; ++i)
            adjList[i] = new HashMap<>();
    }


    //--------------------------------------------------------------------
    // graph methods
    //--------------------------------------------------------------------

    // Returns true if the graph is directed and false if undirected.

    /**
     * Purpose: Generic getter for the field boolean directed.
     * @return true if the graph is directed, otherwise false.
     */
    public boolean directed() {
        return directed;
    }


    /**
     * Purpose: Adds a labeled edge to the graph from node1 to node2 if a directed graph.
     *          This method only adds the edges if there is no data already inserted into
     *          the list.
     * @param node1 The starting node if it's a directed graph.
     * @param label The data of type T to be stored as the label for the edge.
     * @param node2 The ending node if it's a directed graph.
     */
    public void add(int node1, T label, int node2) {
        if (directed) {
            if (adjList[node1].get(node2) == null) {
                // if graph is directed and node1 is empty,
                // add node2 as connected to node1.
                adjList[node1].put(node2, label);
                ++edgeCount;
            } else {
                System.out.println("Invalid Add: " + node1 + " already contains edge");
            }
        } else {
            if (adjList[node1].get(node2) == null) {
                // if graph is undirected and has any node info stored inside,
                // add both node1 and node2 data.
                adjList[node1].put(node2, label);
                adjList[node2].put(node1, label);
                ++edgeCount;
            } else {
                System.out.println("Invalid Add: " + node1 + " already contains edge");
            }
        }
    }


    /**
     * Purpose: Removes and edge from the graph if an edge exists between node1 and node2.
     * @param node1 The starting node if the graph is directed.
     * @param node2 The ending node if the graph is directed.
     */
    public void remove(int node1, int node2) {
        if (directed) {
            if (adjList[node1].get(node2) != null) {
                // if graph is directed and has a node associated with node 1
                adjList[node1].remove(node2);
                --edgeCount;
            } else {
                System.out.println("Invalid Remove: " + node1 + " is already null");
            }
        } else {
            if (adjList[node1].get(node2) != null) {
                // if graph is undirected and has any node info stored inside,
                // remove both node1 and node2 data.
                adjList[node1].remove(node2);
                adjList[node2].remove(node1);
                --edgeCount;
            } else {
                System.out.println("Invalid Remove: " + node1 + " is already null");
            }
        }
    }


    /**
     * Purpose: Sets the label on the edge from node1 to node2 if the graph is directed. If
     *          not directed, both directions get the same label added to their edges.
     * @param node1 The starting node if it's a directed graph.
     * @param label The data of type T to be stored as the label for the edge.
     * @param node2 The ending node if it's a directed graph.
     */
    public void set(int node1, T label, int node2) {
        if (directed) {
            // Directionality is assumed to be from node1 to node2.
            adjList[node1].put(node2, label);
        } else {
            // if undirected add label to both edges.
            adjList[node1].put(node2, label);
            adjList[node2].put(node1, label);
        }
    }


    /**
     * Purpose: Generic Getter for the label attribute between Node 1
     *          and Node 2. Directionality is intended to be from Node 1
     *          to Node 2. For Undirected Graphs, the order of Nodes does
     *          not matter.
     * @param node1 Starting Node to be checked if directional. If undirected,
     *              order doesn't matter.
     * @param node2 Ending Node to be checked if directional. If undirected,
     *              order doesn't matter.
     * @return Type T label object stored between Node 1 and Node 2.
     */
    public T label(int node1, int node2) {
        return adjList[node1].get(node2);
    }


    /**
     * Purpose: if the provided nodes have an edge from node 1 to node 2
     *          this method will return true. This works for both directed
     *          and undirected because if it is directed, the only direction
     *          to be checked is from node 1 to node 2. If it is an
     *          undirected graph, the order of nodes will not matter.
     * @param node1 Starting Node to be checked if directional. If undirected,
     *              order doesn't matter.
     * @param node2 Ending Node to be checked if directional. If undirected,
     *              order doesn't matter.
     * @return True if there is a non-null object stored in the label between
     *         Node 1 and Node 2.
     */
    public boolean hasEdge(int node1, int node2) {
        return adjList[node1].get(node2) != null;
    }


    /**
     * Purpose: Finds all incoming and outgoing edges for the passed in node. If it's a directed,
     *          graph, Both the in and out nodes are checked to find adjacency. If the graph is
     *          undirected, only the outgoing edges are checked since directionality doesn't matter.
     * @param node The node to be checked for adjacent node edges
     * @return A List of Integer values for the nodes that are adjacent to the passed in node.
     */
    public List<Integer> adjacent(int node) {
        List<Integer> nodesList = new ArrayList<>();
        if (directed) {
            for (int index = 0; index < nodeCount; index++) {
                if ((adjList[node].containsKey(index) ||
                        adjList[index].containsKey(node) )&&
                        !nodesList.contains(index)) {
                    // check if either incoming or outgoing edges exist on the parameter node.
                    nodesList.add(index);
                }
            }
        } else {
            for (int index = 0; index < nodeCount; index++) {
                if (adjList[node].containsKey(index) && !nodesList.contains(index)) {
                    // if the node index is stored in the map and not already added to the nodesList.
                    nodesList.add(index);
                }
            }
        }
        return nodesList;
    }


    /**
     * Purpose: Creates a list of integer keys for every out going node from the
     *          adjacency list. The method loops through the adjacency list and checks
     *          every node stored in the hash map. If there is a node key stored in
     *          the map, and it is not already stored in the out nodes list, the key
     *          index is added to the out nodes list. The method then returns the list
     *          of outgoing nodes.
     * @param node The key index of the node to be checked for outgoing node edges.
     * @return A list of integer keys for all the nodes outgoing edges from the node
     *         parameter.
     */
    public List<Integer> outNodes(int node) {
        List<Integer> nodesList = new ArrayList<>();
        for (int index = 0; index < nodeCount; ++index) {
            if (adjList[node].containsKey(index) && !nodesList.contains(index)) {
                // if the node index is stored in the map and not already added to the nodesList.
                nodesList.add(index);
            }
        }
        return nodesList;
    }


    /**
     * Purpose: Creates a list of integer keys for every incoming node edge from the
     *          adjacency list. The method loops through the adjacency list and checks
     *          every node stored in the hash map. If there is a node key that sends an
     *          edge to the parameter node, and it is not already stored in the out
     *          nodes list, the key index is added to the out nodes list. The method
     *          then returns the list of outgoing nodes.
     * @param node The key index of the node to be checked for incoming node edges.
     * @return A list of integer keys for all the nodes outgoing edges from the node
     *         parameter.
     */
    public List<Integer> inNodes(int node) {
        List<Integer> nodesList = new ArrayList<>();
        for (int index = 0; index < nodeCount; ++index) {
            if (adjList[index].containsKey(node) && !nodesList.contains(index)) {
                // if the node index has the parameter node stored in its hash map
                // and not already added to the nodesList.
                nodesList.add(index);
            }
        }
        return nodesList;
    }

    /**
     * Purpose: Generic getter for the number of nodes currently in the graph.
     * @return Integer count of nodes in Graph.
     */
    public int nodeCount() {
        return nodeCount;
    }

    /**
     * Purpose: Generic getter for the number of edges currently in the graph.
     * @return Integer count of edges in Graph.
     *    NOTE: In a directed graph, it returns the total number of directed
     *          edges. In an undirected graph, it returns the number of
     *          undirected edges.
     */
    public int edgeCount() {
        return edgeCount;
    }

}