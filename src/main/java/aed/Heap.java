package aed;

public class Heap<T implements NodoHeap> {
    private ListaEnlazadaAcotada<T> nodos;
    private int[] heap;
    private int longitud;

    public Heap(T[] array) {
        int longitud = array.length;
        this.nodos = ListaEnlazadaAcotada<T>(longitud);
        this.heap = new int[longitud];
        this.longitud = longitud;

        for(int i = 0; i < this.longitud; i++) {
            this.heap[i] = i;
            this.nodos.agregarAtras(array[i]);
        }

        int ultimoPadre = (longitud - 2) / 2;
        for (int i = ultimoPadre; i >= 0; i--) siftDown(i);
    }

    public T obtenerRaiz(){
        return nodos.obtener(heap[0]);
    }

    private void siftDown(int i) {
        int masGrande = i;
        int hijoIzq = 2 * i + 1;
        int hijoDer = 2 * i + 2;

        if (hijoIzq < this.longitud && elems.obtener(heap[hijoIzq]).compareTo(elems.obtener(heap[masGrande])) > 0) {
            masGrande = hijoIzq;
        }

        if (hijoDer < this.longitud && elems.obtener(heap[hijoDer]).compareTo(elems.obtener(heap[masGrande])) > 0) {
            masGrande = hijoDer;
        }

        if (masGrande == i) return;

        this.swap(i, masGrande);
        siftDown(masGrande);
    }

    private void siftUp(int nodoActual) {
        while (nodoActual > 0) {
            int padre = (nodoActual - 1) / 2;
            T nodo = nodos.obtener(heap[nodoActual]);
            T nodoPadre = nodos.obtener(heap[padre]);

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
    }

    public T modificar(int id, T nuevo) {
        return this.elems.modificarPosicion(id, T);
    }

    public T[] toArray() {
        T[] arr = new T[this.size];
        ListaIterador iterador = this.elems.iterador();

        while (iterador.haySiguiente()) 
            transacciones[i] = iterador.siguiente();

        return arr;
    }

    public void eliminarRaiz() {
        elems.eliminar(heap[0]); 
        heap[0] = heap[heap.length - 1];

        siftDown(nodos, longitud, 0);
    }

    public int longitud() {
        return this.longitud;
    }
}
