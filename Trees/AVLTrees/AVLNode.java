package Trees;

import static java.lang.Integer.max;
import java.util.Objects;

public class AVLNode<T extends Comparable<T>> implements AVLNodeADT<T> {
    
    private T element;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private AVLNode<T> dad;
    private int eqFactor;
    private int height;
    
    public AVLNode(){
        element = null;
        left = null;
        right = null;
        dad = null;
        eqFactor = 0;
        height = 0;
    }
    
    public AVLNode(T element){
        this();
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public AVLNode<T> getDad() {
        return dad;
    }

    public int getEqFactor() {
        return eqFactor;
    }

    public int getHeight() {
        return height;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }
    
    public void setHeight(int height){
        this.height = height;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public void setDad(AVLNode<T> dad) {
        this.dad = dad;
    }

    public void setEqFactor(int eqFactor) {
        this.eqFactor = eqFactor;
    }
    
    public void increaseEF(){
        this.eqFactor += 1;
    }
    
    public void decreaseEF(){
        this.eqFactor -= 1;
    }
    
    public boolean isLeaf(){
        return right == null && left == null;
    }
    
    public void hang(AVLNode<T> actual){
        if(actual==null)
            return;
        if(actual.getElement().compareTo(element) <= 0)
            left=actual;
        else
            right=actual;
        actual.setDad(this);
    }
    
    public boolean hasOnlyOneChild(){
        return (this.left != null && this.right == null) || (this.left == null && this.right != null);
    }
    
    public boolean isFull(){
        return this.right != null && this.left != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AVLNode<?> other = (AVLNode<?>) obj;
        if (!Objects.equals(this.element, other.element)) {
            return false;
        }
        if (!Objects.equals(this.left, other.left)) {
            return false;
        }
        if (!Objects.equals(this.right, other.right)) {
            return false;
        }
        return Objects.equals(this.dad, other.dad);
    }

    public void setHeight(){
        if(this.isLeaf()){
            this.height = 0;
            return;
        }
        if(this.isFull()){
            int leftH, rightH;
            leftH = this.getLeft().getHeight();
            rightH = this.getRight().getHeight();
            this.height = max(leftH,rightH) + 1;
            return;
        }
        if(this.hasOnlyOneChild()){
            if(this.right != null){
                this.height = this.right.getHeight() + 1;
            } else {
                this.height = this.left.getHeight() + 1;
            }     
        }
    }
    
}
