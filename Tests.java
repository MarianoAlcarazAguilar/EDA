package TareaPeliculas;

import java.io.*;
import java.util.*;
import Algoritmos.Sort;

public class Tests {
    
    private static Scanner x;
    private static Pelicula[] movies = new Pelicula[12225];
    
    /**
     * Opens the file titles2.csv
     */
    private static void openFile(){
        
        try{
            x = new Scanner(new File("titles2.csv"));
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        
    }
    
    /**
     * Reads the file and uploads the data in the array with movies.
     */
    private static void uploadData(){
        
        int i = 0;
        while(x.hasNext()){
            String st = x.nextLine();
            String[] data = st.split(",");
            String mins = data[0];
            String year = data[1];
            String name = data[2];
            Integer aa = Integer.parseInt(mins);
            Integer bb = Integer.parseInt(year);
            Pelicula pelicula = new Pelicula(aa,bb,name);
            movies[i] = pelicula;
            i++;
        }
    }
    
    private static Pelicula[] randomArray(int size){
        Pelicula[] resp = new Pelicula[size];
        Sort.shuffle(movies);
        for(int i = 0; i < size; i++)
            resp[i] = movies[i];
        
        return resp;
    }
    
    private static Pelicula[] orderedArray(int size){
        Pelicula[] resp = new Pelicula[size];
        Sort aux = new Sort();
        aux.merge_sort(movies);
        for(int i = 0; i < size; i++)
            resp[i] = movies[i];
        return resp;
    }
    
    private static Pelicula[] inverseArray(int size){
        Pelicula[] resp = new Pelicula[size];
        Sort aux = new Sort();
        aux.merge_sort(movies);
        Stack<Pelicula> stack = new Stack<>();
        for(int i = 0; i < 12225 ; i++)
            stack.push(movies[i]);
        int j = 0;
        while(!stack.isEmpty() && j < size){
            resp[j] = stack.pop();
            j++;
        }
        return resp;
    }
    
    private static void createFile(String title, String text){
        try{
            try (PrintWriter pw = new PrintWriter(new File(title+".csv"))) {
                pw.write(text);
                pw.close();
                System.out.println("Se ha creado el archivo " + title + ".csv");
            }
        }catch (FileNotFoundException e) {
            System.out.println("Something went wrong when writing file");
        }
    }
    
    
    //Kosarajus algorithm for topological sort
    public static void main(String[] args) {
        
        openFile();
        uploadData();
        
        //SELECTION SORT
        Sort selectionSort = new Sort();
        StringBuilder sb = new StringBuilder();
        sb.append("datos");
        sb.append(",");
        sb.append("comparaciones");
        sb.append(",");
        sb.append("tiempo");
        sb.append("\n");
        
        System.out.println("SELECTION SORT DATOS ORDENADOS");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] selectionSortOrdenados = orderedArray(n);
            selectionSort.reset();
            long start = System.currentTimeMillis();
            selectionSort.selection_sort(selectionSortOrdenados);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            sb.append(n);
            sb.append(",");
            sb.append(selectionSort.comparisons);
            sb.append(",");
            sb.append(totalTime);
            sb.append("\n");
        }
        createFile("selectionSortOrdenados",sb.toString());
        sb.setLength(0);
        
        sb.append("datos");
        sb.append(",");
        sb.append("comparaciones");
        sb.append(",");
        sb.append("tiempo");
        sb.append("\n");
        
