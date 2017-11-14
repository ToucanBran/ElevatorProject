package main.java;

/**
 * Interface class for different elevator choosing algorithms
 *
 * @author Brandon Gomez
 */
public interface Chooseable<T, C>
{
    public T choose(C choices);
}
