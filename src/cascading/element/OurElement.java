package cascading.element;

/**
 * Class for element from this list
 * @author Irene Petrova
 */
public class OurElement implements Element {
    private int[] nextAlienPos; //позиция следующего чужого элемента
    private int value;

    public OurElement(int value) {
        this.value = value;
    }

    @Override
    public boolean isAlien() {
        return false;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public int[] getNextAlienPos() {
        return nextAlienPos;
    }

    public void setNextAlienPos(int[] nextAlienPos) {
        this.nextAlienPos = nextAlienPos;
    }

}
