import java.util.*;

/**
 * @author Irene Petrova
 */
public class BoundedGraph {
    private List<Map<Integer, Edge>> rightOrder;
    private List<Map<Integer, Edge>> inversedOrder;
    private List<List<Integer>> values;

    public BoundedGraph(List<List<Integer>> values, List<List<Integer>> edges, List<List<Integer>> bounds) {
        if (edges.size() != bounds.size()) {
            throw new AssertionError("Sizes of edges and bound must be equal");
        }
        this.values = values;
        rightOrder = new ArrayList<>();
        inversedOrder = new ArrayList<>();
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < values.size(); ++i) {
            rightOrder.add(new HashMap<Integer, Edge>());
            inversedOrder.add(new HashMap<Integer, Edge>());
        }
        for (int i = 0; i < edges.size(); ++i) {
            int end = edges.get(i).get(1);
            Edge newRightEdge = new Edge(end, bounds.get(i).get(0), bounds.get(i).get(1));
            rightOrder.get(edges.get(i).get(0)).put(end, newRightEdge);
            end = edges.get(i).get(0);
            Edge newInversedEdge = new Edge(end, bounds.get(i).get(0), bounds.get(i).get(1));
            inversedOrder.get(edges.get(i).get(1)).put(end, newInversedEdge);
        }
    }

    private void dfs(int u, boolean[] colored, Entry[] cascade) {
        colored[u] = true;
        for (Edge e : rightOrder.get(u).values()) {
            if (!colored[e.end]) {
                dfs(e.end, colored, cascade);
            }
        }
        if (rightOrder.get(u).isEmpty()) {
            cascade[u] = new Entry(values.get(u), getP(u), u);
        } else {
            List<Entry> parents = new ArrayList<>();
            for (Edge e : rightOrder.get(u).values()) {
                parents.add(cascade[e.end]);
            }
            cascade[u] = new Entry(values.get(u), getP(u), parents, u);
        }

    }

    private int getP(int u) {
        return inversedOrder.get(u).size() * 2;
    }

    public Entry[] createCascade() {
        boolean[] colored = new boolean[values.size()];
        Arrays.fill(colored, false);
        Entry[] cascade = new Entry[values.size()];
        dfs(0, colored, cascade);
        for (int i = 0; i < colored.length; ++i) {
            if (!colored[i]) {
                dfs(i, colored, cascade);
            }
        }
        return cascade;
    }

    public Edge getInversedEdge(int curIndex, int curParentIndex) {
        return inversedOrder.get(curIndex).get(curParentIndex);
    }

    public Edge getEdge(int curIndex, int parentIndex) {
        return rightOrder.get(curIndex).get(parentIndex);
    }

    public Answer search(int x, int start, Entry[] cascade, Selector selector) {
        Entry startEntry = cascade[start];
        return startEntry.search(x, selector);
    }


}