        System.out.println("\nSELECTION SORT DATOS ORDEN INVERSO");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] selectionSortOrdenados = inverseArray(n);
            selectionSort.reset();
            long start = System.currentTimeMillis();
            selectionSort.selection_sort(selectionSortOrdenados);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            sb.append(n);
            sb.append(",");
            sb.append(selectionSort.comparisons);
            sb.append(",");
            sb.append(totalTime);
            sb.append("\n");
        }
        createFile("selectionSortInverso",sb.toString());
        sb.setLength(0);
        
        sb.append("datos");
        sb.append(",");
        sb.append("comparaciones");
        sb.append(",");
        sb.append("tiempo");
        sb.append("\n");
        
        System.out.println("\nSELECTION SORT DATOS AL AZAR");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] selectionSortOrdenados = randomArray(n);
            selectionSort.reset();
            long start = System.currentTimeMillis();
            selectionSort.selection_sort(selectionSortOrdenados);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            sb.append(n);
            sb.append(",");
            sb.append(selectionSort.comparisons);
            sb.append(",");
            sb.append(totalTime);
            sb.append("\n");
        }
        createFile("selectionSortRandom",sb.toString());
        sb.setLength(0);
        
        sb.append("datos");
        sb.append(",");
        sb.append("comparaciones");
        sb.append(",");
        sb.append("tiempo");
        sb.append("\n");
        
        //INSERTION SORT
        Sort order = new Sort();
        
        System.out.println("\nINSERTION SORT DATOS ORDENADOS");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = orderedArray(n);
            order.reset();
            long start = System.currentTimeMillis();
            order.insertion_sort(datos);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            sb.append(n);
            sb.append(",");
            sb.append(selectionSort.comparisons);
            sb.append(",");
            sb.append(totalTime);
            sb.append("\n");
        }
        createFile("insertionSortOrdenados",sb.toString());
        sb.setLength(0);
        
        sb.append("datos");
        sb.append(",");
        sb.append("comparaciones");
        sb.append(",");
        sb.append("tiempo");
        sb.append("\n");
        
        System.out.println("\nINSERTION SORT DATOS ORDEN INVERSO");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = inverseArray(n);
            order.reset();
            long start = System.currentTimeMillis();
            order.insertion_sort(datos);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            sb.append(n);
            sb.append(",");
            sb.append(selectionSort.comparisons);
            sb.append(",");
            sb.append(totalTime);
            sb.append("\n");
        }
        createFile("insertionSortInverso",sb.toString());
        sb.setLength(0);
        
        sb.append("datos");
        sb.append(",");
        sb.append("comparaciones");
        sb.append(",");
        sb.append("tiempo");
        sb.append("\n");
        
        System.out.println("\nINSERTION SORT DATOS AL AZAR");
        System.out.println("Datos, comparaciones, tiempo");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = randomArray(n);
            order.reset();
            long start = System.currentTimeMillis();
            order.insertion_sort(datos);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            sb.append(n);
            sb.append(",");
            sb.append(selectionSort.comparisons);
            sb.append(",");
            sb.append(totalTime);
            sb.append("\n");
        }
        createFile("insertionSortRandom",sb.toString());
        sb.setLength(0);
        
        sb.append("datos");
        sb.append(",");
        sb.append("comparaciones");
        sb.append(",");
        sb.append("tiempo");
        sb.append("\n");
        
        
        //BUBBLE SORT
        
        System.out.println("\nBUBBLE SORT DATOS ORDENADOS");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = orderedArray(n);
            order.reset();
            long start = System.currentTimeMillis();
            order.bubble_sort(datos);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            sb.append(n);
            sb.append(",");
            sb.append(selectionSort.comparisons);
            sb.append(",");
            sb.append(totalTime);
            sb.append("\n");
        }
        
        System.out.println("\nBUBBLE SORT DATOS ORDEN INVERSO");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = inverseArray(n);
            order.reset();
            long start = System.currentTimeMillis();
            order.bubble_sort(datos);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            System.out.println(n + ", " + order.comparisons + ", " + totalTime);
        }
        
        System.out.println("\nBUBBLE SORT DATOS AL AZAR");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = randomArray(n);
            order.reset();
            long start = System.currentTimeMillis();
            order.bubble_sort(datos);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            System.out.println(n + ", " + order.comparisons + ", " + totalTime);
        }
        
        
        
        //QUICK SORT
        
        System.out.println("\nQUICK SORT DATOS ORDENADOS");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = orderedArray(n);
            order.reset();
            long start = System.nanoTime();
            order.quick_sort(datos);
            long end = System.nanoTime();
            long totalTime = (end - start)/1000000;
            System.out.println(n + ", " + order.comparisons + ", " + totalTime);
        }
        
        System.out.println("\nQUICK SORT DATOS ORDEN INVERSO");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = inverseArray(n);
            order.reset();
            long start = System.currentTimeMillis();
            order.quick_sort(datos);
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            System.out.println(n + ", " + order.comparisons + ", " + totalTime);
        }
        
        System.out.println("\nQUICK SORT DATOS AL AZAR");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = randomArray(n);
            order.reset();
            long start = System.nanoTime();
            order.quick_sort(datos);
            long end = System.nanoTime();
            long totalTime = (end - start)/1000000;
            System.out.println(n + ", " + order.comparisons + ", " + totalTime);
        }
        
        
        //MERGE SORT
        
        System.out.println("\nMERGE SORT DATOS ORDENADOS");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = orderedArray(n);
            order.reset();
            long start = System.nanoTime();
            order.merge_sort(datos);
            long end = System.nanoTime();
            long totalTime = (end - start)/1000000;
            System.out.println(n + ", " + order.comparisons + ", " + totalTime);
        }
        
        System.out.println("\nMERGE SORT DATOS ORDEN INVERSO");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = inverseArray(n);
            order.reset();
            long start = System.nanoTime();
            order.merge_sort(datos);
            long end = System.nanoTime();
            long totalTime = (end - start)/1000000;
            System.out.println(n + ", " + order.comparisons + ", " + totalTime);
        }
        
        System.out.println("\nMERGE SORT DATOS AL AZAR");
        for(int n = 100; n <= 12200; n = n+1000){
            Pelicula[] datos = randomArray(n);
            order.reset();
            long start = System.nanoTime();
            order.merge_sort(datos);
            long end = System.nanoTime();
            long totalTime = (end - start)/1000000;
            System.out.println(n + ", " + order.comparisons + ", " + totalTime);
        }
        
    }

}
