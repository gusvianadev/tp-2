package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HeapUsuariosTests {

    @Test
    void crear() {
        HeapUsuarios heap = new HeapUsuarios(5);

        for (int i = 1; i <= 5; i++) {
            heap.incrementarSaldo(i, 0);
        }

        assertEquals(1, heap.maximoTenedor());
    }

    @Test
    void maximoTenedor() {
        HeapUsuarios heap = new HeapUsuarios(5);

        assertEquals(1, heap.maximoTenedor());

        heap.incrementarSaldo(2, 50);
        assertEquals(2, heap.maximoTenedor());

        heap.incrementarSaldo(1, 80);
        assertEquals(1, heap.maximoTenedor());

        heap.incrementarSaldo(3, 30);
        assertEquals(1, heap.maximoTenedor());

        heap.decrementarSaldo(1, 60);
        assertEquals(2, heap.maximoTenedor());

        heap.incrementarSaldo(4, 100);
        assertEquals(4, heap.maximoTenedor());

        heap.incrementarSaldo(1, 100);
        assertEquals(1, heap.maximoTenedor());
    }

    @Test
    void actualizarSaldo() {
        HeapUsuarios heap = new HeapUsuarios(3);

        heap.incrementarSaldo(1, 10);
        heap.incrementarSaldo(2, 20);
        heap.incrementarSaldo(3, 30);
        assertEquals(3, heap.maximoTenedor());

        heap.decrementarSaldo(3, 25);
        assertEquals(2, heap.maximoTenedor());

        heap.incrementarSaldo(1, 15);
        assertEquals(1, heap.maximoTenedor());
    }
}
