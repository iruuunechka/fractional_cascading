import java.util.ArrayList;
import java.util.List;

/**
 * @author Irene Petrova
 */
public class Answer {
    private final List<Integer> index;

    public List<Integer> getAns() {
        return ans;
    }

    private final List<Integer> ans;

    public Answer() {
        index = new ArrayList<>();
        ans = new ArrayList<>();
    }

    public void add(int index, Integer ans) {
        this.index.add(index);
        this.ans.add(ans);
    }


}
