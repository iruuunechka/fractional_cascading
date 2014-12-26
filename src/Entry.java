import java.util.ArrayList;
import java.util.List;

/**
 * @author Irene Petrova
 */
public class Entry {
    private List<Element> elements;
    private List<Integer> elementsValue;
    private Entry parent;
    private int p; //добавляется каждый p-й чужой элемент в список

    //для последнего списка, в который не добавляется чужих
    private Entry(List<Integer> elements, int p) {
        this.elements = null;
        this.elementsValue = new ArrayList<>(elements);
        this.p = p;
        parent = null;
    }

    private Entry(List<Integer> elements, int p, Entry parent) {
        elements = new ArrayList<>();
        Integer curInElements = elements.get(0);
        Integer curInParent = parent.elementsValue.get(0);
    }

}
