package main.java;

public class Person
{
    private int destination;
    double waitTime = 0, rideTime = 0;
    private String name;

    public Person(int destination, String name) throws IllegalArgumentException
    {
        if (destination > Building.getInstance().getFloors().size() || destination < 1)
            throw new IllegalArgumentException(String.format("%s's destination doesn't exist. Person not created.", name));

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
    }

    public String toString()
    {
        return this.name;
    }
}
