package cascading.element;

/**
 * Class for element from this list
 * @author Irene Petrova
 */
public class OurElement<T> implements Element<T> {
    private int[] nextAlienPos; //позиция следующего чужого элемента
    private T value;

    public OurElement(T value) {
        this.value = value;
    }

    @Override
    public boolean isAlien() {
        return false;
    }

    @Override
    public T getValue() {
        return value;
    }

    public int[] getNextAlienPos() {
        return nextAlienPos;
    }

    public void setNextAlienPos(int[] nextAlienPos) {
        this.nextAlienPos = nextAlienPos;
    }

}
