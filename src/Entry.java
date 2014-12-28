import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Irene Petrova
 */
public class Entry {
    private List<Element> elements;
    private List<Entry> parent;
    private int p; //добавляется каждый p-й чужой элемент в список

    //для последнего списка, в который не добавляется чужих
    private Entry(List<Integer> elements, int p) {
        this.elements = new ArrayList<>();
        for (Integer elem : elements) {
            this.elements.add(new OurElement(elem));
        }
        this.p = p;
        parent = null;
    }

    private Entry(List<Integer> elements, int p, List<Entry> parent) {
        this.elements = new ArrayList<>();
        this.p = p;
        this.parent = parent;
        int posInElements = 0;
        Integer curInElements = elements.get(posInElements);
        int[] posInParent = new int[parent.size()];
        Arrays.fill(posInParent, 0);
        List<Integer> curInParent = new ArrayList<>();
        for (int i = 0; i < parent.size(); ++i) {
            curInParent.add(parent.get(i).elements.get(posInParent[i]).getValue());
        }
        boolean elementsFinished = false;
        boolean[] elementsInParentFinished = new boolean[parent.size()];
        Arrays.fill(elementsInParentFinished, false);
        int nonFinishedElements = parent.size() + 1;
        while (nonFinishedElements > 0) {
            Integer minInParent = curInParent.get(0);
            int parentWithMin = 0;
            for (int i = 1; i < parent.size(); ++i) {
                if (elementsInParentFinished[i]) {
                    continue;
                }
                if (curInParent.get(i) < minInParent) {
                    minInParent = curInParent.get(i);
                    parentWithMin = i;
                }
            }
            if ((!elementsFinished) && curInElements <= minInParent) {
                this.elements.add(new OurElement(curInElements));
                posInElements++;
                if (posInElements >= elements.size()) {
                    elementsFinished = true;
                    nonFinishedElements--;
                    continue;
                }
                curInElements = elements.get(posInElements);
            } else {
                this.elements.add(new AlienElement(posInParent[parentWithMin], minInParent, parentWithMin));
                posInParent[parentWithMin] +=p;
                if (posInParent[parentWithMin] >= parent.get(parentWithMin).elements.size()) {
                    elementsInParentFinished[parentWithMin] = true;
                    nonFinishedElements--;
                    continue;
                }
                curInParent.set(parentWithMin, parent.get(parentWithMin).elements.get(posInParent[parentWithMin]).getValue());
            }
        }
        int lastOur = -1;
        int lastAlien = -1;
        for (int i = this.elements.size() - 1; i >= 0; --i) {
            if (this.elements.get(i).isAlien()) {
                lastAlien = i;
                AlienElement ae = (AlienElement) this.elements.get(i);
                ae.setNextOurPos(lastOur);
            } else {
                lastOur = i;
                OurElement oe = (OurElement) this.elements.get(i);
                oe.setNextAlienPos(new int[]{lastAlien});
            }
        }
    }

    public static Entry createCascade(List<List<Integer>> data, int p) {
        if (data.size() == 0) {
            return null;
        }
        Entry cur = new Entry(data.get(data.size() - 1), p);
        for (int i = data.size() - 2; i >= 0; --i) {
            cur = new Entry(data.get(i), p, Arrays.asList(cur));
        }
        return cur;
    }

    private int posInNext(int posInCur, int x, int parentIndex) {
        int posInParent;
        if (posInCur == -1) {
            posInParent = parent.get(parentIndex).elements.size() - 1;
        } else {
            if (elements.get(posInCur).isAlien()) {                    //найденный в текущем списке чужой
                AlienElement elementInAlien = (AlienElement) elements.get(posInCur);
                posInParent = elementInAlien.getPosInLocalList();
            } else {                                                   //найденный в текущем списке свой
                OurElement elementInOur = (OurElement) elements.get(posInCur);
                int nextAlienPos = elementInOur.getNextAlienPos()[0];
                if (nextAlienPos == -1) {     //элемента не существует
                    posInParent = -1;
                } else {
                    AlienElement elementInAlien = (AlienElement) elements.get(nextAlienPos);
                    posInParent = elementInAlien.getPosInLocalList();
                }
            }
        }
        for (int i = posInParent; i >= Math.max(0, posInParent - p); --i) {
            if (parent.get(parentIndex).elements.get(i).getValue() >= x) {
                posInParent = i;
            }
        }
        return parent.get(parentIndex).elements.get(posInParent).getValue() >= x ? posInParent : -1;
    }

    private Integer getRes(Entry cur, int posInParent) {
        Element resInParent = cur.elements.get(posInParent);
        if (resInParent.isAlien()) {
            AlienElement res = (AlienElement) resInParent;
            int resPos = res.getNextOurPos();
            if (resPos == -1) {
                return -1;
            }
            return cur.elements.get(resPos).getValue();
        } else {
            return cur.elements.get(posInParent).getValue();
        }
    }
    public List<Integer> search(int x) {
        List<Integer> answer = new ArrayList<>();
        int l = -1;
        int r = elements.size();
        while (l < r - 1) {
            int m = (l + r) / 2;
            if (elements.get(m).getValue() < x) {
                l = m;
            } else {
                r = m;
            }
        }
        Entry cur = this;
        int curPos = r;
        if (r == elements.size()) {
            answer.add(-1);
            curPos = -1;
        } else {
            answer.add(getRes(cur, r));
        }
        while (cur.parent != null) {
            int parentIndex = 0;
            curPos = cur.posInNext(curPos, x, parentIndex);
            cur = cur.parent.get(parentIndex);
            if (curPos == -1) {
                answer.add(-1);
            } else {
                answer.add(getRes(cur, curPos));
            }
        }
        return answer;
    }
}
