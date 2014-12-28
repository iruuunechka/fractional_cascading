/**
 * @author Irene Petrova
 */
public class OurElement implements Element {
    public void setNextAlienPos(int[] nextAlienPos) {
        this.nextAlienPos = nextAlienPos;
    }

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
}
