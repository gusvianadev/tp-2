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
        HeapTransacciones heap = new HeapTransacciones(transacciones);

        assertEquals(4, heap.raiz().id);

        // Tambien verifico que los heapIndex esten bien
        int[] heapIndexEsperados = { 1, 4, 2, 3, 0 };

        // Las posiciones heapIndexEsperados tienen el heapIndexEsperado para la id que
        // corresponde a esa posicion
        // Ej: el heapIndex esperado para la id 0 es 1, para la id 1 es 4, etc.
        for (int i = 0; i < transacciones.length; i++) {
            assertEquals(heapIndexEsperados[i], transacciones[i].heapIndex,
                    "El heapIndex de la transacción " + i + " no es correcto");
        }

    }

    @Test
    public void hackearUnaVez() {
        Transaccion[] transacciones = crearTransacciones();
        HeapTransacciones heap = new HeapTransacciones(transacciones);

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
        HeapTransacciones heap = new HeapTransacciones(transacciones);

        heap.hackear(); // elimina id 4
        heap.hackear(); // elimina id 2

        Transaccion[] actual = heap.toArray();

        for (Transaccion t : actual) {
            assertNotEquals(4, t.id());
            assertNotEquals(2, t.id());
        }

        assertEquals(3, actual.length);
        assertEquals(3, heap.size());

        // Verifico que el heapIndex de las transacciones restantes sea correcto
        for (Transaccion t : actual) {
            if (t.id() == 0) {
                assertEquals(0, t.heapIndex, "El heapIndex de la transacción con id 0 no es correcto");
            } else if (t.id() == 1) {
                assertEquals(2, t.heapIndex, "El heapIndex de la transacción con id 1 no es correcto");
            } else if (t.id() == 3) {
                assertEquals(1, t.heapIndex, "El heapIndex de la transacción con id 3 no es correcto");
            }
        }
    }
}
