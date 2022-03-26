package Trees;

/**
 *
 * @author Usuario
 * @param <T>
 */
public interface AVLNodeADT<T extends Comparable<T>>{
    
    public T getElement();
    
    public void setElement(T element);
    
    public AVLNode<T> getLeft();
    
    public AVLNode<T> getRight();
    
    public int getEqFactor();
    
    public int getHeight();
    
    public boolean isLeaf();
    
    public void setLeft(AVLNode<T> avlNode);
    
    public void setRight(AVLNode<T> avlNode);
 
    public void setDad(AVLNode<T> dad);
    
    public void hang(AVLNode<T> actual);
    
    public AVLNode<T> getDad();
    
}
