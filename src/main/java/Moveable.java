package main.java;

public interface Moveable extends Locateable
{
    public final int DOWN = -1, IDLE = 0, UP = 1;
    public void move();
    public int getDirection();
    public void setDirection(int direction);
}
