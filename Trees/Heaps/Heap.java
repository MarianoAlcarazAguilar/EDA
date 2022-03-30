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
        return cont-1;
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
    
        
    public void deleteMin(){
        // Cambiamos el primero con el último
        data[1] = data[cont-1];
        cont--;
        
        int i = 1;
        int leftSonIndex = 0, rightSonIndex = 0;
        while(i < cont && leftSonIndex < cont && rightSonIndex < cont){
            // ¿Tiene hijo izquierdo?
            leftSonIndex = i << 1;
            if(leftSonIndex < cont){
                // Si sí tiene hijo izquierdo, continuamos
                // ¿Tiene hijo derecho?
                rightSonIndex = (i << 1) | 1;
                if(rightSonIndex < cont){
                    // Si tiene ambos hijos comparamos para encontrar el menor.
                    int minIndex;
                    if(data[leftSonIndex].compareTo(data[rightSonIndex]) > 0)
                        minIndex = rightSonIndex;
                    else
                        minIndex = leftSonIndex;
                    // ¿Es el hijo menor, menor que yo?
                    if(data[i].compareTo(data[minIndex]) > 0){
                        // Aquí yo soy mayor que mi hijo, entonces los tengo que cambiar
                        // Cambiamos data[i] con data[minIndex] y hacemos la i = minIndex
                        T aux = data[minIndex];
                        data[minIndex] = data[i];
                        data[i] = aux;
                        i = minIndex;
                    }
                } else {
                    // Aquí solo tengo hijo izquierdo
                    // Si no tiene hijo derecho, entonces solo comparamos con el izquierdo
                    // Este sería el ÚLTIMO paso.
                    if(data[i].compareTo(data[leftSonIndex]) > 0){
                        // Aquí soy mayor que mi hijo izquierdo; los tengo que cambiar
                        T aux = data[leftSonIndex];
                        data[leftSonIndex] = data[i];
                        data[i] = aux;
                        i = leftSonIndex;
                    }
                }
            }
        }
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
