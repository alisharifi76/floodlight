package net.floodlightcontroller.mactracker;

public class Triple<T, U, V> {

    private T first;
    private U second;
    private V third;

    public Triple(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() { return first; }
    public U getSecond() { return second; }
    public V getThird() { return third; }

    public void setFirst(T n) { this.first = n; }
    public void setSecond(U n) { this.second = n; }
    public void setThird(V n) { this.third = n; }

}