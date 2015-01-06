package cascading;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for answer. Answer consists of pairs: the number of the list and answer for this list
 * @author Irene Petrova
 */
public class Answer<T> {
    private final List<Integer> index;
    private final List<T> ans;

    public Answer() {
        index = new ArrayList<>();
        ans = new ArrayList<>();
    }

    public List<T> getAns() {
        return ans;
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<Integer> getIndex() {
        return index;
    }

    public void add(int index, T ans) {
        this.index.add(index);
        this.ans.add(ans);
    }
}
