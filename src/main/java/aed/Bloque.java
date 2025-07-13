package aed;

public class Bloque {
	private int id;
	private Heap<Transaccion> transacciones;

	// O(nb)
	Bloque(Transaccion[] transacciones, int id) {
		this.id = id;
		// O(nb)
		this.transacciones = new Heap<Transaccion>(transacciones);
	}

	public int id() {
		return this.id;
	}

	// O(1)
	public Transaccion transaccionMasGrande() {
		return this.transacciones.obtenerRaiz();
	}

	// O(nb)
	public Transaccion[] transacciones() {
		// O(nb) + O(nb) = O(nb)
		return this.transacciones.toArrayList().toArray(new Transaccion[0]);
	}

	// O(log nb)
	public void eliminarTransaccionMayor() {
		this.transacciones.eliminarRaiz();
	}
}
