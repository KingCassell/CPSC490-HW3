
import java.util.*;


public class GraphAlgorithms {


    // NOTE: This file is included just so you have the signatures for
    // the three functions you are implementing. You should add the
    // functions to your HW2 GraphAlgorithms.java file and replace this
    // file with yours (with the signatures added).

    // SEE: tests/DFSTest.java for additional instructions.

    private static Queue<Integer> queue;
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
        Stack<Integer> stack = new Stack<>();
        // add the list of discovered nodes after performing a Breadth first search.
        discoveredNodes = bfs(g, src);
        int parent = 0;
        int child = dst;
        int index = 0;
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
        // TODO
        return null;
    }

    /**
     * Checks if a graph contains cycles.
     * @param g the graph, either directed or undirected
     * @return true if the graph is acyclic, false if it contains cycles
     */
    public static boolean acyclic(Graph g) {
        // TODO
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
