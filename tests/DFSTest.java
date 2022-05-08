/*
 * File: DFSTest.java
 * Date: Spring 2022
 * Auth: Dustin Cassell
 * Desc: General Tests for the Depth First Search related Methods
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;
import java.util.List;


/* TODO: You must do the following steps:

   (1). For DFS, you must add one new test for directed and one new
   test for undirected graphs. Your tests must contain a significantly
   more complex graph than provided below and must test the search
   from different nodes within the graph. You should spend time
   considering what good tests would be for each case (directed vs
   undirected).

   (2). For cycle checking, you must again add two additional tests,
   one for a directed graph and one for an undirected graph. Your
   graphs must be significantly more complex than those given.

   (3). For topological sorting, you must create two additional
   tests. Your graphs must be significantly more complex (and
   preferrably be "interesting"). 

   (4). Once you finish the tests, run and plot the performance tests: 
      
        bazel-bin/hw3 > output.dat
        gnuplot -c plot_script.gp

   (5). You must create a write up that contains: (a) a drawing of
   each graph you tested with (and which test the graph was usef for);
   and (b) an explanation of what tests you did with respect to the
   corresponding graphs. In addition, include your BFS (HW-2) and DFS
   (HW-3) performance graphs and describe how they differ (if at all),
   and your general observations concerning the graphs and the
   performance of the algorithms.


*/



public class DFSTest {


    //--------------------------------------------------------------------
    // My Tests
    //--------------------------------------------------------------------


    @Test
    public void complexDirectedDFS() throws Exception {
        // used graph_A_Directed
        Graph<Integer> g_a_d = new AdjacencyList<>(10, true);
        g_a_d.add(0, null, 1);
        g_a_d.add(1, null, 0);
        g_a_d.add(1, null, 2);
        g_a_d.add(1, null, 3);
        g_a_d.add(1, null, 5);
        g_a_d.add(5, null, 4);
        g_a_d.add(5, null, 8);
        g_a_d.add(5, null, 9);
        g_a_d.add(7, null, 2);
        g_a_d.add(7, null, 6);
        g_a_d.add(9, null, 3);
        g_a_d.add(9, null, 4);
        g_a_d.add(9, null, 7);
        // bfs from 0
        Map<Integer,Integer> tree = GraphAlgorithms.dfs(g_a_d, 0);
        assertEquals(10, tree.size());
        assertTrue(-1 == tree.get(0));
        assertTrue(0 == tree.get(1));
        assertTrue(1 == tree.get(2));
        assertTrue(1 == tree.get(3));
        assertTrue(5 == tree.get(4));
        assertTrue(1 == tree.get(5));
        assertTrue(7 == tree.get(6));
        assertTrue(9 == tree.get(7));
        assertTrue(5 == tree.get(8));
        assertTrue(5 == tree.get(9));
    }


    @Test
    public void complexUndirectedDFS() throws Exception {
        // used Graph_A_Undirected
        Graph<Integer> g_a_u = new AdjacencyList<>(10, false);
        g_a_u.add(0, null, 1);
        g_a_u.add(1, null, 2);
        g_a_u.add(1, null, 3);
        g_a_u.add(1, null, 5);
        g_a_u.add(5, null, 4);
        g_a_u.add(5, null, 8);
        g_a_u.add(5, null, 9);
        g_a_u.add(7, null, 2);
        g_a_u.add(7, null, 6);
        g_a_u.add(9, null, 3);
        g_a_u.add(9, null, 4);
        g_a_u.add(9, null, 7);
        // bfs from 0
        Map<Integer,Integer> tree = GraphAlgorithms.dfs(g_a_u, 0);
        assertEquals(10, tree.size());
        assertTrue(-1 == tree.get(0));
        assertTrue(0 == tree.get(1));
        assertTrue(1 == tree.get(2));
        assertTrue(1 == tree.get(3));
        assertTrue(5 == tree.get(4));
        assertTrue(1 == tree.get(5));
        assertTrue(7 == tree.get(6));
        assertTrue(9 == tree.get(7));
        assertTrue(5 == tree.get(8));
        assertTrue(5 == tree.get(9));

        tree = GraphAlgorithms.dfs(g_a_u, 5);
        assertEquals(10, tree.size());
        assertTrue(1 == tree.get(0));
        assertTrue(5 == tree.get(1));
        assertTrue(7 == tree.get(2));
        assertTrue(9 == tree.get(3));
        assertTrue(5 == tree.get(4));
        assertTrue(-1 == tree.get(5));
        assertTrue(7 == tree.get(6));
        assertTrue(9 == tree.get(7));
        assertTrue(5 == tree.get(8));
        assertTrue(5 == tree.get(9));
    }


