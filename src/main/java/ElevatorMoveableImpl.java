package main.java;

import org.apache.log4j.Logger;

/**
 * ElevatorMoveableImpl is an algorithm for moving an elevator from one floor to the next.
 * It's also responsible for keeping track of the elevator's current direction and location.
 *
 * @author Brandon Gomez
 */
public class ElevatorMoveableImpl implements Moveable
{
    public final Logger log = Logger.getRootLogger();
    private int currentFloor, currentDirection;

    // Two constructors here just in case there's a point where we have
    // a factory change Impls when the elevator is on a floor other than
    // the first.
    public ElevatorMoveableImpl()
    {
        this(1);
    }

    public ElevatorMoveableImpl(int currentFloor)
    {
        this.currentFloor = currentFloor;
        currentDirection = Directions.IDLE;
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
