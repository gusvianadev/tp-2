package aed;

public class Berretacoin {
    HeapUsuarios heapUsuarios;

    public Berretacoin(int n_usuarios) {
        this.heapUsuarios = new HeapUsuarios(n_usuarios);
    }

    public void agregarBloque(Transaccion[] transacciones) {
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion txMayorValorUltimoBloque() {
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion[] txUltimoBloque() {
        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor() {
        throw new UnsupportedOperationException("Implementar!");
    }

    public int montoMedioUltimoBloque() {
        throw new UnsupportedOperationException("Implementar!");
    }

    public void hackearTx() {
        throw new UnsupportedOperationException("Implementar!");
    }
}
