package aed;

public class HeapTransacciones {
    private Transaccion[] nodos;
    private int[] heap;
    private int size = 0;

    public HeapTransacciones(Transaccion[] arr) {
        int longitud = arr.length;

        this.nodos = new Transaccion[longitud];
        this.heap = new int[longitud];
        this.size = longitud;

        for (int i = 0; i < longitud; i++) {
            nodos[i] = arr[i];
            heap[i] = i;
            arr[i].heapIndex = i;
        }

        int ultimoPadre = (longitud - 2) / 2;

        for (int i = ultimoPadre; i >= 0; i--) {
            siftDown(nodos, longitud, i);
        }
    }

    // Este está acá porque no estoy seguro si el primero es O(n) o O(n log n)
    // public HeapTransaccionesAlt(Transaccion[] arr) {
    // int longitud = arr.length;
    //
    // this.nodos = new Transaccion[longitud];
    // this.heap = new int[longitud];
    // this.size = longitud;
    //
    // int ultimoPadre = (longitud - 2) / 2;
    //
    // for (int i = ultimoPadre; i >= 0; i--) {
    // int hijoIzq = 2 * i + 1, hijoDer = 2 * i + 2;
    //
    // nodos[i] = arr[i];
    // heap[i] = i;
    //
    // if (nodos[hijoIzq] == null) {
    // nodos[hijoIzq] = arr[hijoIzq];
    // heap[hijoIzq] = hijoIzq;
    // }
    //
    // // Si o si hay hijo izquierdo, pero puede no haber hijo derecho
    // if (hijoDer > arr.length - 1) {
    // continue;
    // }
    //
    // if (nodos[hijoDer] == null) {
    // nodos[hijoDer] = arr[hijoDer];
    // heap[hijoDer] = hijoDer;
    // }
    //
    // siftDown(i);
    // }
    // }

    private void siftDown(Transaccion[] array, int longitud, int i) {
            int masGrande = i;
            int hijoIzq = 2 * i + 1;
            int hijoDer = 2 * i + 2;

            if (hijoIzq < size && array[heap[hijoIzq]].compareTo(array[heap[masGrande]]) > 0) {
                masGrande = hijoIzq;
            }

            if (hijoDer < size && array[heap[hijoDer]].compareTo(array[heap[masGrande]]) > 0) {
                masGrande = hijoDer;
            }

            if (masGrande != i){
                swap(i, masGrande, array);
                siftDown(array, longitud, masGrande);
            }
    }

    // capaz termina no siendo necesario
    // private void siftUp(int i) {
    // while (i > 0) {
    // int parent = (i - 1) / 2;
    // if (nodos[heap[i]].compareTo(nodos[heap[parent]]) <= 0)
    // break;
    // swap(i, parent);
    // i = parent;
    // }
    // }

    private void swap(int i, int j, Transaccion[] array) {
        int idI = heap[i];
        int idJ = heap[j];

        heap[i] = idJ;
        heap[j] = idI;

        array[idI].heapIndex = j;
        array[idJ].heapIndex = i;
    }

    public Transaccion[] toArray() {
        Transaccion[] transacciones = new Transaccion[this.size];
        int i = 0;

        for (Transaccion tr : this.nodos) {
            if (tr == null) {
                continue;
            }

            transacciones[i] = tr;
            i++;
        }

        return transacciones;
    }

    public void hackear() {
        if (this.size == 0) {
            return;
        }

        Transaccion root = nodos[heap[0]];
        int lastIndex = this.size - 1;

        heap[0] = heap[lastIndex];
        nodos[heap[0]].heapIndex = 0;
        nodos[root.id()] = null;
        this.size--;

        siftDown(nodos, size, 0);
    }

    class Handle {
        public int id;

        public Handle(int id) {
            this.id = id;
        }
    }

    public Handle raiz() {
        if (this.size == 0) {
            return null;
        }

        return new Handle(nodos[heap[0]].id());
    }

    public int size() {
        return this.size;
    }
}
