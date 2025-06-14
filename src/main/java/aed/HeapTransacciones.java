package aed;

public class HeapTransacciones {
    private Nodo[] nodos;
    private int[] heap;
    private int size = 0;
    private int sumaMontos = 0;
    private int cantidadTr = 0;

    class Nodo extends Transaccion {
        Nodo(int id, int id_comprador, int id_vendedor, int monto) {
            super(id, id_comprador, id_vendedor, monto);
        }
    }

    // O(n) ⊆ O(n*log(P))
    public HeapTransacciones(Transaccion[] arr, HeapUsuarios heapUsuarios) {
        int longitud = arr.length;
        this.nodos = new Nodo[longitud];
        this.heap = new int[longitud];
        this.size = longitud;

        // O(n*log(P))
        for (int i = 0; i < longitud; i++) {
            Transaccion tr = arr[i];

            nodos[i] = new Nodo(tr.id(), tr.id_comprador(), tr.id_vendedor(), tr.monto());
            heap[i] = i;

            if (tr.esCreacion()) {
                heapUsuarios.incrementarSaldo(tr.id_vendedor(), tr.monto());
            } else {
                heapUsuarios.decrementarSaldo(tr.id_comprador(), tr.monto());
                heapUsuarios.incrementarSaldo(tr.id_vendedor(), tr.monto());

                sumaMontos += tr.monto();
                cantidadTr++;
            }

        }

        int ultimoPadre = (longitud - 2) / 2;

        // O(n)
        for (int i = ultimoPadre; i >= 0; i--) {
            siftDown(nodos, longitud, i);
        }
    }

    private void siftDown(Nodo[] array, int longitud, int i) {
        int masGrande = i;
        int hijoIzq = 2 * i + 1;
        int hijoDer = 2 * i + 2;

        if (hijoIzq < size && array[heap[hijoIzq]].compareTo(array[heap[masGrande]]) > 0) {
            masGrande = hijoIzq;
        }

        if (hijoDer < size && array[heap[hijoDer]].compareTo(array[heap[masGrande]]) > 0) {
            masGrande = hijoDer;
        }

        if (masGrande != i) {
            swap(i, masGrande, array);
            siftDown(array, longitud, masGrande);
        }
    }

    private void swap(int i, int j, Nodo[] array) {
        int idI = heap[i];
        int idJ = heap[j];

        heap[i] = idJ;
        heap[j] = idI;
    }

    public Transaccion[] toArray() {
        Transaccion[] transacciones = new Transaccion[this.size];
        int i = 0;

        for (Nodo nodo : this.nodos) {
            if (nodo != null) {
                transacciones[i] = new Transaccion(nodo.id(), nodo.id_comprador(), nodo.id_vendedor(), nodo.monto());
                i++;
            }
        }

        return transacciones;
    }

    public Hackeo hackear() {
        if (this.size == 0) {
            return null;
        }

        Nodo root = nodos[heap[0]];
        int lastIndex = this.size - 1;

        heap[0] = heap[lastIndex];
        nodos[root.id()] = null;

        this.size--;

        if (root.id_comprador() > 0) {
            sumaMontos -= root.monto();
            cantidadTr--;
        }

        siftDown(nodos, size, 0);

        return new Hackeo(root.id_comprador(), root.id_vendedor(), root.monto());
    }

    public Transaccion raiz() {
        if (this.size == 0) {
            return null;
        }

        Nodo root = nodos[heap[0]];
        return new Transaccion(root.id(), root.id_comprador(), root.id_vendedor(), root.monto());
    }

    public int montoMedio() {
        if (this.size == 0) {
            return 0;
        }

        int divisor = cantidadTr;

        if (divisor == 0) {
            return 0;
        }

        return sumaMontos / divisor;
    }

    public int size() {
        return this.size;
    }
}
