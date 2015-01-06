package cascading.element;

/**
 * Class for element from another list
 * @author Irene Petrova
 */
public class AlienElement implements Element {

    private int nextOurPos; //позиция следующего элемента из текущего списка
    private int posInLocalList; //позиция в списке, откуда пришел
    private int ownerIndex; // каким является ребенком
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

    @Override
    public Integer getValue() {
        return value;
    }

    public int getOwnerIndex() {
        return ownerIndex;
    }

    public int getPosInLocalList() {
        return posInLocalList;
    }

    public int getNextOurPos() {
        return nextOurPos;
    }

    public void setNextOurPos(int nextOurPos) {
        this.nextOurPos = nextOurPos;
    }
}
