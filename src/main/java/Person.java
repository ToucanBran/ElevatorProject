package main.java;

public class Person
{
    private int destination, waitTime = 0, rideTime = 0;

    public Person(int destination)
    {
        this.destination = destination;
    }

    public int getDestination()
    {
        return destination;
    }

    public void incrementWaitTime()
    {
        waitTime++;
    }

    public void incrementRideTime()
    {
        rideTime++;
    }
}
