/**
 * @author Irene Petrova
 */
public class AlienElement implements Element {
    public void setNextOurPos(int nextOurPos) {
        this.nextOurPos = nextOurPos;
    }

    private int nextOurPos; //позиция следующего элемента из текущего списка
    private int posInLocalList; //позиция в списке, откуда пришел

    public int getOwnerIndex() {
        return ownerIndex;
    }

    private int ownerIndex;

    private int value;

    public AlienElement(int posInLocalList, int value, int ownerIndex) {
        this.posInLocalList = posInLocalList;
        this.value = value;
        this.ownerIndex = ownerIndex;
    }

    @Override
    public boolean isAlien() {
        return true;
    }

    public int getPosInLocalList() {
        return posInLocalList;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public int getNextOurPos() {
        return nextOurPos;
    }
}
