package Trees;

import Queues.ArrayQueue;
import static java.lang.Integer.max;
import java.util.*;

public class AVLTree<T extends Comparable<T>> {
    
    private AVLNode<T> root;
    private int cont;
    
    public AVLTree(){
        root = null;
        cont = 0;
    }
    
    public AVLTree(T element){
        root = new AVLNode<>(element);
        cont = 1;
    }

    public AVLNode<T> getRoot() {
        return root;
    }

    public int getCont() {
        return cont;
    }
    
    public int getHeight(){
        return getHeight(root, 0);
    }
    
    private int getHeight(AVLNode<T> tree, int cont){
        if(tree == null)
            return cont;
        int izq = getHeight(tree.getLeft(), cont+1);
        int der = getHeight(tree.getRight(), cont+1);
        return max(izq,der);
    }
    
    public boolean isEmpty(){
        return cont == 0;
    }
    
    public int size(){
        return cont;
    }
    
    public Iterator<T> iteratorPreOrder(){
        ArrayList<T> list = new ArrayList();
        preOrden(list, root);
        return list.iterator();
    }
    
    private void preOrden(ArrayList<T> list, AVLNode<T> tree){
        if(tree == null)
            return;
        list.add(tree.getElement());
        preOrden(list, tree.getLeft());
        preOrden(list, tree.getRight());
    }
    
    public Iterator<T> iteratorInOrder(){
        ArrayList<T> list = new ArrayList();
        inOrden(list, root);
        return list.iterator();
    }
    
    private void inOrden(ArrayList<T> list, AVLNode<T> tree){
        if(tree == null)
            return;
        inOrden(list, tree.getLeft());
        list.add(tree.getElement());        
        inOrden(list, tree.getRight());
    }
    
    public Iterator<T> iteratorPostOrder(){
        ArrayList<T> list = new ArrayList();
        postOrden(list, root);
        return list.iterator();
    }
    
    private void postOrden(ArrayList<T> list, AVLNode<T> tree){
        if(tree == null)
            return;
        postOrden(list, tree.getLeft());
        postOrden(list, tree.getRight());
        list.add(tree.getElement());
    }
    
    public Iterator<T> iteratorLevels(){
        ArrayList<T> lista = new ArrayList();
        ArrayQueue<AVLNode<T>> stack = new ArrayQueue<>();
        stack.enqueue(root);
        while(!stack.isEmpty()){
            AVLNode<T> actual = stack.dequeue();
            lista.add(actual.getElement());
            if(actual.getLeft() != null){
                stack.enqueue(actual.getLeft());
            }
            if(actual.getRight() != null){
                stack.enqueue(actual.getRight());
            }
        }
        return lista.iterator();
    }
    
