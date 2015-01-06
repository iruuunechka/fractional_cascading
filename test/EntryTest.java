import org.junit.Ignore;
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
        Entry cascade = Entry.createCascade(data, p);
        Selector listSelector = new ListSelector();
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
        BoundedGraph graph = new BoundedGraph(data, edges, bounds);
        Selector selector = new GraphSelector(graph);
        Entry[] cascade = graph.createCascade();
        assertEquals(Arrays.asList(2, 1), graph.search(0, 0, cascade, selector).getAns());
    }

    @Ignore
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
        BoundedGraph graph = new BoundedGraph(data, edges, bounds);
        Selector selector = new GraphSelector(graph);
        Entry[] cascade = graph.createCascade();
        assertEquals(Arrays.asList(2), graph.search(0, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(6, 5), graph.search(5, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(null, 30), graph.search(20, 0, cascade, selector).getAns());
        assertEquals(Arrays.asList(9, 7), graph.search(6, 2, cascade, selector).getAns());
        assertEquals(Arrays.asList(11, 17), graph.search(10, 2, cascade, selector).getAns());
    }
}