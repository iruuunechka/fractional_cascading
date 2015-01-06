package cascading;

import cascading.element.AlienElement;
import cascading.element.Element;
import cascading.element.OurElement;
import cascading.selector.Selector;

import java.util.*;

/**
 * Class for cascading entry
 * @author Irene Petrova
 */
public class Entry<T> {
    public List<Element<T>> elements; //список элементов
    public List<Entry<T>> parent;  //список тех, откуда каскадировали, для списков их всегда 1 (Это те, откуда попадали чужие элементы)
    private int p; //добавляется каждый p-й чужой элемент в список
    public final int index; //номер вершины, соответствующий данному Entry
    private final Comparator<T> comparator; // Comparator для элементов типа T

    //для последнего списка, в который не добавляется чужих
    public Entry(List<T> elements, int p, int index, Comparator<T> comparator) {
        this.index = index;
        this.comparator = comparator;
        this.elements = new ArrayList<>();
        for (T elem : elements) {
            this.elements.add(new OurElement<>(elem));
        }
        this.p = p;
        parent = null;
    }

    public Entry(List<T> elements, int p, List<Entry<T>> parent, int index, Comparator<T> comparator) {
        this.index = index;
        this.comparator = comparator;
        this.elements = new ArrayList<>();
        this.p = p;
        this.parent = parent;
        int posInElements = 0;
        T curInElements = elements.get(posInElements);
        int[] posInParent = new int[parent.size()];
        Arrays.fill(posInParent, 0);
        List<T> curInParent = new ArrayList<>();
        for (int i = 0; i < parent.size(); ++i) {
            curInParent.add(parent.get(i).elements.get(posInParent[i]).getValue());
        }
        boolean elementsFinished = false;
        boolean[] elementsInParentFinished = new boolean[parent.size()];
        Arrays.fill(elementsInParentFinished, false);
        int nonFinishedElements = parent.size() + 1;
        while (nonFinishedElements > 0) {
            T minInParent = null;
            int parentWithMin = -1;
            for (int i = 0; i < parent.size(); ++i) {
                if (elementsInParentFinished[i]) {
                    continue;
                }
                if (minInParent == null || comparator.compare(curInParent.get(i), minInParent) < 0) {
                    minInParent = curInParent.get(i);
                    parentWithMin = i;
                }
            }
            if ((!elementsFinished) && (minInParent == null || comparator.compare(curInElements, minInParent) <= 0)) {
                this.elements.add(new OurElement<>(curInElements));
                posInElements++;
                if (posInElements >= elements.size()) {
                    elementsFinished = true;
                    nonFinishedElements--;
                    continue;
                }
                curInElements = elements.get(posInElements);
            } else {
                //noinspection ConstantConditions
                this.elements.add(new AlienElement<>(posInParent[parentWithMin], minInParent, parentWithMin));
                posInParent[parentWithMin] += parent.get(parentWithMin).p;
                if (posInParent[parentWithMin] >= parent.get(parentWithMin).elements.size()) {
                    elementsInParentFinished[parentWithMin] = true;
                    nonFinishedElements--;
                    continue;
                }
                curInParent.set(parentWithMin, parent.get(parentWithMin).elements.get(posInParent[parentWithMin]).getValue());
            }
        }
        int lastOur = -1;
        int[] lastAlien = new int[parent.size()];
        Arrays.fill(lastAlien, -1);
        for (int i = this.elements.size() - 1; i >= 0; --i) {
            if (this.elements.get(i).isAlien()) {
                AlienElement ae = (AlienElement) this.elements.get(i);
                ae.setNextOurPos(lastOur);
                lastAlien[ae.getOwnerIndex()] = i;
            } else {
                lastOur = i;
                OurElement oe = (OurElement) this.elements.get(i);
                oe.setNextAlienPos(Arrays.copyOf(lastAlien, lastAlien.length));
            }
        }
    }

    public static <T> Entry<T> createCascade(List<List<T>> data, int p, Comparator<T> comparator) {
        if (data.size() == 0) {
            return null;
        }
        Entry<T> cur = new Entry<>(data.get(data.size() - 1), p, data.size() - 1, comparator);
        for (int i = data.size() - 2; i >= 0; --i) {
            cur = new Entry<>(data.get(i), p, Arrays.asList(cur), i, comparator);
        }
        return cur;
    }


    public int posInNext(int posInCur, T x, int parentIndex) {
        int posInParent;
        if (posInCur == -1) {
            posInParent = parent.get(parentIndex).elements.size();
        } else {
            if (elements.get(posInCur).isAlien()) {                    //найденный в текущем списке чужой
                AlienElement elementInAlien = (AlienElement) elements.get(posInCur);
                posInParent = elementInAlien.getPosInLocalList();
            } else {                                                   //найденный в текущем списке свой
                OurElement elementInOur = (OurElement) elements.get(posInCur);
                int nextAlienPos = elementInOur.getNextAlienPos()[parentIndex];
                if (nextAlienPos == -1) {     //элемента не существует
                    posInParent = parent.get(parentIndex).elements.size();
                } else {
                    AlienElement elementInAlien = (AlienElement) elements.get(nextAlienPos);
                    posInParent = elementInAlien.getPosInLocalList();
                }
            }
        }
        for (int i = posInParent - 1; i > Math.max(-1, posInParent - parent.get(parentIndex).p); --i) {
            if (comparator.compare(parent.get(parentIndex).elements.get(i).getValue(), x) >= 0) {
                posInParent = i;
            }
        }
        return posInParent < parent.get(parentIndex).elements.size() ? posInParent : -1;
    }

    public T getRes(Entry<T> cur, int posInParent) {
        Element resInParent = cur.elements.get(posInParent);
        if (resInParent.isAlien()) {
            AlienElement res = (AlienElement) resInParent;
            int resPos = res.getNextOurPos();
            if (resPos == -1) {
                return null;
            }
            return cur.elements.get(resPos).getValue();
        } else {
            return cur.elements.get(posInParent).getValue();
        }
    }

    public int binSearch(T x) {
        int l = -1;
        int r = elements.size();
        while (l < r - 1) {
            int m = (l + r) / 2;
            if (comparator.compare(elements.get(m).getValue(), x) < 0) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }

    public Answer search(T x, Selector<T> selector) {
        Answer<T> answer = new Answer<>();
        Entry<T> cur = this;
        int curPos = binSearch(x);
        if (curPos == elements.size()) {
            answer.add(cur.index, null);
            curPos = -1;
        } else {
            answer.add(cur.index, getRes(cur, curPos));
        }
        while (cur.parent != null) {
            Integer parentIndex;
            if (curPos != -1 && cur.elements.get(curPos).isAlien()) {
                parentIndex = ((AlienElement)cur.elements.get(curPos)).getOwnerIndex();
            } else {
                parentIndex = selector.selectNext(x, cur, comparator);
                if (parentIndex == null)
                    break;
            }
            curPos = cur.posInNext(curPos, x, parentIndex);
            cur = cur.parent.get(parentIndex);
            if (curPos == -1) {
                answer.add(cur.index, null);
            } else {
                answer.add(cur.index, getRes(cur, curPos));
            }
        }
        return answer;
    }
}
