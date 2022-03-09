/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

/**
 *
 * @author Usuario
 * @param <T>
 */
public interface BinaryNodeADT<T extends Comparable <T>> {
    
    
    public T getElement();
    
    public void setElement(T element);
    
    public BinaryNode<T> getLeft();
    
    public BinaryNode<T> getRight();
    
    public boolean isLeaf();
    
    public void setLeft(BinaryNode<T> binaryNode);
    
    public void setRight(BinaryNode<T> binaryNode);
 
    public void setDad(BinaryNode<T> dad);
    
    public void hang(BinaryNode<T> actual);
    
    public BinaryNode<T> getDad();
    
    
}
