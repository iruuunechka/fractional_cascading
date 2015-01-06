package cascading;

/**
 * Class for edge of the graph with bounds
 * @author Irene Petrova
 */
public class Edge<T> {
    public final Integer end;
    public final T lowerBound;
    public final T higherBound;

    public Edge(Integer end, T lowerBound, T higherBound) {
        this.end = end;
        this.lowerBound = lowerBound;
        this.higherBound = higherBound;
    }
}