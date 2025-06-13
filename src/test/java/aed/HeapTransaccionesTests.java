package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HeapTransaccionesTests {
    private Transaccion[] crearTransacciones() {
        return new Transaccion[] {
                new Transaccion(0, 10, 20, 50),
                new Transaccion(1, 11, 21, 30),
                new Transaccion(2, 12, 22, 70),
                new Transaccion(3, 13, 23, 40),
                new Transaccion(4, 14, 24, 90),
        };
    }

    private Transaccion[] crearTransaccionesConCreacion() {
        return new Transaccion[] {
                new Transaccion(0, 0, 20, 1),
                new Transaccion(1, 11, 21, 30),
                new Transaccion(2, 12, 22, 70),
                new Transaccion(3, 13, 23, 40),
                new Transaccion(4, 14, 24, 90),
        };
    }

    @Test
    public void crearHeap() {
        Transaccion[] transacciones = crearTransacciones();
        HeapUsuarios heapUsuarios = new HeapUsuarios(25);
        HeapTransacciones heap = new HeapTransacciones(transacciones, heapUsuarios);

        assertEquals(4, heap.raiz().id());
    }

    @Test
    public void hackearUnaVez() {
        Transaccion[] transacciones = crearTransacciones();
        HeapUsuarios heapUsuarios = new HeapUsuarios(25);
        HeapTransacciones heap = new HeapTransacciones(transacciones, heapUsuarios);

        assertEquals(5, heap.size());
        assertEquals(56, heap.montoMedio());

        heap.hackear();
        
        assertEquals(4, heap.size());
        assertEquals(47, heap.montoMedio());

        Transaccion[] actual = heap.toArray();

        boolean contieneId4 = false;
        for (Transaccion t : actual) {
            if (t.id() == 4) {
                contieneId4 = true;
                break;
            }
        }

        assertFalse(contieneId4, "La transacción con id 4 debería haber sido eliminada");
    }

    @Test
    public void hackearUnaVezConCreacion() {
        Transaccion[] transacciones = crearTransaccionesConCreacion();
        HeapUsuarios heapUsuarios = new HeapUsuarios(25);
        HeapTransacciones heap = new HeapTransacciones(transacciones, heapUsuarios);

        assertEquals(5, heap.size());
        assertEquals(57, heap.montoMedio());

        heap.hackear();

        assertEquals(4, heap.size());
        assertEquals(46, heap.montoMedio());

        Transaccion[] actual = heap.toArray();

        boolean contieneId4 = false;
        for (Transaccion t : actual) {
            if (t.id() == 4) {
                contieneId4 = true;
                break;
            }
        }

        assertFalse(contieneId4, "La transacción con id 4 debería haber sido eliminada");
    }

    @Test
    public void hackearVariasVeces() {
        Transaccion[] transacciones = crearTransacciones();
        HeapUsuarios heapUsuarios = new HeapUsuarios(25);
        HeapTransacciones heap = new HeapTransacciones(transacciones, heapUsuarios);

        assertEquals(5, heap.size());

        heap.hackear(); // elimina id 4

        assertEquals(4, heap.size());

        heap.hackear(); // elimina id 2

        Transaccion[] actual = heap.toArray();

        for (Transaccion t : actual) {
            assertNotEquals(4, t.id());
            assertNotEquals(2, t.id());
        }

        assertEquals(3, actual.length);
        assertEquals(3, heap.size());
    }

    @Test
    public void hackearVariasVecesConCreacion() {
        Transaccion[] transacciones = crearTransaccionesConCreacion();
        HeapUsuarios heapUsuarios = new HeapUsuarios(25);
        HeapTransacciones heap = new HeapTransacciones(transacciones, heapUsuarios);

        assertEquals(5, heap.size());
        assertEquals(57, heap.montoMedio());

        heap.hackear(); // elimina id 4

        assertEquals(4, heap.size());
        assertEquals(46, heap.montoMedio());

        heap.hackear(); // elimina id 2

        assertEquals(3, heap.size());
        assertEquals(35, heap.montoMedio());
    }
}
