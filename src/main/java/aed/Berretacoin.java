package aed;

import java.util.ArrayList;

public class Berretacoin {
	private Heap<Usuario> usuarios;
	private ArrayList<Bloque> bloques;
	private int cantidadTransacciones;
	private int sumaMontos;

	// O(P) + O(P) + O(log P) = O(P)
	public Berretacoin(int n_usuarios) {
		Usuario[] arrUsuarios = new Usuario[n_usuarios + 1];

		// O(P)
		for (int i = 0; i <= n_usuarios; i++)
			arrUsuarios[i] = new Usuario(i, 0);

		// O(P)
		this.usuarios = new Heap<Usuario>(arrUsuarios);

		// Eliminamos el usuario 0, que es log(P)
		this.usuarios.eliminarRaiz();
		this.bloques = new ArrayList<Bloque>();
	}

	// O(nb) + O(nb log P) = O(nb log P)
	public void agregarBloque(Transaccion[] transacciones) {
		int montos = 0;
		int cantidad = 0;
		this.sumaMontos = 0;
		this.cantidadTransacciones = 0;

		// O(nb)
		for (int i = 0; i < transacciones.length; i++) {
			Transaccion transaccion = transacciones[i];
			int id_comprador = transaccion.id_comprador();
			int id_vendedor = transaccion.id_vendedor();
			montos += transaccion.monto();

			Usuario nuevoVendedor = new Usuario(transaccion.id_vendedor(),
					this.usuarios.obtener(id_vendedor).saldo() + transaccion.monto());

			if (transaccion.esCreacion()) {
				// O(log P)
				this.usuarios.modificar(id_vendedor, nuevoVendedor);
				montos -= transaccion.monto();
			} else {
				cantidad++;
				Usuario nuevoComprador = new Usuario(transaccion.id_comprador(),
						this.usuarios.obtener(id_comprador).saldo() - transaccion.monto());

				// O(log P)
				this.usuarios.modificar(id_vendedor, nuevoVendedor);
				// O(log P)
				this.usuarios.modificar(id_comprador, nuevoComprador);
			}
		}

		// O(nb)
		Bloque bloque = new Bloque(transacciones, this.bloques.size());
		this.bloques.add(bloque);
		this.cantidadTransacciones = cantidad;
		this.sumaMontos = montos;
	}

	// O(1)
	private Bloque ultimoBloque() {
		if (this.bloques.isEmpty()) {
			return null;
		}

		return this.bloques.get(bloques.size() - 1);
	}

	// O(1)
	public Transaccion txMayorValorUltimoBloque() {
		return ultimoBloque().transaccionMasGrande();
	}

	// O(nb)
	public Transaccion[] txUltimoBloque() {
		return ultimoBloque().transacciones();
	}

	// O(1)
	public int maximoTenedor() {
		return this.usuarios.obtenerRaiz().id();
	}

	// O(1)
	public int montoMedioUltimoBloque() {
		if (cantidadTransacciones > 0) {
			return sumaMontos / cantidadTransacciones;
		}
		return 0;
	}

	public void hackearTx() {
		Bloque ultimoBloque = this.bloques.get(bloques.size() - 1);
		Transaccion txMax = this.txMayorValorUltimoBloque();
		int monto = txMax.monto();

		if (!txMax.esCreacion()) {
			this.sumaMontos -= txMax.monto();
			int idComprador = txMax.id_comprador();
			int saldoComprador = usuarios.obtener(idComprador).saldo();

			// O(log P)
			this.usuarios.modificar(idComprador, new Usuario(idComprador, saldoComprador + monto));
			cantidadTransacciones--;
		}

		int idVendedor = txMax.id_vendedor();
		int saldoVendedor = usuarios.obtener(idVendedor).saldo();

		// O(log P)
		this.usuarios.modificar(idVendedor, new Usuario(idVendedor, saldoVendedor - monto));

		// O(log nb)
		ultimoBloque.eliminarTransaccionMayor();
	}

	// Este es para los tests. No tiene en cuenta las transacciones de creacion
	public int sumaMontos() {
		return sumaMontos;
	}

	// Este es para los tests. No tiene en cuenta las transacciones de creacion
	public int cantidadTransacciones() {
		return cantidadTransacciones;
	}
}
