import java.util.ArrayList;
import java.util.List;

/**
 * @author Irene Petrova
 */
public class GraphSelector implements Selector {

    private final BoundedGraph graph;

    public GraphSelector(BoundedGraph graph) {
        this.graph = graph;
    }

    @Override
    public int selectNext(int x, Entry cur) {
        int curIndex = cur.index;
        List<Integer> parentCandidates = new ArrayList<>();
        for (int i = 0; i < cur.parent.size(); ++i) {
            int parentIndex = cur.parent.get(i).index;
            Edge e = graph.getInversedEdge(curIndex, parentIndex);
            if (x <= e.higherBound && x >= e.lowerBound) {
                parentCandidates.add(i);
            }
        }
        return parentCandidates.get(0);
    }
}
