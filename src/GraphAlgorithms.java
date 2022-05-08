/*
 * File: DFSTest.java
 * Date: Spring 2022
 * Auth: Dustin Cassell
 * Desc: The class that contains all the graph related algorithms.
 */


import java.util.*;


public class GraphAlgorithms {


    // NOTE: This file is included just so you have the signatures for
    // the three functions you are implementing. You should add the
    // functions to your HW2 GraphAlgorithms.java file and replace this
    // file with yours (with the signatures added).

    // SEE: tests/DFSTest.java for additional instructions.

    private static Queue<Integer> queue;
    private static Stack<Integer> stack;
    private static Map<Integer, Integer> discoveredNodes;

    // Singleton
    private GraphAlgorithms() {}


    /**
     * Purpose: Performs a breadth-first traversal of the given graph starting at
     *          the given source node.
     * @param g the directed or undirected graph to search
     * @param src the source node to search from
     * @return the search tree resulting from the breadth-first search
     *         or null if src is an invalid index
     */
    public static Map<Integer,Integer> bfs(Graph g, int src) {
        List<Integer> toVisit = new ArrayList<>(g.nodeCount());
        queue = new LinkedList<Integer>();
        discoveredNodes = new HashMap<Integer, Integer>();
        int uVal;
        discoveredNodes.put(src, -1);
        queue.add(src);
        while (!queue.isEmpty()) {
            // get the next node to check and clear the list of adjacent nodes
            uVal = queue.poll();
            toVisit.clear();
            // Get all the nodes adjacent to the uVal
            if (g.directed()) {
                toVisit.addAll(g.outNodes(uVal));
            } else {
                toVisit.addAll(g.adjacent(uVal));
            }
            for (int vertex: toVisit) {
                // check the list of nodes that can be visited to see if they have already been discovered.
                // if not discovered and the edge exists, add it to the queue of possible nodes to be checked.
                if (!discoveredNodes.containsKey(vertex)) {
                    queue.add(vertex);
                    discoveredNodes.put(vertex, uVal);
                }
            }
        }
        return discoveredNodes;
    }


    /**
     * Purpose: Finds the shortest (unweighted) path from src to dst using
     *          (modified) bfs.
     * @param g the directed or undirected graph to search
     * @param src the source node to search from
     * @param dst the destination node of the path
     * @return the shortest path as a list from src to dst or null if
     *         there is no path, src is invalid, or dst is invalid.
     */
    public static List<Integer> shortestPath(Graph g, int src, int dst) {
        List<Integer> shortestPath = new ArrayList<>(g.nodeCount());
        stack = new Stack<>();
        // add the list of discovered nodes after performing a Breadth first search.
        discoveredNodes = bfs(g, src);
        int parent = 0;
        int child = dst;
        // traverse from the destination backwards to the src until the src is either found or no more nodes exist.
        // child is pointed to by parent for discoveredNodes.
        stack.push(child);
        while (discoveredNodes.get(child) != null) {
            // as the discoverNodes map is traversed and each node found is added to a stack
            // the stack will be used to reverse the order into the shortestPath list once src is found.
            parent = discoveredNodes.get(child);
            stack.push(parent);

            if (parent == src) {
                // load the list from the stack.
                while (!stack.isEmpty()) {
                    shortestPath.add(stack.pop());
                }
                return shortestPath;
            }
            // the child becomes the new parent value to increment the traversal.
            child = parent;
        }
        // if no path exists return null.
        return null;
    }


