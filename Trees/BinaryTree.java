package Trees;

import Queues.ArrayQueue;
import static java.lang.Integer.max;
import java.util.*;

public class BinaryTree<T extends Comparable<T>> implements BinaryTreeADT<T> {
    
    private BinaryNode<T> root;
    private int cont;
    
    
    public BinaryTree(){
        root = null;
        cont = 0;
    }
    
    public BinaryTree(T element){
        root = new BinaryNode<>(element);
        cont = 1;
    }
    
    public int getCont(){
        return cont;
    }
    
    public BinaryNode<T> getRoot(){
        return root;
    }

    public boolean isEmpty() {
        return cont == 0;
    }

    public int size() {
        return cont;
    }
    
    public Iterator<T> iteratorPreOrder(){
        ArrayList<T> lista = new ArrayList();
        preOrden(lista, root);
        return lista.iterator();
    }
    
    private void preOrden(ArrayList<T> lista, BinaryNode<T> tree){
        if(tree == null)
            return;
        lista.add(tree.getElement());
        preOrden(lista,tree.getLeft());
        preOrden(lista,tree.getRight());
    }
    
    public Iterator<T> iteratorInOrder(){
        ArrayList<T> lista = new ArrayList();
        inOrden(lista, root);
        return lista.iterator();
    }
    
    private void inOrden(ArrayList<T> lista, BinaryNode<T> tree){
        if(tree == null)
            return;
        inOrden(lista,tree.getLeft());
        lista.add(tree.getElement());
        inOrden(lista,tree.getRight());
    }

    public Iterator<T> iteratorPostOrder(){
        ArrayList<T> lista = new ArrayList();
        postOrden(lista, root);
        return lista.iterator();
    }
    
    public void postOrden(ArrayList<T> lista, BinaryNode<T> tree){
        if(tree == null)
            return;
        postOrden(lista,tree.getLeft());
        postOrden(lista,tree.getRight());
        lista.add(tree.getElement());
    }

    public Iterator<T> iteratorPreOrderIterativo(){
        ArrayList<T> lista = new ArrayList<>();
        Stack <BinaryNode<T>> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            BinaryNode<T> actual = stack.pop();
            lista.add(actual.getElement());
            if(actual.getLeft() != null)
                stack.add(actual.getLeft());
            if(actual.getRight() != null)
                stack.add(actual.getRight());
        }
        return lista.iterator();
    }
    
    public Iterator<T> iteratorPorNivelIterativo(){
        ArrayList<T> lista = new ArrayList<>();
        ArrayQueue<BinaryNode<T>> stack = new ArrayQueue<>();
        stack.enqueue(root);
        while(!stack.isEmpty()){
            BinaryNode<T> actual = stack.dequeue();
            lista.add(actual.getElement());
            if(actual.getLeft() != null)
                stack.enqueue(actual.getLeft());
            if(actual.getRight() != null)
                stack.enqueue(actual.getRight());
        }
        return lista.iterator();
    }
    
    /**
     * Regresa true si encuentra un binary node que tenga como elemento el buscado.
     * Este méodo no aprovecha el uso de un binary search tree.
     * @param element
     * @return 
     */
    public boolean contains(T element){
        return contains(root, element);
    }
    
    private boolean contains(BinaryNode<T> tree, T element){
        if(tree == null)
            return false;
        
        boolean l,r;
        if(root.getElement().equals(element)){
            return true;
        } else {
            l = contains(tree.getLeft(), element);
            if(l){
                return true;
            } else {
                r = contains(tree.getRight(), element);
                return r;
            } 
        }
    }
    
    /**
     * Si encuentra el elemento, regresa el BinaryNode que lo contiene
     * @param element
     * @return 
     */
    public BinaryNode<T> search(T element){
        return search(root, element);
    }
    
    private BinaryNode<T> search(BinaryNode<T> tree, T element){
        if(tree == null)
            return null;
        
        BinaryNode<T> aux;
        if(tree.getElement().compareTo(element) == 0){
            return tree;
        } else {
            if(tree.getElement().compareTo(element) > 0){
                aux = search(tree.getLeft(), element);
                return aux;
            } else {
                aux = search(tree.getRight(), element);
                return aux;
            }
        }
    }
    
    /**
     * @param element 
     */
    public void insert(T element){
        BinaryNode<T> actual = root;
        BinaryNode<T> anterior = root;
        BinaryNode<T> nuevo = new BinaryNode<>(element);
        
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
        
        
        
    }
    
    
    public void delete(T element){
        BinaryNode<T> delete = search(element);
        if(delete == null)
            return;
        
        if(delete.isLeaf()){
            if(delete.getDad()== null)
                root = null;
            else {
                if(delete.getDad().getLeft() == delete)
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
            BinaryNode<T> nextInOrder = findNextInOrder(delete); 
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
    
    /**
     * Regresa el sucesor in order del nodo dado.
     * @param tree
     * @return 
     */
    public BinaryNode<T> findNextInOrder(BinaryNode<T> tree){
        if(tree == null)
            return null;
        if(tree.getRight() == null)
            return null;
        BinaryNode<T> aux = tree.getRight();
        if(aux.getLeft() == null)
            return aux;
        else{
            BinaryNode<T> actual = aux;
            while(actual != null){
                aux = actual.getLeft();
                actual = actual.getLeft();
            }
            return aux;
        }
    }
    
    public int getHeight(){
        return getHeight(root, 0);
    }
    
    //Se pueden contar usando el recorrido por niveles con el while y la pila
    private int getHeight(BinaryNode<T> tree, int cont){
        if(tree == null)
            return cont;
        int izq = getHeight(tree.getLeft(), cont+1);
        int der = getHeight(tree.getRight(), cont+1);
        return max(izq, der);
    }
    
    //Ahora vamos a hacer árboles frondosos usando árboles AVL
    //Para cada nodo del árbol al diferencia en alturas entre sus subárboles es -1, 0, 1
    //Vamos a hacer rotaciones:
    /*
    Izquierda-izquierda (-)(-)
    Derecha-derecha (+)(+)
    Izquierda-derecha (-)(+)
    Derecha-izquierda (+)(-)

    Los signos son los valores de los equilibrity factor.
    */
    
    /**
     * Método que rota left left (Cuando se tiene -2, -1)
     * @param tree 
     */
    public void rotaLeftLeft(BinaryNode<T> tree){
        BinaryNode<T> newTop = tree.getLeft();      //B is newTop   
        BinaryNode<T> originalDad = tree.getDad();  //guardamos el papa de alfa
        tree.setLeft(null);                         //cortamos la parte izquirda del tree
        BinaryNode<T> c = newTop.getRight();        //c es el hijo derecho de beta
        newTop.setRight(tree);                      //cambiamos el hijo derecho de beta por alfa
        tree.setDad(newTop);                        //le cambiamos el papa al alfa
        tree.setLeft(c);                            //le colgamos a alfa el c
        c.setDad(tree);                             //Le cambiamos el papa a c
        newTop.setDad(originalDad);                 //El papa de newTop es el que era el papa de alfa
    }
    
    
    
    
    
    
    
    

}