    @Test
    public void complexUndirectedAcyclic() throws Exception {
        // used Graph_A_Undirected
        Graph<Integer> g_a_u = new AdjacencyList<>(10, false);
        g_a_u.add(0, null, 1);
        g_a_u.add(1, null, 2);
        g_a_u.add(1, null, 3);
        g_a_u.add(1, null, 5);
        g_a_u.add(5, null, 4);
        g_a_u.add(5, null, 8);
        g_a_u.add(5, null, 9);
        g_a_u.add(7, null, 2);
        g_a_u.add(7, null, 6);
        g_a_u.add(9, null, 3);
        g_a_u.add(9, null, 4);
        g_a_u.add(9, null, 7);
        assertFalse(GraphAlgorithms.acyclic(g_a_u));
    }


    @Test
    public void complexDirectedAcyclic() throws Exception {
        // used graph_A_Directed
        Graph<Integer> g_a_d = new AdjacencyList<>(10, true);
        g_a_d.add(0, null, 1);
        g_a_d.add(1, null, 0);
        g_a_d.add(1, null, 2);
        g_a_d.add(1, null, 3);
        g_a_d.add(1, null, 5);
        g_a_d.add(5, null, 4);
        g_a_d.add(5, null, 8);
        g_a_d.add(5, null, 9);
        g_a_d.add(7, null, 2);
        g_a_d.add(7, null, 6);
        g_a_d.add(9, null, 3);
        g_a_d.add(9, null, 4);
        g_a_d.add(9, null, 7);
        assertFalse(GraphAlgorithms.acyclic(g_a_d));
        g_a_d.remove(1, 0);
        assertTrue(GraphAlgorithms.acyclic(g_a_d));
        g_a_d.add(8, null, 1);
        assertFalse(GraphAlgorithms.acyclic(g_a_d));

        // used Graph_B_Directed
        Graph<Integer> g_b_d = new AdjacencyList<>(10, true);
        g_b_d.add(0, null, 1);
        g_b_d.add(1, null, 0);
        g_b_d.add(1, null, 2);
        g_b_d.add(2, null, 6);
        g_b_d.add(4, null, 5);
        g_b_d.add(5, null, 4);
        g_b_d.add(5, null, 8);
        g_b_d.add(5, null, 9);
        g_b_d.add(6, null, 7);
        g_b_d.add(7, null, 2);
        g_b_d.add(7, null, 6);
        g_b_d.add(9, null, 3);
        g_b_d.add(9, null, 4);
        assertFalse(GraphAlgorithms.acyclic(g_a_d));
    }


    @Test
    public void ComplexTopologicalSort() {
        // used Graph_C_Directed_Bipartite
        Graph<Integer> g_c_d_bipart = new AdjacencyList<>(10, true);
        g_c_d_bipart.add(0, null, 1);
        g_c_d_bipart.add(1, null, 0);
        g_c_d_bipart.add(1, null, 2);
        g_c_d_bipart.add(1, null, 3);
        g_c_d_bipart.add(1, null, 5);
        g_c_d_bipart.add(5, null, 4);
        g_c_d_bipart.add(4, null, 5);
        g_c_d_bipart.add(5, null, 8);
        g_c_d_bipart.add(6, null, 7);
        g_c_d_bipart.add(7, null, 2);
        g_c_d_bipart.add(7, null, 6);
        g_c_d_bipart.add(9, null, 4);
        g_c_d_bipart.add(9, null, 7);
        Map<Integer, Integer> ordering = GraphAlgorithms.topologicalSort(g_c_d_bipart);
        assertEquals(10, ordering.size());
        assertTrue(ordering.get(3) < ordering.get(5));
        assertTrue(ordering.get(2) < ordering.get(6));
        assertTrue(ordering.get(7) < ordering.get(0));
        assertTrue(ordering.get(8) < ordering.get(1));
        assertTrue(ordering.get(9) < ordering.get(2));
    }


