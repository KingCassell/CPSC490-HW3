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
        // TODO: still not passing all original tests, but the results appear correct.
        Set<Integer> white = new HashSet<>(g.nodeCount());
        Set<Integer> grey = new HashSet<>(g.nodeCount());
        Set<Integer> black = new HashSet<>(g.nodeCount());
        int uVal;
        // populate the white set with all the nodes in the graph
        for (int node = 0; node < g.nodeCount(); ++node) {
            white.add(node);
        }
        // check the graph for cycles until either a cycle is found or all the nodes have
        // been moved into the black set.
        while (white.size() > 0) {
            uVal = white.iterator().next();
            // use a recursive helper method to check for any cycles in the graph.
            if (hasCycle(g, uVal, white, grey, black)) {
                return false;
            }
        }
        return true;
    }


    /**
     * performs a DFS recursively to find any possible cycles in both undirected and directed graphs.
     * @param g the Graph to be checked
     * @param uVal teh current node in the graph to find children of.
     * @param white set of nodes that have not been used at all yet.
     * @param grey set of nodes currently under one parent. all nodes in a cluster.
     * @param black set of nodes that have had all their children visited.
     * @return boolean of if the graph contains a cycle.
     */
    private static boolean hasCycle(Graph g, int uVal, Set<Integer> white, Set<Integer> grey, Set<Integer> black) {
        List<Integer> toVisit = new ArrayList<>(g.nodeCount());
        // move node from white to grey
        white.remove(uVal);
        grey.add(uVal);
        // get all nodes adjacent to uVal
        if (g.directed()) {
            toVisit.addAll(g.outNodes(uVal));
        } else {
            toVisit.addAll(g.adjacent(uVal));
        }
        // check all the child nodes of uVal.
        for (int vertex: toVisit) {
            // check if vertex has had all its children visited.
            if (black.contains(vertex)) {
                continue;
            }
            // check if the vertex is a child of the same node cluster under the same parent.
            if (grey.contains(vertex)) {
                return true;
            }
            // recursively check the next vertex and its children.
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
        Deque<Integer> stack = new ArrayDeque<>(g.nodeCount());
        Map<Integer, Integer> sortedNodes = new HashMap<>(g.nodeCount());
        Set<Integer> discoveredNodes = new HashSet<>(g.nodeCount());
        int parent;
        // loop through every node and check if it can be sorted recursively using the helper.
        for (int vertex = 0; vertex < g.nodeCount(); ++vertex) {
            if (discoveredNodes.contains(vertex)) {
                continue;
            }
            topologicalSortHelper(g, vertex, stack, discoveredNodes);
        }
        // match up the node index with the spots that they would be ordered into.
        int i = 0;
        while (!stack.isEmpty()) {
            parent = stack.poll();
            sortedNodes.put(i, parent);
            ++i;
        }
        return sortedNodes;
    }


    /**
     * Recursive helper method for filling a stack with the sorted order of nodes in a graph
     * without losing their order of dependency.
     * @param g the graph to be sorted
     * @param vertex the current node that will have its children checked.
     * @param stack the stack of ordered nodes.
     * @param discoveredNodes the nodes that have been visited already.
     */
    private static void topologicalSortHelper(Graph g, int vertex, Deque<Integer> stack,
                                              Set<Integer> discoveredNodes) {
        List<Integer> toVisit = new ArrayList<>(g.nodeCount());
        // load a list of the adjacent nodes based on if the graph is directed
        if (g.directed()) {
            toVisit.addAll(g.outNodes(vertex));
        } else {
            toVisit.addAll(g.adjacent(vertex));
        }
        discoveredNodes.add(vertex);
        // recursively go through all the child nodes and check if they have already been visited.
        for (int child: toVisit) {
            if (discoveredNodes.contains(child)) {
                continue;
            }
            topologicalSortHelper(g, child, stack, discoveredNodes);
        }
        // load the node into the stack after all its children have been visited.
        stack.offerFirst(vertex);
    }
}
