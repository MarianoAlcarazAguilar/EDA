package Algoritmos;

import java.util.Random;

/**
 * Esta clase incluye los métodos de ordenamiento siguientes:
 *  Selection Sort
 *  Insertion Sort
 *  Bubble Sort
 *  Quick Sort
 *  Merge Sort
 * 
 * @author Mariano Alcaraz Aguilar
 */

public class Sort {
    
    public int comparisons;
    public int swaps;
    
    public Sort(){
        comparisons = 0;
        swaps = 0;
    }
    
    public void reset(){
        comparisons = 0;
        swaps = 0;
    }
    
    /**
     * Método AUXILIAR para cambiar dos posiciones en un arreglo.
     *
     */
    private static <T> void swap(T[] array, int pos1, int pos2){
        if(array == null || pos1 < 0 || pos2 < 0 || pos1 > array.length || pos2 > array.length)
            throw new RuntimeException("Array nulo o fuera de límites a mover");
        
        T aux = array[pos2];
        array[pos2] = array[pos1];
        array[pos1] = aux;
    }
    
    
    /**
     * Método AUXILIAR para encontrar la posición del elemento más pequeño en un arreglo.
     *
     */
    private <T extends Comparable<T>> int findPosMin(T[] array, int startPos){
        if(array == null || startPos < 0 || startPos > array.length)
            throw new RuntimeException("Array is null in findPosMin or startPos out of boudries");
        
        int pos = startPos;
        for(int i = startPos; i < array.length; i++){
            comparisons++;
            if(array[i].compareTo(array[pos]) < 0)
                pos = i;
        }
        return pos;
    }
    
    
    /**
     * Método AUXILIAR que regresa la posición adecuada del primer elemento.
     * Pone todos los elementos menores a la izquierda y los más grandes a la derecha.
     * MAX SE INCLUYE.
     *
     */
    private <T extends Comparable<T>> int partition(T[] array, int min, int max){
        if(array == null)
            throw new RuntimeException("Array in partition is null");
        
        int posMin = min;
        int i;
        
        i = min + 1;
        while(i <= max){
            comparisons++;
            if(array[i].compareTo(array[i-1]) > 0){
                swaps++;
                swap(array,i,max);
                max--;
            } else {
                swaps++;
                swap(array,i, i-1);
                i++;
                posMin++;
            }
        }
        return posMin;
    }
    
    /**
     * Método AUXILIAR que junta dos arreglos ordenados en otro arreglo ordenado.
     * Sirve para aplicar el algoritmo de Merge Sort.
     *
     */
    private <T extends Comparable<T>> void merge(T[] inputArray, T[] leftHalf, T[] rightHalf){
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;
        
        int i = 0;
        int j = 0;
        int k = 0;
        
        while(i < leftSize && j < rightSize){
            comparisons++;
            if(leftHalf[i].compareTo(rightHalf[j]) <= 0){
                swaps++;
                inputArray[k] = leftHalf[i];
                i++;
            } else {
                swaps++;
                inputArray[k] = rightHalf[j];
                j++;
            }
            k++;
        }
        
        //At this point either leftHalf or rightHalf is empty
        
        while(i < leftSize){
            swaps++;
            inputArray[k] = leftHalf[i];
            i++;
            k++;
        }
        while(j < rightSize){
            swaps++;
            inputArray[k] = rightHalf[j];
            j++;
            k++;
        }
    }
    
    
    /**
     * Método de ordenamiento usando Quick Sort.
     * O(nlog(n))
     *
     * @param <T>
     * @param array
     */
    public <T extends Comparable<T>> void quick_sort(T[] array){
        if(array == null)
            throw new RuntimeException("Array in quick_sort is null");
        
        quick_sort(array,0,array.length-1);
    } 
    
    
    /**
     * Método de ordenamiento usando selección directa.
     * O(n^2)
     *
     * @param <T>
     * @param array
     */
    public <T extends Comparable<T>> void selection_sort(T[] array){
        if(array == null)
            throw new RuntimeException("Array is null in seleccionDirecta");
        
        int minPos;
        for(int i = 0; i < array.length; i++){
            minPos = findPosMin(array,i);
            swaps++;
            swap(array,i,minPos);
        }  
    }
    
    
    /**
     * Método de ordenamiento usando Insertion Sort.
     * O(n^2)
     *
     * @param <T>
     * @param array
     */
    public <T extends Comparable<T>> void insertion_sort(T[] array){
        if(array == null)
            throw new RuntimeException("Array in order_insertion is null");
        
        int i, j;
        
        for(i = 1; i < array.length; i++){
            j = i;
            
            comparisons++;
            while(j > 0 && array[j].compareTo(array[j-1]) < 0){
                comparisons++;
                swaps++;
                swap(array,j,j-1);
                j--;
            }
        }
    }
    
    
    /**
     * Método de ordenamiento usando Bubble Sort
     * O(n^2)
     *
     * @param <T>
     * @param array
     */
    public <T extends Comparable<T>> void bubble_sort(T[] array){
        if(array == null)
            throw new RuntimeException("Array in bubble_sort is null");
        
        for(int j = array.length-1; j > 0; j--){
            for(int i = 0; i < j; i++){
                comparisons++;
                if(array[i].compareTo(array[i+1]) > 0){
                    swaps++;
                    swap(array,i,i+1);
                }
            }
        }
    }
    
    
    /**
     * Método recursivo de ordenamiento usando Quick Sort.
     * El usuario accede a este método mediante el método con el mismo nombre más arriba.
     *
     */
    private <T extends Comparable<T>> void quick_sort(T[] array, int min, int max){
        if(min >= max)
            return;
        
        int pos_pivote = partition(array, min, max);        
        quick_sort(array, min, pos_pivote-1);
        quick_sort(array, pos_pivote+1, max);
    }
    
    /**
     * Método recursivo de ordenamiento usando Merge Sort.
     * O(nlog(n)))
     *
     * @param <T>
     * @param inputArray
     */
    public <T extends Comparable<T>> void merge_sort(T[] inputArray){
        int inputLength = inputArray.length;
        
        if(inputLength < 2)
            return;
        
        int midIndex = inputLength / 2;
        T[] leftHalf = (T[]) new Comparable[midIndex];
        T[] rightHalf = (T[]) new Comparable[inputLength - midIndex];
        
        
        for(int i = 0; i < midIndex; i++){
            swaps++;
            leftHalf[i] = inputArray[i];
        }
        for(int i = midIndex; i < inputLength; i++){
            swaps++;
            rightHalf[i - midIndex] = inputArray[i];
        }
        merge_sort(leftHalf);
        merge_sort(rightHalf);
        
        //Merge
        merge(inputArray, leftHalf, rightHalf);
    }
    
    /**
     * Shuffles an array.
     * @param <T>
     * @param array
     */
    public static <T> void shuffle(T[] array){
        int rand;
        for(int i = 0; i < array.length; i++){
            rand = rand_number_between(i,array.length);
            swap(array, i, rand);
        }
    }
    
    /**
     * Retuns a random int between low(inclusive) and high(exclusive)
     */
    private static int rand_number_between(int low, int high){
        Random r = new Random();
        if(low > high)
            throw new RuntimeException("low is greater than high in rand_number_between");
        
        return r.nextInt(high - low) + low;
    }

}
