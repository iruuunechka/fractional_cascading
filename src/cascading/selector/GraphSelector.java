package cascading.selector;

import cascading.Edge;
import cascading.Entry;
import cascading.BoundedGraph;

/**
 * Selector for graph with bounds
 * @author Irene Petrova
 */
public class GraphSelector implements Selector {

    private final BoundedGraph graph;

    public GraphSelector(BoundedGraph graph) {
        this.graph = graph;
    }

    @Override
    public Integer selectNext(Integer x, Entry cur) {
        int curIndex = cur.index;
        for (int i = 0; i < cur.parent.size(); ++i) {
            int parentIndex = cur.parent.get(i).index;
            Edge e = graph.getInversedEdge(parentIndex, curIndex);
            if (x <= e.higherBound && x >= e.lowerBound) {
                return i;
            }
        }
        return null;
    }
}