    @Test
    public void ComplexTopologicalSortInteresting() {
        // used Graph_C_Undirected_Bipartite
        Graph<Integer> g_c_u_bipart = new AdjacencyList<>(10, false);
        g_c_u_bipart.add(0, null, 1);
        g_c_u_bipart.add(1, null, 2);
        g_c_u_bipart.add(1, null, 3);
        g_c_u_bipart.add(1, null, 5);
        g_c_u_bipart.add(5, null, 4);
        g_c_u_bipart.add(5, null, 8);
        g_c_u_bipart.add(6, null, 7);
        g_c_u_bipart.add(7, null, 2);
        g_c_u_bipart.add(9, null, 4);
        g_c_u_bipart.add(9, null, 7);
        Map<Integer, Integer> ordering = GraphAlgorithms.topologicalSort(g_c_u_bipart);
        // the ordering works out to allow for convenient equality below
        assertTrue(ordering.get(0) < ordering.get(9));
        assertTrue(ordering.get(1) < ordering.get(8));
        assertTrue(ordering.get(2) < ordering.get(7));
        assertTrue(ordering.get(3) < ordering.get(6));
        assertTrue(ordering.get(4) < ordering.get(5));
    }


    //--------------------------------------------------------------------
    // Directed Graph Tests
    //--------------------------------------------------------------------

    @Test
    public void basicDirectedAllReachableDFS() throws Exception {
        Graph<Integer> g = new AdjacencyList<>(4, true);
        g.add(0, null, 1);
        g.add(0, null, 2);
        g.add(2, null, 3);
        g.add(1, null, 0);
        // dfs from 0
        Map<Integer,Integer> tree = GraphAlgorithms.dfs(g, 0);
        assertEquals(4, tree.size());
        assertTrue(-1 == tree.get(0));
        assertTrue(0 == tree.get(1));
        assertTrue(0 == tree.get(2));
        assertTrue(2 == tree.get(3));
        // dfs from 1
        tree = GraphAlgorithms.dfs(g, 1);
        assertEquals(4, tree.size());
        assertTrue(-1 == tree.get(1));
        assertTrue(1 == tree.get(0));
        assertTrue(0 == tree.get(2));
        assertTrue(2 == tree.get(3));
    }

    @Test
    public void basicDirectedSomeReachableDFS() {
        Graph<Integer> g = new AdjacencyList<>(5, true);
        g.add(0, null, 1);
        g.add(0, null, 3);
        g.add(1, null, 2);
        g.add(1, null, 4);
        g.add(3, null, 1);
        g.add(3, null, 4);
        g.add(4, null, 2);
        // dfs from 3
        Map<Integer,Integer> tree = GraphAlgorithms.dfs(g, 3);
        assertEquals(4, tree.size());
        assertTrue(-1 == tree.get(3));
        assertTrue(3 == tree.get(1));
        assertTrue(3 == tree.get(4));
        assertTrue(1 == tree.get(2) || 4 == tree.get(2));
    }

    @Test
    public void basicDirectedNoneReachableDFS() {
        Graph<Integer> g = new AdjacencyList<>(5, true);
        g.add(0, null, 1);
        g.add(0, null, 3);
        g.add(1, null, 2);
        g.add(1, null, 4);
        g.add(3, null, 1);
        g.add(3, null, 4);
        g.add(4, null, 2);
        // dfs from 0
        Map<Integer,Integer> tree = GraphAlgorithms.dfs(g, 2);
        assertEquals(1, tree.size());
        assertTrue(-1 == tree.get(2));
    }

    @Test
    public void basicUndirectedAllReachableDFS() {
        Graph<Integer> g1 = new AdjacencyList<>(5, false);
        g1.add(0, null, 1);
        g1.add(0, null, 2);
        g1.add(1, null, 4);
        g1.add(1, null, 3);
        g1.add(3, null, 4);
        // dfs from 0
        Map<Integer,Integer> tree = GraphAlgorithms.dfs(g1, 0);
        assertEquals(5, tree.size());
        assertTrue(-1 == tree.get(0));
        assertTrue(0 == tree.get(2));
        assertTrue(0 == tree.get(1));
        assertTrue(1 == tree.get(3) || 4 == tree.get(3));
        if (1 == tree.get(3))
            assertTrue(3 == tree.get(4));
        else
            assertTrue(1 == tree.get(4));
        // same graph, order of edges in add inversed
        Graph<Integer> g2 = new AdjacencyList<>(5, false);
        g2.add(1, null, 0);
        g2.add(2, null, 0);
        g2.add(4, null, 1);
        g2.add(3, null, 1);
        g2.add(4, null, 3);
        // dfs from 0
        tree = GraphAlgorithms.dfs(g2, 0);
        assertEquals(5, tree.size());
        assertTrue(-1 == tree.get(0));
        assertTrue(0 == tree.get(2));
        assertTrue(0 == tree.get(1));
        assertTrue(1 == tree.get(3) || 4 == tree.get(3));
        if (1 == tree.get(3))
            assertTrue(3 == tree.get(4));
        else
            assertTrue(1 == tree.get(4));
    }

