package aed;

public class HeapTransacciones {
    private Nodo[] nodos;
    private int[] heap;
    private int size = 0;
    private int sumaMontos = 0;
    private int cantidadTr = 0;

    class Nodo {
        int id;
        int id_comprador;
        int id_vendedor;
        int monto;
        int heapIndex;

        Nodo(int id, int id_comprador, int id_vendedor, int monto, int heapIndex) {
            this.id = id;
            this.id_comprador = id_comprador;
            this.id_vendedor = id_vendedor;
            this.monto = monto;
            this.heapIndex = heapIndex;
        }

        public int compareTo(Nodo otro) {
            if (this.monto != otro.monto)
                return Integer.compare(this.monto, otro.monto);
            return Integer.compare(this.id, otro.id);
        }

        public boolean equals(Object otro) {
            if (this == otro)
                return true;
            if (otro == null || getClass() != otro.getClass())
                return false;

            Nodo t = (Nodo) otro;
            return this.id == t.id &&
                    this.id_comprador == t.id_comprador &&
                    this.id_vendedor == t.id_vendedor &&
                    this.monto == t.monto;
        }
    }

    public HeapTransacciones(Transaccion[] arr, HeapUsuarios heapUsuarios) {
        int longitud = arr.length;
        this.cantidadTr = 0;
        this.sumaMontos = 0;
        this.nodos = new Nodo[longitud];
        this.heap = new int[longitud];
        this.size = longitud;

        for (int i = 0; i < longitud; i++) {
            Transaccion tr = arr[i];

            nodos[i] = new Nodo(tr.id(), tr.id_comprador(), tr.id_vendedor(), tr.monto(), i);
            heap[i] = i;

            if (tr.esCreacion()) {
                heapUsuarios.incrementarSaldo(tr.id_vendedor(), tr.monto());
            }else{
                heapUsuarios.decrementarSaldo(tr.id_comprador(), tr.monto());
                heapUsuarios.incrementarSaldo(tr.id_vendedor(), tr.monto());
            }
            
            if (!tr.esCreacion()){
                sumaMontos += tr.monto();
                cantidadTr++;
            }
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

    private void swap(int i, int j, Nodo[] array) {
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

        for (Nodo nodo : this.nodos) {
            if (nodo == null) {
                continue;
            }

            transacciones[i] = new Transaccion(nodo.id, nodo.id_comprador, nodo.id_vendedor, nodo.monto);
            i++;
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
        nodos[heap[0]].heapIndex = 0;
        nodos[root.id] = null;

        this.size--;

        if (root.id_comprador > 0){
            sumaMontos -= root.monto;
            cantidadTr--;
        }

        siftDown(nodos, size, 0);

        return new Hackeo(root.id_comprador, root.id_vendedor, root.monto);
    }

    public Transaccion raiz() {
        if (this.size == 0) {
            return null;
        }

        Nodo root = nodos[heap[0]];
        return new Transaccion(root.id, root.id_comprador, root.id_vendedor, root.monto);
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
