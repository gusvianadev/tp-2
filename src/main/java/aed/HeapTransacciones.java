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

        int ultimoPadre = (longitud - 2) / 2;

        for (int i = ultimoPadre; i >= 0; i--) {
            int hijoIzq = 2 * i + 1, hijoDer = 2 * i + 2;

            nodos[i] = arr[i];
            heap[i] = i;

            if (nodos[hijoIzq] == null) {
                nodos[hijoIzq] = arr[hijoIzq];
                heap[hijoIzq] = hijoIzq;
            }

            // Si o si hay hijo izquierdo, pero puede no haber hijo derecho
            if (hijoDer > arr.length - 1) {
                continue;
            }

            if (nodos[hijoDer] == null) {
                nodos[hijoDer] = arr[hijoDer];
                heap[hijoDer] = hijoDer;
            }

            siftDown(i);
        }
    }

    private void siftDown(int padreActual) {
        int longitud = this.size;

        while (true) {
            int nuevoPadre = padreActual;
            int hijoIzq = 2 * padreActual + 1;
            int hijoDer = 2 * padreActual + 2;

            if (hijoIzq < longitud) {
                Transaccion nodoIzq = nodos[heap[hijoIzq]];
                Transaccion nodoNuevoPadre = nodos[heap[nuevoPadre]];

                if (nodoIzq.monto() > nodoNuevoPadre.monto()
                        || (nodoIzq.monto() == nodoNuevoPadre.monto() && nodoIzq.id() > nodoNuevoPadre.id()))
                    nuevoPadre = hijoIzq;
            }

            if (hijoDer < longitud) {
                Transaccion nodoDer = nodos[heap[hijoDer]];
                Transaccion nodoNuevoPadre = nodos[heap[nuevoPadre]];

                if (nodoDer.monto() > nodoNuevoPadre.monto()
                        || (nodoDer.monto() == nodoNuevoPadre.monto() && nodoDer.id() > nodoNuevoPadre.id()))
                    nuevoPadre = hijoDer;
            }

            if (nuevoPadre == padreActual)
                break;

            swap(padreActual, nuevoPadre);
            padreActual = nuevoPadre;
        }
    }

    // capaz termina no siendo necesario
    // private void siftUp(int nodoActual) {
    // while (nodoActual > 0) {
    // int padre = (nodoActual - 1) / 2;
    //
    // if (nodos[heap[nodoActual]].monto < nodos[heap[padre]].monto
    // || (nodos[heap[nodoActual]].monto == nodos[heap[padre]].monto
    // && nodos[heap[nodoActual]].id < nodos[heap[padre]].id))
    // break;
    //
    // swap(nodoActual, padre);
    // nodoActual = padre;
    // }
    // }

    private void swap(int i, int j) {
        int idI = heap[i];
        int idJ = heap[j];

        heap[i] = idJ;
        heap[j] = idI;

        nodos[idI].heapIndex = j;
        nodos[idJ].heapIndex = i;
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

        siftDown(0);
    }
}