    /**
     * Purpose: Finds the connected components of the given graph. Treats the
     *          graph as undirected, regardless of whether the graph is directed
     *          or not (i.e., for directed graph, finds its weakly connected
     *          components).
     * @param g the given graph
     * @return the node component map (node to component number)
     */
    public static Map<Integer,Integer> connectedComponents(Graph g) {
        discoveredNodes = new HashMap<>();
        boolean[] visited = new boolean[g.nodeCount()];
        List<Integer> toVisit = new ArrayList<>(g.nodeCount());
        queue = new LinkedList<>();
        discoveredNodes = new HashMap<>();
        int uVal;
        // set all booleans in array to false
        Arrays.fill(visited, Boolean.FALSE);
        // Loop through and check every node in the graph while keeping track of visited
        // nodes in the above boolean array. when a vertex and its adjacent node have
        // both been unvisited, add it to the discoveredNodes map.
        for (int vertex = 0; vertex < g.nodeCount(); ++vertex) {
            // verify if this node has already been checked.
            if (!visited[vertex]) {
                visited[vertex] = true;
                queue.add(vertex);
                // Now check the adjacent nodes -> vertex
                while (!queue.isEmpty()) {
                    // get the next node to check and clear the list of adjacent nodes
                    uVal = queue.poll();
                    toVisit.clear();
                    // Get all the nodes adjacent to the uVal
                    toVisit.addAll(g.adjacent(uVal));
                    for (int node: toVisit) {
                        // check the list of nodes that can be visited to see if they have already been discovered.
                        // if not discovered and the edge exists, add it to the queue of possible nodes to be checked.
                        if (!discoveredNodes.containsKey(node)) {
                            queue.add(node);
                            discoveredNodes.put(node, vertex);
                        }
                    }
                }
            }
        }
        return discoveredNodes;
    }


    /**
     * Purpose: Determines if the given graph is bipartite by finding a
     *          2-coloring. Treats the graph as undirected, regardless of whether
     *          the graph is directed or not.
     * @param g the graph to check
     * @return true if the graph is bipartite and false otherwise
     */
    public static boolean bipartite(Graph g) {
        // TODO: fix Bipartite
        queue = new LinkedList<>();
        int[] coloring = new int[g.nodeCount()]; // -1 | 0 | 1
        List<Integer> adjacentNodes = new ArrayList<>(g.nodeCount());
        int uVal;
        Arrays.fill(coloring, -1); // fill with no coloring as -1
        coloring[0] = 1;
        queue.add(0); // always start with vertex 0
        for (int index = 0; index < g.nodeCount(); ++index) {
            if (g.hasEdge(index, index)) {
                // self loop, not a valid candidate for bipartite
                return false;
            }
        }
        // continue to use values in the queue like in BFS to search nodes for coloring.
        while (!queue.isEmpty()) {
            uVal = queue.poll();
            for (int vertex = 0; vertex < g.nodeCount(); ++vertex) {
                // loop through every node in the graph to find all non-colored nodes
                if (g.hasEdge(uVal, vertex) || g.hasEdge(vertex, uVal) && coloring[vertex] == -1) {
                    // vertex is not colored and there exists and edge
                    coloring[vertex] = 1 - coloring[uVal];
                    queue.add(vertex);
                } else if (g.hasEdge(uVal, vertex) && coloring[vertex] == coloring[uVal]) {
                    // An edge exists and the nodes are the same color. This is a non-bipartite graph
                    return false;
                }

            }

        }
        System.out.println();
        return true;
    }


    /**
     * Computes the depth first search of the given graph.
     * @param g the graph, either directed or undirected
     * @param src the starting node to search from
     * @return A search tree (node to parent node mapping)
     */
    public static Map<Integer,Integer> dfs(Graph g, int src) {
        List<Integer> toVisit = new ArrayList<>(g.nodeCount());
        stack = new Stack<>();
        discoveredNodes = new HashMap<>();
        int uVal;
        discoveredNodes.put(src, -1);
        stack.add(src);
        // the new nodes to check are stored in a stack so that once a node runs out of available out edges
        // the next node to be checked is the last node that was pushed onto the stack.
        while (!stack.isEmpty()) {
            // get the next node to check and clear the list of adjacent nodes
            uVal = stack.pop();
            toVisit.clear();
            // Get all the nodes adjacent to the uVal
            if (g.directed()) {
                toVisit.addAll(g.outNodes(uVal));
            } else {
                toVisit.addAll(g.adjacent(uVal));
            }
            for (int vertex: toVisit) {
                // check the list of nodes that can be visited to see if they have already been discovered.
                // if not discovered and the edge exists, add it to the stack of possible nodes to be checked.
                if (!discoveredNodes.containsKey(vertex)) {
                    stack.push(vertex);
                    discoveredNodes.put(vertex, uVal);
                }
            }
        }
        return discoveredNodes;
    }

