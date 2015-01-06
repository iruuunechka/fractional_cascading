import cascading.BoundedGraph;
import cascading.Entry;
import cascading.selector.GraphSelector;
import cascading.selector.ListSelector;
import cascading.selector.Selector;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EntryTest {
    @Test
    public void listCascading() {
        List<List<Integer>> data = Arrays.asList(
                Arrays.asList(2, 4, 7, 9, 10),
                Arrays.asList(1, 3, 5, 6),
                Arrays.asList(8, 11, 18, 25, 30),
                Arrays.asList(12, 13, 15, 17, 20, 27)
        );
        int p = 2;
        Entry<Integer> cascade = Entry.createCascade(data, p, Integer::compare);
        Selector<Integer> listSelector = new ListSelector<>();
        assertEquals(Arrays.asList(2, 1, 8, 12), cascade.search(0, listSelector).getAns());
        assertEquals(Arrays.asList(null, null, 11, 12), cascade.search(11, listSelector).getAns());
        assertEquals(Arrays.asList(null, null, 11, 12), cascade.search(11, listSelector).getAns());
        assertEquals(Arrays.asList(4, 3, 8, 12), cascade.search(3, listSelector).getAns());
        assertEquals(Arrays.asList(7, null, 8, 12), cascade.search(7, listSelector).getAns());
        assertEquals(Arrays.<Integer>asList(null, null, null, null), cascade.search(100, listSelector).getAns());
    }

    @Test
    public void graphCascading1() {
        List<List<Integer>> data = Arrays.asList(
                Arrays.asList(2, 4, 7, 9, 10),
                Arrays.asList(1, 3, 5, 6),
                Arrays.asList(8, 11, 18, 25, 30),
                Arrays.asList(12, 13, 15, 17, 20, 27)
        );
        List<List<Integer>> edges = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 2),
                Arrays.asList(1, 3)
        );
        List<List<Integer>> bounds = Arrays.asList(
                Arrays.asList(1, 27),
                Arrays.asList(8, 30),
                Arrays.asList(12, 27)
        );
        BoundedGraph<Integer> graph = new BoundedGraph<>(data, edges, bounds, Integer::compare);
        Selector<Integer> selector = new GraphSelector<>(graph);
        Entry<Integer>[] cascade = graph.createCascade();
        assertEquals(Arrays.asList(2, 1), graph.search(0, 0, cascade, selector).getAns());
    }

    @Test
    public void graphCascading2() {
        List<List<Integer>> data = Arrays.asList(
                Arrays.asList(2, 6, 9, 11, 12),
                Arrays.asList(3, 5, 8, 17),
                Arrays.asList(1, 9, 11),
                Arrays.asList(4, 5, 7, 8, 13, 14),
                Arrays.asList(9, 11, 13, 19, 30)
        );
        List<List<Integer>> edges = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(0, 4),
                Arrays.asList(2, 1),
                Arrays.asList(2, 3),
                Arrays.asList(3, 4)
        );
        List<List<Integer>> bounds = Arrays.asList(
                Arrays.asList(3, 17),
                Arrays.asList(9, 30),
                Arrays.asList(3, 17),
                Arrays.asList(4, 14),
                Arrays.asList(9, 30)
        );
        BoundedGraph<Integer> graph = new BoundedGraph<>(data, edges, bounds, Integer::compare);
        Selector<Integer> selector = new GraphSelector<>(graph);
        Entry<Integer>[] cascade = graph.createCascade();
        assertEquals(Arrays.asList(2), graph.search(0, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(6, 5), graph.search(5, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(null, 30), graph.search(20, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(9, 7), graph.search(6, 2, cascade, selector).getAns());
        assertEquals(Arrays.asList(11, 17), graph.search(10, 2, cascade, selector).getAns());
    }

    @Test
    public void graphCascading3() {
        List<List<Double>> data = Arrays.asList(
                Arrays.asList(0.0, 7.0, 10.0),
                Arrays.asList(0.3, 1.4, 6.1),
                Arrays.asList(1.0, 2.3, 4.2),
                Arrays.asList(0.1, 1.7, 3.8, 4.3),
                Arrays.asList(4.0, 9.5, 13.0),
                Arrays.asList(6.2, 7.3, 8.1),
                Arrays.asList(0.2, 1.6, 8.6)
        );
        List<List<Integer>> edges = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(0, 3),
                Arrays.asList(1, 2),
                Arrays.asList(3, 2),
                Arrays.asList(4, 3),
                Arrays.asList(4, 5),
                Arrays.asList(3, 6),
                Arrays.asList(5, 6)
        );

        List<List<Double>> bounds = Arrays.asList(
                Arrays.asList(0.3, 6.1),
                Arrays.asList(0.1, 4.3),
                Arrays.asList(2.0, 3.0),
                Arrays.asList(3.0, 4.0),
                Arrays.asList(0.1, 4.3),
                Arrays.asList(0.0, 9.0),
                Arrays.asList(1.0, 3.0),
                Arrays.asList(0.0, 9.0)
        );
        BoundedGraph<Double> graph = new BoundedGraph<>(data, edges, bounds, Double::compare);
        Selector<Double> selector = new GraphSelector<>(graph);
        Entry<Double>[] cascade = graph.createCascade();
        assertEquals(Arrays.asList(0.0), graph.search(0.0, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(7.0, 6.1), graph.search(4.0, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(10.0), graph.search(9.5, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(3.8, 8.6), graph.search(2.3, 3, cascade, selector).getAns());
        assertEquals(Arrays.asList(9.5, null, null), graph.search(9.0, 4, cascade, selector).getAns());
        assertEquals(Arrays.asList(9.5, 8.1, 8.6), graph.search(8.0, 4, cascade, selector).getAns());
    }
}