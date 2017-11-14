package main.java;

/**
 * Factory class for creating different types of floors based on the typeOfFloor property in the building.json file
 *
 * @author Brandon Gomez
 */
public class FloorFactory
{
    public static Floor createFloor(String type)
    {
        if (type.equalsIgnoreCase("Standard"))
            return new StandardFloor();

        return null;
    }
}
