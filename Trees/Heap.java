package Heaps;


public class Heap<T extends Comparable<T>> implements HeapADT<T> {
    
    private T[] data;
    private int cont;
    private static int MAX;
    
    public Heap(){
        MAX = 100;
        cont = 1;
        data = (T[]) new Comparable[MAX];
    }
    
    public T getMin(){
        if(cont >= 1)
            return data[1];
        return null;
    }
    
    public int size(){
        return cont--;
    }
    
    public int getLevels(){
        return (int) Math.ceil(Math.log(cont)/Math.log(2));
    }
    
    public void insert(T element){
        if(cont < MAX){
            data[cont] = element;
            //Revisa que esté en la posición correcta
            int aux = cont;
            int dad = aux;
            while(aux > 1){
                dad = aux >> 1;
                if(data[dad].compareTo(data[aux]) < 0)
                    break;
                //Cambiamos las posiciones
                T change = data[dad];
                data[dad] = data[aux];
                data[aux] = change;
                aux = dad;
            }
            cont++;
            return;
        }
        //Expandimos tamaño
        expand();
        insert(element);
    }
    
    private void expand(){
        MAX = 2*MAX;
        T[] aux = (T[]) new Comparable[MAX];
        //Copiamos datos
        for(int i = 1; i < cont; i++)
            aux[i] = data[i];
        data = aux;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= this.getLevels(); i++){
            sb.append(printGivenLevel(i));
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private String printGivenLevel(int level){
        StringBuilder sb = new StringBuilder();
        int start = (int) Math.pow(2, level - 1);
        for(int i = start ; i < cont; i++){
            if(i >> level == 0){
                sb.append(data[i]);
                sb.append(" ");
            }
        }
        return sb.toString();
    }    
    
}
