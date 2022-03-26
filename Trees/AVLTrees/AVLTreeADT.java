package Trees;

import java.util.Iterator;

/**
 *
 * @author Usuario
 * @param <T>
 */
public interface AVLTreeADT<T extends Comparable<T>>{
    
    public void rotate(AVLNode<T> node);
    
    public boolean isEmpty();
    
    public int size();
    
    public boolean contains(T element);
    
    public Iterator<T> iteratorPreOrder();
    
    public Iterator<T> iteratorPostOrder();
    
    public Iterator<T> iteratorInOrder();
    
    public Iterator<T> iteratorLeves();
    
    public AVLNode<T> search(T element);
    
    public void insert(T element);
    
    public void delete(T element);
    
    public int getHeight();
    
    
    
}
