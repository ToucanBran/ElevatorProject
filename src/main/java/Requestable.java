package main.java;

/**
 * Requestable interface to request something. Typically used for elevators
 *
 * @author Brandon Gomez
 */
public interface Requestable
{
    public void request(int destination, int direction);
}
