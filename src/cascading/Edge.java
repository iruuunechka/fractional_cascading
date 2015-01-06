package cascading;

/**
 * Class for edge of the graph with bounds
 * @author Irene Petrova
 */
public class Edge {
    public final Integer end;
    public final Integer lowerBound;
    public final Integer higherBound;

    public Edge(Integer end, Integer lowerBound, Integer higherBound) {
        this.end = end;
        this.lowerBound = lowerBound;
        this.higherBound = higherBound;
    }
}