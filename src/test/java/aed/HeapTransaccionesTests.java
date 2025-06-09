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

        heap.hackear();
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

        heap.hackear(); // elimina id 4
        heap.hackear(); // elimina id 2

        Transaccion[] actual = heap.toArray();

        for (Transaccion t : actual) {
            assertNotEquals(4, t.id());
            assertNotEquals(2, t.id());
        }

        assertEquals(3, actual.length);
        assertEquals(3, heap.size());
    }
}
