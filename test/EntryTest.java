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
    public void graphCascading() {
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
        int p = 4;
        Entry[] cascade = graph.createCascade(p);
        assertEquals(Arrays.asList(2, 1, 8), graph.search(0, 0, cascade, selector).getAns());
    }
}