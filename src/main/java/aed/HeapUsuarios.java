package aed;

public class HeapUsuarios {
    private final Node[] nodos;
    private final int[] heap;

    class Node {
        int saldo = 0;
        int id;
        int heapIndex;

        Node(int id, int heapIndex) {
            this.id = id;
            this.heapIndex = heapIndex;
        }
    }

    public HeapUsuarios(int longitud) {
        // + 1 porque las ids empiezan en 1
        this.nodos = new Node[longitud + 1];
        this.heap = new int[longitud];

        for (int i = 1; i <= longitud; i++) {
            int heapIndex = i - 1; // el heap empieza en 0, por eso i - 1
            Node nodo = new Node(i, heapIndex);

            nodos[i] = nodo;
            heap[heapIndex] = i;
        }
    }

    private void siftDown(int padreActual) {
        int longitud = heap.length;

        while (true) {
            int nuevoPadre = padreActual;
            int hijoIzq = 2 * padreActual + 1;
            int hijoDer = 2 * padreActual + 2;

            if (hijoIzq < longitud) {
                Node nodoIzq = nodos[heap[hijoIzq]];
                Node nodoNuevoPadre = nodos[heap[nuevoPadre]];

                if (nodoIzq.saldo > nodoNuevoPadre.saldo
                        || (nodoIzq.saldo == nodoNuevoPadre.saldo && nodoIzq.id < nodoNuevoPadre.id))
                    nuevoPadre = hijoIzq;
            }

            if (hijoDer < longitud) {
                Node nodoDer = nodos[heap[hijoDer]];
                Node nodoNuevoPadre = nodos[heap[nuevoPadre]];

                if (nodoDer.saldo > nodoNuevoPadre.saldo
                        || (nodoDer.saldo == nodoNuevoPadre.saldo && nodoDer.id < nodoNuevoPadre.id))
                    nuevoPadre = hijoDer;
            }

            if (nuevoPadre == padreActual)
                break;

            swap(padreActual, nuevoPadre);
            padreActual = nuevoPadre;
        }
    }

    private void siftUp(int nodoActual) {
        while (nodoActual > 0) {
            int padre = (nodoActual - 1) / 2;

            if (nodos[heap[nodoActual]].saldo < nodos[heap[padre]].saldo
                    || (nodos[heap[nodoActual]].saldo == nodos[heap[padre]].saldo
                            && nodos[heap[nodoActual]].id > nodos[heap[padre]].id))
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

        nodos[idI].heapIndex = j;
        nodos[idJ].heapIndex = i;
    }

    private void actualizarSaldo(Node nodo, int cantidad) {
        int pos = nodo.heapIndex;
        int previo = nodo.saldo;

        nodo.saldo += cantidad;

        if (nodo.saldo > previo) {
            siftUp(pos);
        } else if (nodo.saldo < previo) {
            siftDown(pos);
        }
    }

    public void incrementarSaldo(int id, int cantidad) {
        if (id < 1 || id >= nodos.length)
            throw new IllegalArgumentException("Id (" + id + ") inválido");

        actualizarSaldo(nodos[id], cantidad);
    }

    public void decrementarSaldo(int id, int cantidad) {
        if (id < 1 || id >= nodos.length)
            throw new IllegalArgumentException("Id (" + id + ") inválido");

        actualizarSaldo(nodos[id], -cantidad);
    }

    public int maximoTenedor() {
        return heap[0];
    }
}
