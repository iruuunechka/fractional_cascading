import java.util.*;

/**
 * @author Irene Petrova
 */
public class BoundedGraph {
    private List<List<Edge>> rightOrder;
    private List<List<Edge>> inversedOrder;
    private List<List<Integer>> values;

    public BoundedGraph(List<List<Integer>> values, List<List<Integer>> edges, List<List<Integer>> bounds) {
        if (edges.size() != bounds.size()) {
            throw new AssertionError("Sizes of edges and bound must be equal");
        }
        this.values = values;
        rightOrder = new ArrayList<>();
        inversedOrder = new ArrayList<>();
        for (int i = 0; i < values.size(); ++i) {
            rightOrder.add(new ArrayList<Edge>());
            inversedOrder.add(new ArrayList<Edge>());
        }
        for (int i = 0; i < edges.size(); ++i) {
            Edge newRightEdge = new Edge(edges.get(i).get(1), bounds.get(i).get(0), bounds.get(i).get(1));
            rightOrder.get(edges.get(i).get(0)).add(newRightEdge);
            Edge newInversedEdge = new Edge(edges.get(i).get(0), bounds.get(i).get(0), bounds.get(i).get(1));
            inversedOrder.get(edges.get(i).get(1)).add(newInversedEdge);
        }
    }

    private void dfs(int u, boolean[] colored, Entry[] cascade, int p) {
        colored[u] = true;
        for (Edge e : rightOrder.get(u)) {
            if (!colored[e.end]) {
                dfs(e.end, colored, cascade, p);
            }
        }
        if (inversedOrder.get(u).isEmpty()) {
            cascade[u] = new Entry(values.get(u), p, u);
        } else {
            List<Entry> parents = new ArrayList<>();
            for (Edge e : inversedOrder.get(u)) {
                parents.add(cascade[e.end]);
            }
            cascade[u] = new Entry(values.get(u), p, parents, u);
        }

    }
    private Entry[] createCascade(int p) {
        boolean[] colored = new boolean[values.size()];
        Arrays.fill(colored, false);
        Entry[] cascade = new Entry[values.size()];
        dfs(0, colored, cascade, p);
        return cascade;
    }

    public Edge getInversedEdge(int curIndex, int curParentIndex) {
        return inversedOrder.get(curIndex).get(curParentIndex);
    }

    public Answer search(int x, int start, Entry[] cascade, Selector selector) {
        Entry startEntry = cascade[start];
        return startEntry.search(x, selector);
    }


}
