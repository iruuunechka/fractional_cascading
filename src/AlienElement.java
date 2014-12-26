/**
 * @author Irene Petrova
 */
public class AlienElement implements Element {
    private int nextOurPos; //позиция следующего элемента из текущего списка
    private int posInLocalList; //позиция в списке, откуда пришел

    @Override
    public boolean isAlien() {
        return true;
    }
}
