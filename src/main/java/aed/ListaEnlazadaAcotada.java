package aed;

import java.util.*;

public class ListaEnlazadaAcotada<T> {
    private Nodo[] elems;
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;
    private int cota;

    private class Nodo {
        T dato;
        Nodo siguiente;
        Nodo anterior;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    public ListaEnlazadaAcotada(int cota) {
        this.elems = new Nodo[cota];
        this.primero = null;
        this.ultimo = null;
        this.longitud = 0;
        this.cota = cota;
    }

    public ListaEnlazadaAcotada(ListaEnlazada<T> lista) {
        this.primero = null;
        this.ultimo = null;
        this.longitud = 0;
        this.cota = lista.cota;

        for (Nodo n = lista.primero; n != null; n = n.siguiente) {
            agregarAtras(n.dato);
        }
    }

    public int longitud() {
        return longitud;
    }

    public void agregarAdelante(T elem) {
        if (this.longitud == this.cota)
            throw new IndexOutOfBoundsException();

        Nodo n = new Nodo(elem);
        n.siguiente = primero;

        if (primero != null)
            primero.anterior = n;
        else
            ultimo = n;

        primero = n;
        this.elems[longitud] = n;
        longitud++;
    }

    public void agregarAtras(T elem) {
        if (this.longitud == this.cota)
            throw new IndexOutOfBoundsException();

        Nodo n = new Nodo(elem);
        n.anterior = ultimo;

        if (ultimo != null)
            ultimo.siguiente = n;
        else
            primero = n;

        ultimo = n;
        this.elems[longitud] = n;
        longitud++;
    }

    private Nodo obtenerNodo(int i) {
        if (i < 0 || i >= longitud)
            throw new IndexOutOfBoundsException();

        if (elems[i] == null)
            throw new NoSuchElementException();
        
        return elems[i];
    }

    public T obtener(int i) {
        return obtenerNodo(i).dato;
    }

    public void eliminar(int i) {
        Nodo nodo = obtenerNodo(i);

        if (nodo.anterior != null)
            nodo.anterior.siguiente = nodo.siguiente;
        else
            primero = nodo.siguiente;

        if (nodo.siguiente != null)
            nodo.siguiente.anterior = nodo.anterior;
        else
            ultimo = nodo.anterior;

        this.elems[i] = null;

        longitud--;
    }

    public void modificarPosicion(int i, T elem) {
        obtenerNodo(i).dato = elem;
    }

    public int cota() {
        return this.cota;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (Nodo n = primero; n != null; n = n.siguiente) {
            sb.append(n.dato);

            if (n.siguiente != null)
                sb.append(", ");
        }
        sb.append("]");

        return sb.toString();
    }

    private class ListaIterador implements Iterador<T> {
        private Nodo siguienteNodo;

        public ListaIterador() {
            siguienteNodo = primero;
        }

        @Override
        public boolean haySiguiente() {
            return siguienteNodo != null;
        }

        @Override
        public boolean hayAnterior() {
            return haySiguiente()
                    ? siguienteNodo.anterior != null
                    : ultimo != null;
        }

        @Override
        public T siguiente() {
            if (!haySiguiente())
                throw new NoSuchElementException();

            T val = siguienteNodo.dato;
            siguienteNodo = siguienteNodo.siguiente;

            return val;
        }

        @Override
        public T anterior() {
            if (!hayAnterior())
                throw new NoSuchElementException();

            if (siguienteNodo != null) {
                siguienteNodo = siguienteNodo.anterior;
            } else {
                siguienteNodo = ultimo;
            }

            return siguienteNodo.dato;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }
}
