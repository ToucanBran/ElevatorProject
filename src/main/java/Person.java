package main.java;

public class Person
{
    private int destination, waitTime = 0, rideTime = 0;
    private String name;

    public Person(int destination, String name)
    {
        this.destination = destination;
        this.name = name;
    }

    public int getDestination()
    {
        return destination;
    }

    public String getName()
    {
        return name;
    }
}
