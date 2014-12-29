/**
 * @author Irene Petrova
 */
public interface Selector {
    /**
     * Select the next list in the graph
     * @param x -- the searched item
     * @param cur -- the current entry
     * @return -- the index of the next list
     */
    public int selectNext(int x, Entry cur);
}
