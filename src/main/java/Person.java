package main.java;

public class Person
{
    private int destination, start;
    double waitTime = 0, rideTime = 0;
    private String name;

    public Person(int destination, String name, int start) throws IllegalArgumentException
    {
        if (destination > Building.getInstance().getFloors().size() || destination < 1)
            throw new IllegalArgumentException(String.format("%s's destination doesn't exist. Person not created.", name));

        this.destination = destination;
        this.name = name;
        this.start = start;
    }

    public int getDestination()
    {
        return destination;
    }

    public String getName()
    {
        return name;
    }

    public double getWaitTime()
    {
        return waitTime;
    }

    public void setWaitTime(double waitTime)
    {
        this.waitTime = waitTime;
    }

    public double getRideTime()
    {
        return rideTime;
    }

    public void setRideTime(double rideTime)
    {
        this.rideTime = rideTime;
        Building.getInstance().addPersonStat(new PersonProperties(name, destination, start, waitTime, rideTime));
    }

    public String toString()
    {
        return this.name;
    }

    public int getStart()
    {
        return start;
    }
}