    @Test
    public void basicUndirectedSomeReachableDFS() {
        Graph<Integer> g = new AdjacencyList<>(4, false);
        g.add(0, null, 1);
        g.add(2, null, 3);
        // dfs from 0
        Map<Integer,Integer> tree = GraphAlgorithms.dfs(g, 0);
        assertEquals(2, tree.size());
        assertTrue(-1 == tree.get(0));
        assertTrue(0 == tree.get(1));
        // dfs from 3
        tree = GraphAlgorithms.dfs(g, 3);
        assertEquals(2, tree.size());
        assertTrue(-1 == tree.get(3));
        assertTrue(3 == tree.get(2));
    }

    @Test
    public void basicUndirectedDFS() {
        Graph<Integer> g = new AdjacencyList<>(4, false);
        g.add(0, null, 1);
        g.add(0, null, 3);
        g.add(1, null, 2);
        assertTrue(GraphAlgorithms.acyclic(g));
        g.add(3, -1, 2);
        assertFalse(GraphAlgorithms.acyclic(g));
    }

    @Test
    public void basicDirectedCycleCheck() {
        Graph<Integer> g = new AdjacencyList<>(4, true);
        g.add(0, null, 1);
        g.add(1, null, 2);
        g.add(0, null, 3);
        assertTrue(GraphAlgorithms.acyclic(g));
        g.add(2, 0, 0);
        assertFalse(GraphAlgorithms.acyclic(g));
    }

    @Test
    public void undirectedDisconnectedCycleCheck() {
        Graph<Integer> g = new AdjacencyList<>(6, false);
        g.add(0, null, 1);
        g.add(0, null, 2);
        g.add(1, null, 2);
        assertFalse(GraphAlgorithms.acyclic(g));
        g.add(3, null, 4);
        g.add(4, null, 5);
        g.add(5, null, 3);
        assertFalse(GraphAlgorithms.acyclic(g));
    }

    @Test
    public void directedDisconnectedCycleCheck() {
        Graph<Integer> g = new AdjacencyList<>(6, true);
        g.add(0, null, 1);
        g.add(0, null, 2);
        g.add(1, null, 2);
        assertTrue(GraphAlgorithms.acyclic(g));
        g.add(3, null, 4);
        g.add(4, null, 5);
        g.add(5, null, 3);
        assertFalse(GraphAlgorithms.acyclic(g));
    }


    @Test
    public void basicDFSTopologicalSort() {
        Graph<Integer> g1 = new AdjacencyList<>(5, true);
        g1.add(0, null, 2);
        g1.add(1, null, 2);
        g1.add(2, null, 3);
        g1.add(2, null, 4);
        g1.add(3, null, 4);
        Map<Integer,Integer> ordering = GraphAlgorithms.topologicalSort(g1);
        assertEquals(5, ordering.size());
        assertTrue(ordering.get(3) < ordering.get(4));
        assertTrue(ordering.get(2) < ordering.get(3));
        assertTrue(ordering.get(1) < ordering.get(2));
        assertTrue(ordering.get(0) < ordering.get(2));
        Graph<Integer> g2 = new AdjacencyList<>(5, true);
        g2.add(0, null, 2);
        g2.add(1, null, 2);
        g2.add(2, null, 3);
        g2.add(2, null, 4);
        g2.add(4, null, 3);
        ordering = GraphAlgorithms.topologicalSort(g2);
        assertTrue(ordering.get(4) < ordering.get(3));
        assertTrue(ordering.get(2) < ordering.get(3));
        assertTrue(ordering.get(1) < ordering.get(2));
        assertTrue(ordering.get(0) < ordering.get(2));
    }



}
