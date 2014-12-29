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
        assertEquals(Arrays.asList(-1, -1, 11, 12), cascade.search(11, listSelector).getAns());
        assertEquals(Arrays.asList(-1, -1, 11, 12), cascade.search(11, listSelector).getAns());
        assertEquals(Arrays.asList(4, 3, 8, 12), cascade.search(3, listSelector).getAns());
        assertEquals(Arrays.asList(7, -1, 8, 12), cascade.search(7, listSelector).getAns());
        assertEquals(Arrays.asList(-1, -1, -1, -1), cascade.search(100, listSelector).getAns());
    }
}