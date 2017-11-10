package main.java;

public class PersonProperties
{
    private String id;
    private int destination, start;
    private double waitTime = 0, rideTime = 0;

    public PersonProperties(String id, int destination, int start, double waitTime, double rideTime)
    {
        this.id = id;
        this.destination = destination;
        this.start = start;
        this.waitTime = waitTime;
        this.rideTime = rideTime;
    }

    public int getDestination()
    {
        return destination;
    }

    public int getStart()
    {
        return start;
    }

    public double getWaitTime()
    {
        return waitTime;
    }

    public double getRideTime()
    {
        return rideTime;
    }

    public String getId()
    {
        return id;
    }

    public double getTotalTime()
    {
        return waitTime + rideTime;
    }
}
