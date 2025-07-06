package aed;

public class Heap<T extends Transaccion> {
    private T[] nodos;
    private int[] heap;
    private int longitud;

    public Heap(T[] array){
        this.longitud = array.length;
        this.nodos = array;        
        this.heap = new int[this.longitud];
        for(int i = 0; i < this.longitud; i ++){
            this.heap[i] = this.nodos[i].id();
        }
        this.heap = this.heapify();
    }

    public T obtenerRaiz(){
        if (this.longitud == 0) {
            return null;
        }

        T raiz = nodos[heap[0]];
        return raiz;
    }

    private int[] heapify(){
        int ultimoPadre = (this.longitud - 2) / 2;
        // O(n)
        for (int i = ultimoPadre; i >= 0; i--) {
            this.siftDown(this.nodos, this.longitud, i);
        }

        return this.heap;
    }

    private void siftDown(T[] array, int longitud, int i) {
        int masGrande = i;
        int hijoIzq = 2 * i + 1;
        int hijoDer = 2 * i + 2;

        if (hijoIzq < longitud && array[heap[hijoIzq]].compareTo(array[heap[masGrande]]) > 0) {
            masGrande = hijoIzq;
        }

        if (hijoDer < longitud && array[heap[hijoDer]].compareTo(array[heap[masGrande]]) > 0) {
            masGrande = hijoDer;
        }

        if (masGrande != i) {
            this.swap(i, masGrande, array);
            siftDown(array, longitud, masGrande);
        }
    }

    private void swap(int i, int j, T[] array) {
        int idI = heap[i];
        int idJ = heap[j];

        heap[i] = idJ;
        heap[j] = idI;
    }

    public void eliminarRaiz(){
        // O(log n)
         if (this.longitud == 0) {
            return;
        }

        T root = nodos[heap[0]];
        int lastIndex = this.longitud - 1;

        heap[0] = heap[lastIndex];
        nodos[root.id()] = null;

        this.longitud--;

        siftDown(nodos, longitud, 0);
    }

    public int longitud(){
        return this.longitud;
    }

    public T[] toArray(){
        // ?????        
        return this.nodos;
    }
}
