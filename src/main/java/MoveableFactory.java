package main.java;
/**
 * Factory for creating moveable objects.
 *
 * @author Brandon Gomez
 */
public class MoveableFactory
{
    // Adding param for future cases but main.java.Elevator will just pass in standard
    // or whatever now since there's only one type of impl
    public static Moveable createMoveable(String type)
    {
        if (type.equalsIgnoreCase("Standard"))
            return new ElevatorMoveableImpl();

        return null;
    }
}
