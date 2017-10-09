package main.java;

public class MoveableImpl implements Moveable
{
    private int currentFloor, currentDirection;

    // Two constructors here just in case there's a point where we have
    // a factory change Impls when the elevator is on a floor other than
    // the first.
    public MoveableImpl()
    {
        // Not zero-basing floors
        this.currentFloor = 1;
        currentDirection = IDLE;
    }
    public MoveableImpl(int currentFloor)
    {
        this.currentFloor = currentFloor;
    }
    @Override
    public void move()
    {
        currentFloor += currentDirection;
    }
    @Override
    public int getLocation()
    {
        return currentFloor;
    }

    @Override
    public int getDirection()
    {
        return currentDirection;
    }

    @Override
    public void setDirection(int direction)
    {
        currentDirection = direction;
    }
}