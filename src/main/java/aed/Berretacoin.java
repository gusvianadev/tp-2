package aed;

import java.util.ArrayList;

public class Berretacoin {
    private Heap<Usuario> usuarios;
    private ArrayList<Bloque> bloques;

    public class Bloque {
        private int id;
        private Heap<Transaccion> transacciones;
        private int montoTransacciones;
        private int cantidadTransacciones;

        Bloque(Transaccion[] transacciones, int montoTransacciones, int cantidadTransacciones) {
            this.id = bloques.size();
            this.transacciones = new Heap<Transaccion>(transacciones);
            this.montoTransacciones = montoTransacciones;

            // Podría poner un if acá para ver si hay una transacción de creación y hacer
            // transacciones.length - 1, pero es mejor que el creador haga lo menos posible
            // por claridad
            this.cantidadTransacciones = cantidadTransacciones;
        }

        public int id() {
            return this.id;
        }

        public int montoTransacciones() {
            return this.montoTransacciones;
        }

        public int cantidadTransacciones() {
            return this.cantidadTransacciones;
        }

        public Transaccion transaccionMasGrande() {
            return this.transacciones.obtenerRaiz();
        }

        public Transaccion[] transacciones() {
            int longitudTransacciones = this.transacciones.longitud();

            return this.transacciones.toArrayList().toArray(new Transaccion[longitudTransacciones]);
        }

        public Transaccion eliminarTransaccionMayor() {
            if (this.transacciones.longitud() == 0)
                return null;

            Transaccion transaccionMayor = this.transacciones.eliminarRaiz();

            if (!transaccionMayor.esCreacion()) {
                this.cantidadTransacciones--;
                this.montoTransacciones -= transaccionMayor.monto();
            }

            return transaccionMayor;
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
        int montoTransacciones = 0;
        int cantidadTransacciones = 0;

        for (int i = 0; i < transacciones.length; i++) {
            Transaccion transaccion = transacciones[i];
            int id_comprador = transaccion.id_comprador();
            int id_vendedor = transaccion.id_vendedor();
            int monto = transaccion.monto();

            Usuario nuevoVendedor = new Usuario(id_vendedor, this.usuarios.obtener(id_vendedor).saldo() + monto);

            if (transaccion.esCreacion()) {
                this.usuarios.modificar(id_vendedor, nuevoVendedor);
            } else {
                Usuario nuevoComprador = new Usuario(id_comprador, this.usuarios.obtener(id_comprador).saldo() - monto);

                this.usuarios.modificar(id_vendedor, nuevoVendedor);
                this.usuarios.modificar(id_comprador, nuevoComprador);

                montoTransacciones += monto;
                cantidadTransacciones++;
            }
        }

        this.bloques.add(new Bloque(transacciones, montoTransacciones, cantidadTransacciones));
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

    public int montoMedioUltimoBloque() {
        if (this.bloques.isEmpty() || ultimoBloque().cantidadTransacciones() == 0) {
            return 0; // Evitar división por cero
        }

        return ultimoBloque().montoTransacciones() / ultimoBloque().cantidadTransacciones();
    }

    public void hackearTx() {
        if (this.bloques.isEmpty()) {
            return;
        }

        Transaccion elementoHackeado = ultimoBloque().eliminarTransaccionMayor();

        if (elementoHackeado == null) {
            return;
        }

        int id_comprador = elementoHackeado.id_comprador();
        int id_vendedor = elementoHackeado.id_vendedor();
        int monto = elementoHackeado.monto();
        Usuario nuevoVendedor = new Usuario(id_vendedor, this.usuarios.obtener(id_vendedor).saldo() - monto);

        if (elementoHackeado.esCreacion()) {
            this.usuarios.modificar(id_vendedor, nuevoVendedor);
        } else {
            Usuario nuevoComprador = new Usuario(id_comprador, this.usuarios.obtener(id_comprador).saldo() + monto);

            this.usuarios.modificar(id_vendedor, nuevoVendedor);
            this.usuarios.modificar(id_comprador, nuevoComprador);
        }
    }
}
