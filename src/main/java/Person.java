package main.java;

public class Person
{
    private int destination, start;
    double waitTime = 0, rideTime = 0;
    private String name;
    private boolean riding = false, waiting = false;
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

    private void setWaitTime(double waitTime)
    {
        this.waitTime = waitTime;
    }

    public double getRideTime()
    {
        return rideTime;
    }

    private void setRideTime(double rideTime)
    {
        this.rideTime = rideTime;
    }

    public void beginWait()
    {
        waiting = true;
        logTimes();
        setWaitTime(TimeManager.getInstance().getCurrentTime());
    }

    public void endRide()
    {
        riding = false;
        setRideTime(TimeManager.getInstance().getCurrentTime() - rideTime);
        logTimes();
    }

    public void enterElevator()
    {
        riding = true;
        waiting = false;
        setWaitTime(TimeManager.getInstance().getCurrentTime() - waitTime);
        logTimes();
        setRideTime(TimeManager.getInstance().getCurrentTime());
    }
    public String toString()
    {
        return this.name;
    }

    public int getStart()
    {
        return start;
    }

    private void logTimes()
    {
        Building.getInstance().addPersonStat(new PersonProperties(name, destination, start, waitTime, rideTime));
    }
}
