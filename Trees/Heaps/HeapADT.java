package Heaps;

/**
 *
 * @author Mariano
 * @param <T>
 */
public interface HeapADT<T extends Comparable<T>> {
    
    public T getMin();
    
    public int size();
    
    public void insert(T element);
    
    public int getLevels();
    
    public String toString();
    
}
