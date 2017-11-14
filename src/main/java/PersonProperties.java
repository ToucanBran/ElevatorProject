package main.java;

/**
 * PersonProperties class holds standard information about a person. This is mostly used for the building log so
 * reports can be built around the information.
 *
 * @author Brandon Gomez
 */
public class PersonProperties
{
    private String id;
    private int destination, start;
    private double waitTime = 0, rideTime = 0;

    public PersonProperties(PersonProperties p)
    {
        this.id = p.id;
        this.destination = p.getDestination();
        this.start = p.getStart();
        this.waitTime = p.getWaitTime();
        this.rideTime = p.getRideTime();
    }

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