    public String printLevelOrder(){
        int h = this.getHeight();
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= h; i++){
            printGivenLevel(root, i, sb);
            sb.append('\n');
        }
        return sb.toString();
    }
    
    private void printGivenLevel(AVLNode<T> node, int level, StringBuilder sb){
        if(node == null)
            return;
        if(level == 1)
            if(node.getDad() == null)
                sb.append(node.getElement()).append(' ');
            else{
                sb.append(node.getElement()).append(" (").append(node.getDad().getElement()).append(")");
                sb.append("(").append(node.getEqFactor()).append(")  "); 
            }   
        if(level > 1){
            printGivenLevel(node.getLeft(), level-1, sb);
            printGivenLevel(node.getRight(), level-1, sb);
        }
    }
    
    public AVLNode<T> search(T element){
        return search(root, element);
    }
    
    private AVLNode<T> search(AVLNode<T> tree, T element){
        if(tree == null)
            return null;
        
        AVLNode<T> aux;
        if(tree.getElement().compareTo(element) == 0)
            return tree;
        if(tree.getElement().compareTo(element) > 0){
            aux = search(tree.getLeft(), element);
            return aux;
        } else {
            aux = search(tree.getRight(), element);
            return aux;
        }
    }
    
    public void insert(T element){
        AVLNode<T> actual, anterior, nuevo;
        actual = root;
        anterior = root;
        nuevo = new AVLNode<>(element);
        
        if(root == null){
            root = nuevo;
            return;
        }
        while(actual != null){
            anterior = actual;
            if(element.compareTo(actual.getElement()) <= 0)
                actual = actual.getLeft();
            else
                actual = actual.getRight();
        }
        anterior.hang(nuevo);
        cont++;
        
        
        //TODO: Ajustar los factores de equilibrio
        this.updateEF(nuevo);
    }
    
    private void updateEF(AVLNode<T> actual){ //Actual es nuevo
        //¿Cuándo dejamos de ajustar?
        //Cuando el factor de equilibrio cambia a 0 o hacemos una rotación
        //Cuando hacemos una rotación: cuando el ef es 2 o -2
        AVLNode<T> dad = actual.getDad();
        while(dad != null ){
            if(dad.getLeft() != null){
                if(dad.getLeft().equals(actual)){
                    dad.decreaseEF();
                    if(dad.getEqFactor()==2 || dad.getEqFactor() ==-2){
                        this.rotate(dad);
                        break;
                    }
                }    
            }
            if(dad.getRight() != null){
                if(dad.getRight().equals(actual)){
                    dad.increaseEF();
                    if(dad.getEqFactor()==2 || dad.getEqFactor() ==-2){
                        this.rotate(dad);
                        break;
                    }
                }
            }
            actual = dad;
            dad = dad.getDad();
        }
    }
    
    public void delete(T element){
        AVLNode<T> delete = search(element);
        if(delete == null)
            return;
        
        if(delete.isLeaf()){
            if(delete.getDad() == null)
                root = null;
            else {
                if(delete.getDad().getLeft().equals(delete))
                    delete.getDad().setLeft(null);
                else
                    delete.getDad().setRight(null);
            }
            cont--;
            return;
        }
        if(delete.hasOnlyOneChild()){
            if(delete.getDad() == null){
                if(delete.getLeft() != null)
                    root = delete.getLeft();
                else
                    root = delete.getRight();
            }
            else {
                if(delete.getLeft() != null)
                    delete.getDad().hang(delete.getLeft());
                else
                    delete.getDad().hang(delete.getRight());
            }
            cont--;
            return;
        }
        if(delete.isFull()){
            AVLNode<T> nextInOrder = findNextInOrder(delete);
            delete.setElement(nextInOrder.getElement());
            if(nextInOrder.getRight() == null){
                if(nextInOrder.getDad().getRight() == nextInOrder)
                    nextInOrder.getDad().setRight(null);
                else
                    nextInOrder.getDad().setLeft(null);
            } else {
                nextInOrder.getDad().hang(nextInOrder.getRight());
            }
            cont--;
        }
    }
    
    public AVLNode<T> findNextInOrder(AVLNode<T> tree){
        if(tree == null)
            return null;
        if(tree.getRight() == null)
            return null;
        AVLNode<T> aux = tree.getRight();
        if(aux.getLeft() == null)
            return aux;
        else {
            AVLNode<T> actual = aux;
            while(actual != null){
                aux = actual.getLeft();
                actual = actual.getLeft();
            }
            return aux;
        }  
    }
    
    public AVLNode<T> rotate(AVLNode<T> actual){
        //Izquierda-Izquierda
        
        
        //Derecha-Derecha
        
        
        //Izquierda-Derecha
        
        
        //Derecha-Izquierda
        if(actual.getEqFactor() == 2 && actual.getRight().getEqFactor() == -1){
            AVLNode<T> alfa, papa, A, beta, gamma, D, B, C;
            alfa = actual;
            papa = actual.getDad();
            A = alfa.getLeft();
            beta = alfa.getRight();
            gamma = beta.getLeft();
            D = beta.getRight();
            B = gamma.getLeft();
            C = gamma.getRight();
            if(A != null)
                alfa.hang(A);
            else
                alfa.setLeft(null);
            if(B != null)
                alfa.hang(B);
            else 
                alfa.setRight(null);
            if(C != null)
                beta.hang(C);
            else
                beta.setLeft(null);
            if(D != null)
                beta.hang(D);
            else
                beta.setRight(null);
            gamma.hang(alfa);
            gamma.hang(beta);
            papa.hang(gamma);
            
            //Falta actualizar factores de equilibrio
            
            //Tenemos que regresar a gamma para saber a partir de donde se va a continuar
            return gamma;
        }
        return null;
    }
    
    
    
    
    
    
    
    
    
    

}
