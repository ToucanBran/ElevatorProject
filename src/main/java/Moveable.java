package main.java;
/**
 * Interface for moving objects.
 *
 * @author Brandon Gomez
 */
public interface Moveable extends Locateable
{
    public void move();
    public int getDirection();
    public void setDirection(int direction);
}
