package main.java;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ElevatorChoiceProperties hold a list of candidate elevators as well as properties that
 * factor into make a final choice of which elevator to pick.
 *
 * @author Brandon Gomez
 */
public class ElevatorChoiceProperties
{
    private int currentFloor, requestedDirection, maxFloorTime, maxWaitTime;
    Collection<Elevator> elevators;

    public ElevatorChoiceProperties(int currentFloor, int requestedDirection, int maxFloorTime, int maxWaitTime, Collection<Elevator> elevators)
    {
        this.currentFloor = currentFloor;
        this.requestedDirection = requestedDirection;
        this.maxFloorTime = maxFloorTime;
        this.elevators = elevators;
        this.maxWaitTime = maxWaitTime;
    }

    public int getCurrentFloor()
    {
        return currentFloor;
    }

    public int getRequestedDirection()
    {
        return requestedDirection;
    }

    public int getMaxFloorTime()
    {
        return maxFloorTime;
    }

    public int getMaxWaitTime()
    {
        return maxWaitTime;
    }

    public Collection<Elevator> getElevators()
    {
        return elevators;
    }
}
