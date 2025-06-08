package aed;

public class HeapTransacciones {
    private final Node[] nodos;
    private int[] heap;
    private int size = 0;

    class Node {
        int id;
        int heapIndex;
        int monto;

        Node(int id, int heapIndex, int monto) {
            this.id = id;
            this.heapIndex = heapIndex;
            this.monto = monto;
        }
    }

    public HeapTransacciones(Transaccion[] arr) {
        int longitud = arr.length;

        this.nodos = new Node[longitud];
        this.heap = new int[longitud];
        this.size = longitud;

        int ultimoPadre = (longitud - 2) / 2;

        for (int i = ultimoPadre; i >= 0; i--) {
            int hijoIzq = 2 * i + 1, hijoDer = 2 * i + 2;
            Transaccion tr = arr[i];

            Node nodoRaiz = new Node(tr.id(), i, tr.monto());
            nodos[i] = nodoRaiz;
            heap[i] = i;

            if (nodos[hijoIzq] == null) {
                Transaccion hijoIzqTr = arr[hijoIzq];
                Node nodoHijoIzquierdo = new Node(hijoIzqTr.id(), hijoIzq,
                        hijoIzqTr.monto());

                nodos[hijoIzq] = nodoHijoIzquierdo;
                heap[hijoIzq] = hijoIzq;
            }

            // Si o si hay hijo izquierdo, pero puede no haber hijo derecho
            if (hijoDer > arr.length - 1) {
                continue;
            }

            if (nodos[hijoDer] == null) {
                Transaccion hijoDerTr = arr[hijoDer];
                Node nodoHijoDerecho = new Node(hijoDerTr.id(), hijoDer,
                        hijoDerTr.monto());

                nodos[hijoDer] = nodoHijoDerecho;
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
                Node nodoIzq = nodos[heap[hijoIzq]];
                Node nodoNuevoPadre = nodos[heap[nuevoPadre]];

                if (nodoIzq.monto > nodoNuevoPadre.monto
                        || (nodoIzq.monto == nodoNuevoPadre.monto && nodoIzq.id > nodoNuevoPadre.id))
                    nuevoPadre = hijoIzq;
            }

            if (hijoDer < longitud) {
                Node nodoDer = nodos[heap[hijoDer]];
                Node nodoNuevoPadre = nodos[heap[nuevoPadre]];

                if (nodoDer.monto > nodoNuevoPadre.monto
                        || (nodoDer.monto == nodoNuevoPadre.monto && nodoDer.id > nodoNuevoPadre.id))
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

    public void hackear() {
        if (this.size == 0) {
            return;
        }

        Node root = nodos[heap[0]];
        int lastIndex = this.size - 1;

        heap[0] = heap[lastIndex];
        nodos[heap[0]].heapIndex = 0;
        nodos[root.id] = null;
        this.size--;

        siftDown(0);
    }
}
