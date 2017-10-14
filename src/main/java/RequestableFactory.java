package main.java;

public class RequestableFactory
{
    public static Requestable createRequestable(String type){return new ElevatorRequestableImpl();}
}
