package aed;

import java.util.ArrayList;

public class Berretacoin {
	private Heap<Usuario> usuarios;
	private ArrayList<Bloque> bloques;
	private int cantidadTransacciones;
	private int sumaMontos;

	public class Bloque{
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
		Usuario[] arrUsuarios = new Usuario[n_usuarios+1];
		
		for (int i = 0; i <= n_usuarios; i++)
			arrUsuarios[i] = new Usuario(i, 0);

		this.usuarios = new Heap<Usuario>(arrUsuarios);
		//eliminamos el usuario 0, que es log(P)
		this.usuarios.eliminarRaiz();
		this.bloques = new ArrayList<Bloque>();
	}

	public void agregarBloque(Transaccion[] transacciones) {
		int montos = 0;
		int cantidad = 0;

		for (int i = 0; i < transacciones.length; i++) {
			Transaccion transaccion = transacciones[i];
			int id_comprador = transaccion.id_comprador();
			int id_vendedor = transaccion.id_vendedor();
			montos += transaccion.monto();

			Usuario nuevoVendedor = new Usuario(transaccion.id_vendedor(),
					this.usuarios.obtener(id_vendedor).saldo() + transaccion.monto());

			if (transaccion.esCreacion()) {
				this.usuarios.modificar(id_vendedor, nuevoVendedor);
				montos -= transaccion.monto();
			} else {
				cantidad++;
				Usuario nuevoComprador = new Usuario(transaccion.id_comprador(),
						this.usuarios.obtener(id_comprador).saldo() - transaccion.monto());

				this.usuarios.modificar(id_vendedor, nuevoVendedor);
				this.usuarios.modificar(id_comprador, nuevoComprador);
			}
		}

		Bloque bloque = new Bloque(transacciones);
		this.bloques.add(bloque);
		this.cantidadTransacciones = cantidad;
		this.sumaMontos = montos;
	}

	public Bloque ultimoBloque() {
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

	public int montoMedioUltimoBloque() {
		if(cantidadTransacciones > 0){
			return sumaMontos / cantidadTransacciones;
		}
		return 0;
	}

	public void hackearTx() {
		Bloque ultimoBloque = this.bloques.get(bloques.size()-1);
		Transaccion txMax = this.txMayorValorUltimoBloque();

		if(!txMax.esCreacion()){
			cantidadTransacciones--;
			this.sumaMontos -= txMax.monto();
		}

		if(txMax.id_comprador() > 0){
			this.usuarios.modificar(txMax.id_comprador(), new Usuario(txMax.id_comprador(), usuarios.obtener(txMax.id_comprador()).saldo() + txMax.monto()));
			
		}
		this.usuarios.modificar(txMax.id_vendedor(), new Usuario(txMax.id_vendedor(), usuarios.obtener(txMax.id_vendedor()).saldo() - txMax.monto()));
		ultimoBloque.transacciones.eliminarRaiz();
		
	}

	public int sumaMontos(){
		return sumaMontos;
	}

	public int cantidadTransacciones(){
		return cantidadTransacciones;
	}
}
