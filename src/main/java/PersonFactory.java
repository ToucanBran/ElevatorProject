package main.java;

/**
 * Factory for creating Person objects based on type specified.
 *
 * @author Brandon Gomez
 */
public class PersonFactory
{
    public static Person createPerson(int destination, String name, int start, String type) throws IllegalArgumentException
    {
            if (type.equalsIgnoreCase("Standard"))
                return new StandardPerson(destination, name, start);

        return null;
    }
}
