package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ListaEnlazadaAcotada<NodoHeap> nodos;
    private int[] heap;
    private int longitud;

    private class NodoHeap {
        T dato;
        int heapIndex;

        NodoHeap(T dato, int heapIndex) {
            this.dato = dato;
            this.heapIndex = heapIndex; // Inicialmente no está en el heap
        }
    }

    public Heap(T[] array) {
        int longitud = array.length;
        this.nodos = new ListaEnlazadaAcotada<NodoHeap>(longitud);
        this.heap = new int[longitud];
        this.longitud = longitud;

        for (int i = 0; i < this.longitud; i++) {
            NodoHeap nodo = new NodoHeap(array[i], i);
            this.nodos.agregarAtras(nodo);
            this.heap[i] = i;
        }

        int ultimoPadre = (longitud - 2) / 2;
        for (int i = ultimoPadre; i >= 0; i--)
            siftDown(i);
    }

    public T obtenerRaiz() {
        return nodos.obtener(heap[0]).dato;
    }

    public T obtener(int id) {
        return nodos.obtener(id).dato;
    }

    private void siftDown(int i) {
        int masGrande = i;
        int hijoIzq = 2 * i + 1;
        int hijoDer = 2 * i + 2;

        if (hijoIzq < this.longitud) {
            int idIzq = heap[hijoIzq];
            int idMasGrande = heap[masGrande];
            NodoHeap nodoIzq = nodos.obtener(idIzq);

            if (nodoIzq != null && nodoIzq.dato.compareTo(nodos.obtener(idMasGrande).dato) > 0) {
                masGrande = hijoIzq;
            }
        }

        if (hijoDer < this.longitud) {
            int idDer = heap[hijoDer];
            int idMasGrande = heap[masGrande];
            NodoHeap nodoDer = nodos.obtener(idDer);

            if (nodoDer != null && nodoDer.dato.compareTo(nodos.obtener(idMasGrande).dato) > 0) {
                masGrande = hijoDer;
            }
        }

        if (masGrande == i)
            return;

        this.swap(i, masGrande);
        siftDown(masGrande);
    }

    private void siftUp(int nodoActual) {
        while (nodoActual > 0) {
            int padre = (nodoActual - 1) / 2;
            T nodo = nodos.obtener(heap[nodoActual]).dato;
            T nodoPadre = nodos.obtener(heap[padre]).dato;

            if (nodoPadre.compareTo(nodo) > 0)
                break;

            swap(nodoActual, padre);
            nodoActual = padre;
        }
    }

    private void swap(int i, int j) {
        int idI = heap[i];
        int idJ = heap[j];

        heap[i] = idJ;
        heap[j] = idI;

        nodos.obtener(idI).heapIndex = j;
        nodos.obtener(idJ).heapIndex = i;
    }

    public void modificar(int id, T nuevo) {
        NodoHeap nodoViejo = nodos.obtener(id);
        T viejo = nodoViejo.dato;
        int comparacion = nuevo.compareTo(viejo);
        this.nodos.modificarPosicion(id, new NodoHeap(nuevo, nodoViejo.heapIndex));

        if (comparacion == 0)
            return;

        if (comparacion < 0)
            siftDown(nodoViejo.heapIndex);
        else
            siftUp(nodoViejo.heapIndex);
    }

    public ArrayList<T> toArrayList() {
        ArrayList<T> arr = new ArrayList<T>(this.longitud);
        Iterador<Heap<T>.NodoHeap> iterador = this.nodos.iterador();

        while (iterador.haySiguiente())
            arr.add(iterador.siguiente().dato);

        return arr;
    }

    public T eliminarRaiz() {
        int idUltimo = heap[this.longitud - 1];

        swap(0, this.longitud - 1);
        heap[this.longitud - 1] = -1;
        this.longitud--;
        siftDown(0);

        return nodos.eliminar(idUltimo).dato;
    }

    public int longitud() {
        return this.longitud;
    }
}
