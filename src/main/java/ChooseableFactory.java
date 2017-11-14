package main.java;

/**
 * Factory class for creating different Chooseables based on the type of algorithm
 * the developer wants to use
 *
 * @author Brandon Gomez
 */
public class ChooseableFactory
{
    public static Chooseable createChooseable(String type)
    {
        if (type.equalsIgnoreCase("Standard"))
            return new ElevatorChooseableImpl();

        return null;
    }
}
