package aed;

public interface NodoHeap {
    private int id;
    private int valor;

    public int compareTo(NodoHeap otro);
    public boolean equals(Object otro);
    public int id();
    public int valor();
}