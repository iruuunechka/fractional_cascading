/**
 * @author Irene Petrova
 */
public class OurElement implements Element {
    private int nextAlienPos; //позиция следующего чужого элемента

    @Override
    public boolean isAlien() {
        return false;
    }
}
