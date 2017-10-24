package main.java;

public class Helpers
{
    public static boolean onWay(int currentLocation, int direction, int destination)
    {
        return Math.abs(currentLocation - destination) > Math.abs((currentLocation + direction) - destination);
    }

    // Method that returns true if a destination is relatively close to a current location.
    // The closeness gap is sort of arbitrary but it scales up/down based on total floors.
    public static boolean isClose(int currentLocation, int destination)
    {
        int totalFloors = Building.getInstance().getFloors().size();
        int maxGap = totalFloors / 10;
        return Math.abs(currentLocation - destination) <= maxGap;

    }
}
