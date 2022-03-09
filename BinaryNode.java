package Trees;


public class BinaryNode<T extends Comparable <T>> implements BinaryNodeADT<T> {
    
    private T element;
    private BinaryNode<T> left;
    private BinaryNode<T> right;
    private BinaryNode<T> dad;
    public int eqFactor;
    
    
    /*
     *Creates an empty node
     */
    public BinaryNode(){
        element = null;
        left = null;
        right = null;
        dad = null;
    }
    
    /*
     *Creates a node storing the specified data
     * @param element.*/
    public BinaryNode(T element){
        this.element = element;
        left = null;
        right = null;
        dad = null;
    }
    
    /**Returns the data stored in the node
     * @return .*/
    public T getElement(){
        return element;
    }
    
    /**
     * Sets the element stored in the node.
     * @param element 
     */
    public void setElement(T element){
        this.element = element;
    }
    
    /**Returns the TreeNode stored on the left
     * @return .*/
    public BinaryNode<T> getLeft(){
        return left;
    }
    
    /**Returns the TreeNode stored on the right
     * @return .*/
    public BinaryNode<T> getRight(){
        return right;
    }

    /**
     * Returns the reference to the node above this one.
     * @return 
     */
    public BinaryNode<T> getDad() {
        return dad;
    }
   
    /**Returns true if both the left and right TreeNodes point to null
     * @return .*/
    public boolean isLeaf(){
        return right == null && left == null;
    }
    
    /**Sets the left node that follows this one
     * @param binaryNode*/
    public void setLeft(BinaryNode<T> binaryNode){
        left = binaryNode;
    }
    
    /**Sets the right node that follows this one
     * @param binaryNode*/
    public void setRight(BinaryNode<T> binaryNode){
        right = binaryNode;
    }
    
    /**
     * Sets the dad to the node above this one.
     * @param dad 
     */
    public void setDad(BinaryNode<T> dad) {
        this.dad = dad;
    }
    
   /**
    * This I don't really understand what it does.
    * @param actual 
    */
    public void hang(BinaryNode<T> actual){
        if(actual==null)
            return;
        if(actual.getElement().compareTo(element) <= 0)
            left=actual;
        else
            right=actual;
        actual.setDad(this);
    }
    
    /**
     * Regresa true si el nodo tiene un hijo solamente.
     * @return 
     */
    public boolean hasOnlyOneChild(){
        return (this.left != null && this.right == null) || (this.left == null && this.right != null);
    }
    
    /**
     * Returns true if the node has two children.
     * @return 
     */
    public boolean isFull(){
        return this.right != null && this.left != null;
    }

    

    

    

    
    
    

    
    
    

}
