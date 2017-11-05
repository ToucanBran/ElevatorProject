package main.java;

public interface Moveable extends Locateable
{
    public void move();
    public int getDirection();
    public void setDirection(int direction);
}
