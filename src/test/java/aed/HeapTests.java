package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

// Realmente podriamos testear con cualquier tipo de dato pero testeamos con Transacciones porque viene al caso
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

	// Test para modificar un nodo del heap
	@Test
	public void modificarNodo() {
		Transaccion[] transacciones = crearTransacciones();
		Heap<Transaccion> heap = new Heap<Transaccion>(transacciones);

		assertEquals(5, heap.longitud());
		assertEquals(4, heap.obtenerRaiz().id());

		// Este es para ver si la raiz se mantiene
		Transaccion transaccionModificada = new Transaccion(4, 14, 24, 100);
		heap.modificar(4, transaccionModificada);

		assertEquals(100, heap.obtenerRaiz().monto());
		assertEquals(4, heap.obtenerRaiz().id());
		assertEquals(5, heap.longitud());

		// Y este para ver se la raiz cambia
		Transaccion transaccionModificada2 = new Transaccion(2, 12, 22, 200);
		heap.modificar(2, transaccionModificada2);
		assertEquals(200, heap.obtenerRaiz().monto());
		assertEquals(2, heap.obtenerRaiz().id());
	}

	@Test
	public void toArrayList() {
		Transaccion[] transacciones = crearTransacciones();
		Transaccion[] transaccionesHackeadas = new Transaccion[] {
				new Transaccion(0, 10, 20, 50),
				new Transaccion(1, 11, 21, 30),
				new Transaccion(2, 12, 22, 70),
				new Transaccion(3, 13, 23, 40),
		};

		Heap<Transaccion> heap = new Heap<Transaccion>(transacciones);

		assertEquals(5, heap.longitud());
		assertEquals(4, heap.obtenerRaiz().id());
		assertArrayEquals(transacciones, heap.toArrayList().toArray(new Transaccion[0]));
		heap.eliminarRaiz();
		assertArrayEquals(transaccionesHackeadas, heap.toArrayList().toArray(new Transaccion[0]));
	}
}
