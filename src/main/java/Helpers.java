package main.java;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;

/**
 * Helper class used for basic calculations and JSON parsing
 *
 * @author Brandon Gomez
 */
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
            else if (direction == floorRequestedDirection)
                return true;

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

    public static JsonElement getBuildingJson(String key) throws BuildSetupException
    {
        try
        {
            InputStream jsonStream = Main.class.getResourceAsStream("../resources/building.json");
            Reader reader = new InputStreamReader(jsonStream);
            Gson gson = new Gson();

            JsonObject jObj = gson.fromJson(reader, JsonObject.class);
            return jObj.get(key);
        }
        catch (NullPointerException ex)
        {
            throw new BuildSetupException("Building.json is either missing required fields or is unable to be located. Please check" +
                    " your file and try again.");
        }
    }
}
