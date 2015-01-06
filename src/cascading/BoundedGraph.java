package cascading;

import cascading.selector.Selector;

import java.util.*;

/**
 * Class for a graph with bounds
 * @author Irene Petrova
 */
public class BoundedGraph<T> {
    private List<Map<Integer, Edge<T>>> rightOrder;  //прямые ребра
    private List<Map<Integer, Edge<T>>> inversedOrder;   //инвертированные ребра
    private List<List<T>> values;   //списки значений
    private Comparator<T> comparator;

    /**
     * Constructor for {@link cascading.BoundedGraph}
     * @param values -- lists of values in vertices
     * @param edges -- edges of the graph
     * @param bounds -- bounds for edges
     */
    public BoundedGraph(List<List<T>> values, List<List<Integer>> edges, List<List<T>> bounds, Comparator<T> comparator) {
        this.comparator = comparator;
        if (edges.size() != bounds.size()) {
            throw new AssertionError("Sizes of edges and bound must be equal");
        }
        this.values = values;
        rightOrder = new ArrayList<>();
        inversedOrder = new ArrayList<>();
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < values.size(); ++i) {
            rightOrder.add(new HashMap<>());
            inversedOrder.add(new HashMap<>());
        }
        for (int i = 0; i < edges.size(); ++i) {
            int end = edges.get(i).get(1);
            Edge<T> newRightEdge = new Edge<>(end, bounds.get(i).get(0), bounds.get(i).get(1));
            rightOrder.get(edges.get(i).get(0)).put(end, newRightEdge);
            end = edges.get(i).get(0);
            Edge<T> newInversedEdge = new Edge<>(end, bounds.get(i).get(0), bounds.get(i).get(1));
            inversedOrder.get(edges.get(i).get(1)).put(end, newInversedEdge);
        }
    }

    public Edge<T> getInversedEdge(int curIndex, int curParentIndex) {
        return inversedOrder.get(curIndex).get(curParentIndex);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Edge<T> getEdge(int curIndex, int parentIndex) {
        return rightOrder.get(curIndex).get(parentIndex);
    }

    private int getP(int u) {
        return inversedOrder.get(u).size() * 2;
    }

    private void dfs(int u, boolean[] colored, Entry<T>[] cascade) {
        colored[u] = true;
        for (Edge e : rightOrder.get(u).values()) {
            if (!colored[e.end]) {
                dfs(e.end, colored, cascade);
            }
        }
        if (rightOrder.get(u).isEmpty()) {
            cascade[u] = new Entry<>(values.get(u), getP(u), u, comparator);
        } else {
            List<Entry<T>> parents = new ArrayList<>();
            for (Edge e : rightOrder.get(u).values()) {
                parents.add(cascade[e.end]);
            }
            cascade[u] = new Entry<>(values.get(u), getP(u), parents, u, comparator);
        }

    }

    public Entry<T>[] createCascade() {
        boolean[] colored = new boolean[values.size()];
        Arrays.fill(colored, false);
        //noinspection unchecked
        Entry<T>[] cascade = new Entry[values.size()];
        dfs(0, colored, cascade);
        for (int i = 0; i < colored.length; ++i) {
            if (!colored[i]) {
                dfs(i, colored, cascade);
            }
        }
        return cascade;
    }

    public Answer search(T x, int start, Entry<T>[] cascade, Selector<T> selector) {
        Entry<T> startEntry = cascade[start];
        return startEntry.search(x, selector);
    }
}