    /**
     * Checks if a graph contains cycles.
     * @param g the graph, either directed or undirected
     * @return true if the graph is acyclic, false if it contains cycles
     */
    public static boolean acyclic(Graph g) {
        // TODO
        List<Integer> toVisit = new ArrayList<>(g.nodeCount());
        discoveredNodes = new HashMap<>(g.nodeCount());
        stack = new Stack<>();
        int uVal;
        int parent = -1;

//        if (g.directed()) {
            Set<Integer> white = new HashSet<>(g.nodeCount());
            Set<Integer> grey = new HashSet<>(g.nodeCount());
            Set<Integer> black = new HashSet<>(g.nodeCount());
            for (int node = 0; node < g.nodeCount(); ++node) {
                white.add(node);
            }
            while (white.size() > 0) {
                uVal = white.iterator().next();
                if (hasCycle(g, uVal, white, grey, black)) {
                    return false;
                }
            }
//        } else {
//            discoveredNodes.put(0, -1);
//            stack.push(0);
//            while (!stack.isEmpty()) {
//                // get the next node to check and clear the list of adjacent nodes
//                uVal = stack.pop();
//                System.out.println("uVal:    " + uVal);
//                System.out.println("parent:  " + parent);
//                toVisit.clear();
//                // Get all the nodes adjacent to the uVal
//                toVisit.addAll(g.adjacent(uVal));
//                for (int vertex: toVisit) {
//                    // check the list of nodes that can be visited to see if they have already been discovered.
//                    // if not discovered, add it to the stack of possible nodes to be checked.
//                    if (vertex != parent && !discoveredNodes.containsKey(vertex)) {
//                        System.out.println("PUSHING: " + vertex);
//                        stack.push(vertex);
//                        parent = uVal;
//                        discoveredNodes.put(vertex, uVal);
//                    } else {
//                        System.out.println("RETURNING: false");
//                        return false;
//                    }
//                }
//                // the parent will be checked every loop to mark which paths cannot be traversed
//                System.out.println();
//            }
//            // if no path exists return null.
//            System.out.println("RETURNING: false\n\n\n\n");
//        }
        return true;
    }


    private static boolean hasCycle(Graph g, int uVal, Set<Integer> white, Set<Integer> grey, Set<Integer> black) {
        List<Integer> toVisit = new ArrayList<>(g.nodeCount());
        // move node from white to grey
        white.remove(uVal);
        grey.add(uVal);
        // get all nodes adjacent to the uVal
        if (g.directed()) {
            toVisit.addAll(g.outNodes(uVal));
        } else {
            toVisit.addAll(g.adjacent(uVal));
        }

        for (int vertex: toVisit) {
            // if the current vertex is in the black list skip it.
            if (black.contains(vertex)) {
                System.out.println("Found a Black: " + uVal);
                continue;
            }
            if (grey.contains(vertex)) {
                System.out.println("Found a grey: " + uVal + " -> " + vertex);
                return true;
            }
            if (hasCycle(g, vertex, white, grey, black)) {
                return true;
            }
        }
        // move node from grey to black
        grey.remove(uVal);
        black.add(uVal);
        return false;
    }


    /**
     * Computes a topological sort over a directed graph.
     * @param g a directed, acyclic graph
     * @return the components for each node (node to component number
     * mapping)
     */
    public static Map<Integer,Integer> topologicalSort(Graph g) {
        // TODO
        return null;
    }


}
