package cascading.selector;

import cascading.Entry;

/**
 * Selector for lists
 * @author Irene Petrova
 */
public class ListSelector implements Selector {
    @Override
    public Integer selectNext(Integer x, Entry cur) {
        if (cur.parent == null)
            return null;
        return 0;
    }
}
