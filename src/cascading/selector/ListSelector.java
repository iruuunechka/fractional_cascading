package cascading.selector;

import cascading.Entry;

import java.util.Comparator;

/**
 * Selector for lists
 * @author Irene Petrova
 */
public class ListSelector<T> implements Selector<T> {
    @Override
    public Integer selectNext(T x, Entry<T> cur, Comparator<T> comparator) {
        if (cur.parent == null)
            return null;
        return 0;
    }
}
