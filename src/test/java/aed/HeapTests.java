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

    private Transaccion[] crearTransaccionesConCreacion() {
        return new Transaccion[] {
                new Transaccion(0, 0, 1, 1),
                new Transaccion(1, 1, 2, 4),
                new Transaccion(2, 2, 1, 2)
        };
        // saldos
        // 1 = $4 -> $5 -> $1 -> $3
        // 2 =       $0 -> $4 -> $2
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

    @Test
    public void montoMedio(){
        Berretacoin berretacoin = new Berretacoin(5);
        Transaccion[] transacciones = crearTransaccionesConCreacion();

        berretacoin.agregarBloque(transacciones);
        // cantidad total de transacciones = 2 (sin la creacion)
        assertEquals(2, berretacoin.cantidadTransacciones());
        assertEquals(6, berretacoin.sumaMontos());
        // monto medio
        assertEquals(3, berretacoin.montoMedioUltimoBloque());

        // hackear
        berretacoin.hackearTx();
        // cantidad total de transacciones = 1 (sin la creacion)
        assertEquals(1, berretacoin.cantidadTransacciones());
        assertEquals(2, berretacoin.sumaMontos());
        // monto medio
        assertEquals(2, berretacoin.montoMedioUltimoBloque());
    }
}
