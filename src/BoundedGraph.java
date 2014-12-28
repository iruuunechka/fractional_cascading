import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private void dfs(int u, boolean[] colored, Entry[] cascade) {
        colored[u] = true;
        for (Edge e : rightOrder.get(u)) {
            if (!colored[e.end]) {
                dfs(e.end, colored, cascade);
            }
        }
     //   cascade[u] = new Entry();

    }
    private List<Entry> createCascade() {
        boolean[] colored = new boolean[values.size()];
        Arrays.fill(colored, false);
        Entry[] cascade = new Entry[values.size()];
        dfs(0, colored, cascade);
        return null;
    }

    private class Edge {
        private Integer end;
        private Integer lowerBound;
        private Integer higherBound;

        private Edge(Integer end, Integer lowerBound, Integer higherBound) {
            this.end = end;
            this.lowerBound = lowerBound;
            this.higherBound = higherBound;
        }
    }
}
