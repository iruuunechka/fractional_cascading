/**
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
