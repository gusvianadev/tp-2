package aed;

import java.util.ArrayList;

public class Berretacoin {
	private Heap<Usuario> usuarios;
	private ArrayList<Bloque> bloques;

	public class Bloque{
		private int id;
		private Heap<Transaccion> transacciones;
		private int sumaMontos;
		private Boolean creacionDeleted;

		Bloque(Transaccion[] transacciones, int sumaMontos) {
			this.id = bloques.size();
			this.transacciones = new Heap<Transaccion>(transacciones);
			this.sumaMontos = sumaMontos;
			this.creacionDeleted = false;
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

		public void setSumaMontos(int montos){
			this.sumaMontos = montos;
		}

		public int getSumaMontos(){
			return this.sumaMontos;
		}

		public void creacionDeleted(Boolean bool){
			this.creacionDeleted = bool;
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
				Usuario nuevoComprador = new Usuario(transaccion.id_comprador(),
						this.usuarios.obtener(id_comprador).saldo() - transaccion.monto());

				this.usuarios.modificar(id_vendedor, nuevoVendedor);
				this.usuarios.modificar(id_comprador, nuevoComprador);
			}
		}

		Bloque bloque = new Bloque(transacciones, montos);
		this.bloques.add(bloque);
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
		// El .size de un arrayList es O(1), asi que todo se devuelve en O(1)
		int montos = this.bloques.get(bloques.size()-1).sumaMontos;
		int longitud = this.bloques.get(bloques.size()-1).transacciones.longitud();
		// Chequeamos si existe la transaccion de creacion para modificar la longitud
		if(!this.ultimoBloque().creacionDeleted){
			longitud--;
		}
		if(longitud == 0){
			return 0;
		}

		return montos / longitud;
	}

	public void hackearTx() {
		Bloque ultimoBloque = this.bloques.get(bloques.size()-1);
		Transaccion txMax = this.txMayorValorUltimoBloque();

		if(txMax.id() == 0){
			ultimoBloque.creacionDeleted = true;
		}

		if(txMax.id_comprador() > 0){
			this.usuarios.modificar(txMax.id_comprador(), new Usuario(txMax.id_comprador(), usuarios.obtener(txMax.id_comprador()).saldo() + txMax.monto()));
			ultimoBloque.sumaMontos = ultimoBloque.sumaMontos - txMax.monto();
		}
		this.usuarios.modificar(txMax.id_vendedor(), new Usuario(txMax.id_vendedor(), usuarios.obtener(txMax.id_vendedor()).saldo() - txMax.monto()));
		ultimoBloque.transacciones.eliminarRaiz();
		
	}
}
