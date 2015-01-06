package cascading.selector;

import cascading.Edge;
import cascading.Entry;
import cascading.BoundedGraph;

import java.util.Comparator;

/**
 * Selector for graph with bounds
 * @author Irene Petrova
 */
public class GraphSelector<T> implements Selector<T> {

    private final BoundedGraph<T> graph;

    public GraphSelector(BoundedGraph<T> graph) {
        this.graph = graph;
    }

    @Override
    public Integer selectNext(T x, Entry<T> cur, Comparator<T> comparator) {
        int curIndex = cur.index;
        for (int i = 0; i < cur.parent.size(); ++i) {
            int parentIndex = cur.parent.get(i).index;
            Edge<T> e = graph.getInversedEdge(parentIndex, curIndex);
            if (comparator.compare(x, e.higherBound) <= 0 && comparator.compare(x, e.lowerBound) >= 0) {
                return i;
            }
        }
        return null;
    }
}
