package cascading;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for answer. Answer consists of pairs: the number of the list and answer for this list
 * @author Irene Petrova
 */
public class Answer {
    private final List<Integer> index;
    private final List<Integer> ans;

    public Answer() {
        index = new ArrayList<>();
        ans = new ArrayList<>();
    }

    public List<Integer> getAns() {
        return ans;
    }

    public List<Integer> getIndex() {
        return index;
    }

    public void add(int index, Integer ans) {
        this.index.add(index);
        this.ans.add(ans);
    }
}
