package aed;

public class Usuario implements Comparable<Usuario> {
	private int id;
	private int saldo;

	public Usuario(int id, int saldo) {
		this.id = id;
		this.saldo = saldo;
	}

	public int saldo() {
		return this.saldo;
	}

	public int id() {
		return this.id;
	}

	public int compareTo(Usuario otro) {
		if (this.saldo != otro.saldo)
			return Integer.compare(this.saldo, otro.saldo);
		return Integer.compare(otro.id, this.id);
	}
}