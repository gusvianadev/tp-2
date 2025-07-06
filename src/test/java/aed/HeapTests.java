package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HeapTests {
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
        Heap<Transaccion> heap = new Heap<Transaccion>(transacciones);
        assertEquals(5, heap.longitud());
        assertEquals(4, heap.obtenerRaiz().id());
    }

    @Test
    public void eliminarRaiz() {
        Transaccion[] transacciones = crearTransacciones();
        Heap<Transaccion> heap = new Heap<Transaccion>(transacciones);

        assertEquals(5, heap.longitud());
        assertEquals(4, heap.obtenerRaiz().id());
        heap.eliminarRaiz();
        assertEquals(4, heap.longitud());
        assertEquals(2, heap.obtenerRaiz().id());
    }
}
