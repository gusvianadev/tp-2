package aed;

import java.util.ArrayList;

public class Berretacoin {
	private Heap<Usuario> usuarios;
	private ArrayList<Bloque> bloques;

	public class Bloque {
		private int id;
		private Heap<Transaccion> transacciones;

		Bloque(Transaccion[] transacciones) {
			this.id = bloques.size();
			this.transacciones = new Heap<Transaccion>(transacciones);
		}

		public int id() {
			return this.id;
		}

		public Transaccion transaccionMasGrande() {
			return this.transacciones.obtenerRaiz();
		}

		public Transaccion[] transacciones() {
			return this.transacciones.toArrayList().toArray(new Transaccion[this.transacciones.longitud()]);
		}
	}

	public Berretacoin(int n_usuarios) {
		Usuario[] arrUsuarios = new Usuario[n_usuarios];

		for (int i = 0; i < n_usuarios; i++)
			arrUsuarios[i] = new Usuario(i + 1, 0);

		this.usuarios = new Heap<Usuario>(arrUsuarios);
		this.bloques = new ArrayList<Bloque>();
	}

	public void agregarBloque(Transaccion[] transacciones) {
		for (int i = 0; i < transacciones.length; i++) {
			Transaccion transaccion = transacciones[i];
			int id_comprador = transaccion.id_comprador() - 1;
			int id_vendedor = transaccion.id_vendedor() - 1;

			Usuario nuevoVendedor = new Usuario(transaccion.id_vendedor(),
					this.usuarios.obtener(transaccion.id_vendedor()).saldo() + transaccion.monto());

			if (transaccion.esCreacion()) {
				this.usuarios.modificar(id_vendedor, nuevoVendedor);
			} else {
				Usuario nuevoComprador = new Usuario(id_comprador,
						this.usuarios.obtener(id_comprador).saldo() - transaccion.monto());

				this.usuarios.modificar(id_vendedor, nuevoVendedor);
				this.usuarios.modificar(id_comprador, nuevoComprador);
			}
		}
		this.bloques.add(new Bloque(transacciones));
	}

	private Bloque ultimoBloque() {
		if (this.bloques.isEmpty()) {
			return null;
		}
		return this.bloques.get(bloques.size() - 1);
	}

	public Transaccion txMayorValorUltimoBloque() {
		return ultimoBloque().transaccionMasGrande();
	}

	public Transaccion[] txUltimoBloque() {
		return ultimoBloque().transacciones();
	}

	public int maximoTenedor() {
		return this.usuarios.obtenerRaiz().id();
	}

	// public int montoMedioUltimoBloque() {
	// return this.bloques.get(bloques.size() - 1).transacciones.montoMedio();
	// }

	// public void hackearTx() {
	// Hackeo hackeo = this.bloques.get(bloques.size() - 1).transacciones.hackear();

	// if (hackeo == null) {
	// return;
	// }

	// if (hackeo.id_comprador > 0)
	// this.usuarios.incrementarSaldo(hackeo.id_comprador, hackeo.monto);

	// this.usuarios.decrementarSaldo(hackeo.id_vendedor, hackeo.monto);
	// }
}
