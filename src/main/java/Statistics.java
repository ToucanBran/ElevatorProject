package main.java;

public interface Statistics<T, C>
{
    public void addEntry(T[] entry);
    public C getStatistics();
}
