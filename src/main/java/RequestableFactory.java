package main.java;

public class RequestableFactory
{
    // Adding param for future cases but for now classes just pass in null
    // or whatever since there's only one type of impl.
    public static Requestable createRequestable(String type){return new ElevatorRequestableImpl();}
}
