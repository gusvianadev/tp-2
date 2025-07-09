package aed;

import java.util.ArrayList;

public class Berretacoin {
	Heap<Usuario> heapUsuarios;
	ArrayList<Bloque> bloques;

	public class Bloque {
		int id;
		Heap<Transaccion> transacciones;

		Bloque(Transaccion[] transacciones) {
			this.id = bloques.size();

			// HAY QUE ARREGLAR ESTO
			for (int i; i < transacciones.length; i++)
				transaccionesAHeap[i] = TransaccionHeap(transacciones[i]);

			this.transacciones = new Heap<Transaccion>(transacciones);
		}
	}

	public Berretacoin(int n_usuarios) {
		this.heapUsuarios = new HeapUsuarios(n_usuarios);
		this.bloques = new ArrayList<>();
	}

	public void agregarBloque(Transaccion[] transacciones) {
		this.bloques.add(new Bloque(transacciones));
	}

	public Transaccion txMayorValorUltimoBloque() {
		return this.bloques.get(bloques.size() - 1).transacciones.raiz();
	}

	public Transaccion[] txUltimoBloque() {
		return this.bloques.get(bloques.size() - 1).transacciones.toArray();
	}

	public int maximoTenedor() {
		return this.heapUsuarios.maximoTenedor();
	}

	public int montoMedioUltimoBloque() {
		return this.bloques.get(bloques.size() - 1).transacciones.montoMedio();
	}

	public void hackearTx() {
		Hackeo hackeo = this.bloques.get(bloques.size() - 1).transacciones.hackear();

		if (hackeo == null) {
			return;
		}

		if (hackeo.id_comprador > 0)
			this.heapUsuarios.incrementarSaldo(hackeo.id_comprador, hackeo.monto);

		this.heapUsuarios.decrementarSaldo(hackeo.id_vendedor, hackeo.monto);
	}
}
