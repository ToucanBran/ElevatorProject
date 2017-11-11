package main.java;

import java.util.Set;

public class Helpers
{
    public static boolean onWay(int currentLocation, int direction, int destination)
    {
        return Math.abs(currentLocation - destination) >= Math.abs((currentLocation + direction) - destination);
    }

    // Overloaded method used to take into account direction set by previous requests. This avoids situations where an
    // elevator will pick up a person and end up going the wrong direction than their initial request.
    public static boolean onWay(int currentLocation, int direction, int destination, int requestedDirection, int floorRequestedDirection,
                                Set<Integer> stops, int topFloor)
    {
        // checks if something is going in the same direction toward a destination
        if (onWay(currentLocation, direction, destination))
        {
            if (stops.contains(destination) && requestedDirection == floorRequestedDirection)
                return true;
            else if (requestedDirection == Directions.IDLE)
                return true;
            if (stops.size() > 0)
            {
                if (direction == Directions.DOWN && floorRequestedDirection == Directions.DOWN)
                    return stops.stream().min(Integer::compare).get() <= destination;
                else if (direction == Directions.UP && floorRequestedDirection == Directions.UP)
                    return stops.stream().min(Integer::compare).get() >= destination;
            }
        }
        return false;
    }

    // Method that returns true if a destination is relatively close to a current location.
    // The closeness gap is sort of arbitrary but it scales up/down based on total floors.
    public static boolean isClose(int currentLocation, int destination, int timePerFloor, int maxTime)
    {
        return getEstimatedWaitTime(currentLocation, destination, timePerFloor) <= maxTime;
    }

    public static int calculateMaxWaitTime(int numFloors, int timePerFloor, int doorTime)
    {
        return numFloors * (timePerFloor + doorTime) * 2;
    }

    public static int calculateRideWaitTime(int numFloors, int timePerFloor, int doorTime)
    {
        return numFloors * (timePerFloor + doorTime);
    }

    public static int getEstimatedWaitTime(int currentLocation, int destination, int timePerFloor)
    {
        return Math.abs(currentLocation - destination) * timePerFloor;
    }
}
