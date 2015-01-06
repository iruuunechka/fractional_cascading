package cascading.element;

/**
 * Interface for elements in cascade's lists
 * @author Irene Petrova
 */
public interface Element<T> {
    public boolean isAlien();
    public T getValue();
}
