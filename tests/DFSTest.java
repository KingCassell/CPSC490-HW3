/*
 * File: DFSTest.java
 * Date: Spring 2022
 * Auth: 
 * Desc: 
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
    Graph<Integer> g = new AdjacencyList<>(4, false);
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
