import java.util.ArrayList;
import java.util.List;

/**
 * @author Irene Petrova
 */
public class Entry {
    private List<Element> elements;
    private Entry parent;
    private int p; //добавляется каждый p-й чужой элемент в список

    //для последнего списка, в который не добавляется чужих
    private Entry(List<Integer> elements, int p) {
        this.elements = new ArrayList<>();
        for (Integer elem : elements) {
            this.elements.add(new OurElement(elem, -1));
        }
        this.p = p;
        parent = null;
    }

    private Entry(List<Integer> elements, int p, Entry parent) {
        this.elements = new ArrayList<>();
        this.p = p;
        this.parent = parent;
        int posInElements = 0;
        Integer curInElements = elements.get(posInElements);
        int posInParent = 0;
        Integer curInParent = parent.elements.get(posInParent).getValue();
        while (posInElements < elements.size() && posInParent < parent.elements.size()) {
            if (curInElements <= curInParent) {
                this.elements.add(new OurElement(curInElements, -1));
                posInElements++;
                if (posInElements >= elements.size()) {
                    for (int i = posInParent; i < parent.elements.size(); i +=p) {
                        this.elements.add(new AlienElement(i, parent.elements.get(i).getValue(), -1));
                    }
                    continue;
                }
                curInElements = elements.get(posInElements);
            } else {
                this.elements.add(new AlienElement(posInParent, curInParent, -1));
                posInParent +=p;
                if (posInParent >= parent.elements.size()) {
                    for (int i = posInElements; i < elements.size(); i++) {
                        this.elements.add(new OurElement(elements.get(i), -1));
                    }
                    continue;
                }
                curInParent = parent.elements.get(posInParent).getValue();
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
                oe.setNextAlienPos(lastAlien);
            }
        }
    }

    public static Entry createCascade(List<List<Integer>> data, int p) {
        if (data.size() == 0) {
            return null;
        }
        Entry cur = new Entry(data.get(data.size() - 1), p);
        for (int i = data.size() - 2; i >= 0; --i) {
            cur = new Entry(data.get(i), p, cur);
        }
        return cur;
    }

    private int posInNext(int posInCur, int x) {
        int posInParent;
        if (posInCur == -1) {
            posInParent = parent.elements.size() - 1;
        } else {
            if (elements.get(posInCur).isAlien()) {                    //найденный в текущем списке чужой
                AlienElement elementInAlien = (AlienElement) elements.get(posInCur);
                posInParent = elementInAlien.getPosInLocalList();
            } else {                                                   //найденный в текущем списке свой
                OurElement elementInOur = (OurElement) elements.get(posInCur);
                int nextAlienPos = elementInOur.getNextAlienPos();
                if (nextAlienPos == -1) {     //элемента не существует
                    posInParent = -1;
                } else {
                    AlienElement elementInAlien = (AlienElement) elements.get(nextAlienPos);
                    posInParent = elementInAlien.getPosInLocalList();
                }
            }
        }
        for (int i = posInParent; i >= Math.max(0, posInParent - p); --i) {
            if (parent.elements.get(i).getValue() >= x) {
                posInParent = i;
            }
        }
        return parent.elements.get(posInParent).getValue() >= x ? posInParent : -1;
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
            curPos = cur.posInNext(curPos, x);
            cur = cur.parent;
            if (curPos == -1) {
                answer.add(-1);
            } else {
                answer.add(getRes(cur, curPos));
            }
        }
        return answer;
    }
}
