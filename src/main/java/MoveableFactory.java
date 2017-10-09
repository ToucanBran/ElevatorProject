package main.java;

public class MoveableFactory
{
    // Adding param for future cases but main.java.Elevator will just pass in null
    // or whatever now since there's only one type of impl
    public static Moveable createMoveable(String type)
    {
        return new MoveableImpl();
    }
}
