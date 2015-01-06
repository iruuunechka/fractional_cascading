package cascading.selector;

import cascading.Entry;

import java.util.Comparator;

/**
 * Interface of selector for the next list (necessary for graphs)
 * @author Irene Petrova
 */
public interface Selector<T> {
    /**
     * Select the next list in the graph
     * @param x -- the searched item
     * @param cur -- the current entry
     * @param comparator -- {@link java.util.Comparator} for elements of type T
     * @return -- the index of the next list
     */
    public Integer selectNext(T x, Entry<T> cur, Comparator<T> comparator);
}
