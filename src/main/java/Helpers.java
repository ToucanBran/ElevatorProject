package main.java;

public class Helpers
{
    public static boolean onWay(int currentLocation, int direction, int destination)
    {
        return Math.abs(currentLocation - destination) > Math.abs((currentLocation + direction) - destination);
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

    public static int getEstimatedRidTime(int currentLocation, int destination, int timePerFloor)
    {
        return Math.abs(currentLocation - destination) * timePerFloor;
    }
}
