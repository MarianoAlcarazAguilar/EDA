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
    
    public int getHeight(AVLNode<T> node){
        int aux = getHeight(node, 0);
        return aux-1;
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
            if(node.getDad() == null){
                sb.append(node.getElement()).append(' ').append("(root)").append(" (");
                sb.append(node.getEqFactor()).append(") ");
                //sb.append(node.getHeight()).append(")  ");
            } else {
                sb.append(node.getElement()).append(" (").append(node.getDad().getElement()).append(")");
                sb.append("(").append(node.getEqFactor()).append(") ");
                //sb.append(node.getHeight()).append(")  ");
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
        //Esto evita que metamos duplicados.
        //if(this.search(element) != null)
            //return;
        
        AVLNode<T> actual, anterior, nuevo;
        nuevo = new AVLNode<>(element);
        actual = root;
        anterior = root;
        
        
        
        if(root == null){
            root = nuevo;
            cont++;
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
        //this.updateAllHeights(nuevo);
        cont++;
        
        //TODO: Ajustar los factores de equilibrio
        this.updateEF(nuevo);
    }
    
    public void updateHeightsAfterInsertion(){
        
    }
    
    private void updateEF(AVLNode<T> actual){
        actual.setHeight();
        AVLNode<T> dad = actual.getDad();
        while(dad != null ){
            if(dad.getLeft() != null){
                if(dad.getLeft().equals(actual)){
                    dad.decreaseEF();
                    
                    if(dad.getEqFactor() == 0)
                        break;
                    if(dad.getEqFactor()==2 || dad.getEqFactor() ==-2){
                        this.rotate(dad);
                        break;
                    }
                }    
            }
            if(dad.getRight() != null){
                if(dad.getRight().equals(actual)){
                    dad.increaseEF();
                    
                    if(dad.getEqFactor() == 0)
                        break;
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
                AVLNode<T> dadAux = delete.getDad();
                if(dadAux.getRight() != null && dadAux.getRight().equals(delete)){
                    dadAux.setRight(null);
                } else {
                    dadAux.setLeft(null);
                }
                while(dadAux != null){
                    this.setNewEqFactor(dadAux);
                    if(dadAux.getEqFactor() == 2 || dadAux.getEqFactor() == -2){
                        dadAux = rotate(dadAux); //////////////////////////////////////Maybe aqu?? hay que cambiar algo
                    }
                    dadAux = dadAux.getDad();
                }
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
                this.setNewEqFactor(root);
            }
            else {
                if(delete.getLeft() != null)
                    delete.getDad().hang(delete.getLeft());
                else
                    delete.getDad().hang(delete.getRight());
                AVLNode<T> aux = delete.getDad();
                while(aux != null){
                    this.setNewEqFactor(aux);
                    if(aux.getEqFactor() == 2 || aux.getEqFactor() == -2){
                        aux = rotate(aux); //////////////////////////////////////Maybe aqu?? hay que cambiar algo
                    }
                    aux = aux.getDad();
                }
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
            
            this.setNewEqFactor(delete.getRight());
            if(delete.getRight() != null)
                if(delete.getRight().getEqFactor() == 2 || delete.getRight().getEqFactor() == -2){
                    rotate(delete.getRight());
                }
            this.setNewEqFactor(delete.getLeft());
            if(delete.getLeft() != null)
                if(delete.getLeft().getEqFactor() == 2 || delete.getLeft().getEqFactor() == -2){
                    rotate(delete.getLeft());
                }
            this.setNewEqFactor(delete);
            if(delete.getEqFactor() == 2 || delete.getEqFactor() == -2){
                rotate(delete);
            }
            AVLNode<T> aux = this.findNextInOrder(delete);
            while(aux != null){
                this.setNewEqFactor(aux);
                if(aux.getEqFactor() == 2 || aux.getEqFactor() == -2){
                    aux = rotate(aux); //////////////////////////////////////Maybe aqu?? hay que cambiar algo
                }
                aux = aux.getDad();
            }
            cont--;
        }
    }
    
    public AVLNode<T> findNextInOrder(AVLNode<T> node){
        if(node == null)
            return null;
        if(node.getRight() == null)
            return null;
        AVLNode<T> aux, actual;
        aux = node.getRight();
        if(aux.getLeft() == null)
            return aux;
        actual = aux.getLeft();
        while(actual != null){
            aux = actual;
            actual = actual.getLeft();
        }
        return aux;
    }
    
    public AVLNode<T> rotate(AVLNode<T> actual){
        //Izquierda-Izquierda
        if(actual.getEqFactor() == -2 && actual.getLeft().getEqFactor() == -1){
            AVLNode<T> alfa, beta, gamma, A, B, C, D, dad;
            alfa = actual;
            beta = alfa.getLeft();
            gamma = beta.getLeft();
            A = gamma.getLeft();
            B = gamma.getRight();
            C = beta.getRight();
            D = alfa.getRight();
            dad = actual.getDad();
            if(A != null)
                gamma.hang(A);
            else
                gamma.setLeft(null);
            if(B != null)
                gamma.hang(B);
            else
                gamma.setRight(null);
            if(C != null)
                alfa.hang(C);
            else
                alfa.setLeft(null);
            if(D != null)
                alfa.hang(D);
            else
                alfa.setRight(null);
            beta.hang(gamma);
            beta.hang(alfa);
            
            if(dad != null)
                dad.hang(beta);
            else{
                beta.setDad(null);
                root = beta;
            }
            //TODO: Actualizar factores de equilibrio
            
            this.setNewEqFactor(alfa);
            this.setNewEqFactor(gamma);
            this.setNewEqFactor(beta);
            
            
            return beta;
        }
        //Derecha-Derecha
        if(actual.getEqFactor() >= 2 && actual.getRight().getEqFactor() >= 0){
            AVLNode<T> alfa, beta, gamma, A, B, C, D, dad;
            alfa = actual;          
            beta = alfa.getRight(); 
            gamma = beta.getRight();
            A = alfa.getLeft();  
            B = beta.getLeft();  
            C = gamma.getLeft(); 
            D = gamma.getRight();
            dad = actual.getDad();
            if(A != null)
                alfa.hang(A);
            else
                alfa.setLeft(null);
            if(B != null)
                alfa.hang(B);
            else
                alfa.setRight(null);
            if(C != null)
                gamma.hang(C);
            else
                gamma.setLeft(null);
            if(D != null)
                gamma.hang(D);
            else
                gamma.setRight(null);
            beta.hang(alfa);
            beta.hang(gamma);
            if(dad != null)
                dad.hang(beta);
            else{
                beta.setDad(null);
                root = beta;
            }    
            //TODO: Actualizar factores de equilibrio
            this.setNewEqFactor(alfa);
            this.setNewEqFactor(gamma);
            this.setNewEqFactor(beta);
            
            
            return beta;
        }
        
        
        //Izquierda-Derecha
        if(actual.getEqFactor() == -2 && actual.getLeft().getEqFactor() >= 0){
            AVLNode<T> alfa, beta, gamma, A, B, C, D, dad;
            alfa = actual;
            beta = alfa.getLeft();
            gamma = beta.getRight();
            A = beta.getLeft();
            B = gamma.getLeft();
            C = gamma.getRight();
            D = alfa.getRight();
            dad = actual.getDad();
            if(A != null)
                beta.hang(A);
            else
                beta.setLeft(null);
            if(B != null)
                beta.hang(B);
            else
                beta.setRight(null);
            if(C != null)
                alfa.hang(C);
            else
                alfa.setLeft(null);
            if(D != null)
                alfa.hang(D);
            else
                alfa.setRight(null);
            gamma.hang(beta);
            gamma.hang(alfa);
            if(dad != null)
                dad.hang(gamma);
            else{
                gamma.setDad(null);
                root = gamma;
            }
            //TODO: Actualizar factores de equilibrio
            this.setNewEqFactor(alfa);
            this.setNewEqFactor(gamma);
            this.setNewEqFactor(beta);
            
            return gamma;
        }
        
        
        //Derecha-Izquierda
        if(actual.getEqFactor() == 2 && actual.getRight().getEqFactor() == -1){
            AVLNode<T> alfa, beta, gamma, A, B, C, D, dad;
            alfa = actual;
            dad = actual.getDad();
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
            if(dad != null)
                dad.hang(gamma);
            else{
                gamma.setDad(null);
                root = gamma;
            }
            //Falta actualizar factores de equilibrio
            
            this.setNewEqFactor(gamma);
            this.setNewEqFactor(gamma.getLeft());
            this.setNewEqFactor(gamma.getRight());
            
            return gamma;
            
            
            
        }
        return null;
    }
    
    private void setNewEqFactor(AVLNode<T> node){
        if(node == null)
            return;
        int lH, rH;
        if(node.getLeft() != null)
            lH = this.getHeight(node.getLeft())+1;
        else
            lH = 0;
        if(node.getRight() != null)
            rH = this.getHeight(node.getRight())+1;
        else
            rH = 0;
        node.setEqFactor(rH - lH);
        
    }
    
    private void updateAllHeights(AVLNode<T> node){
        if(node == null)
            return;
        node = node.getDad();
        while(node != null){
            node.setHeight(this.getHeight(node));
            node = node.getDad();
        }
    }
    
}
