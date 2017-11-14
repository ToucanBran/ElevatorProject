package main.java;

/**
 * Abstract Person class. All people should have the following information in order to work with this program.
 *
 * @author Brandon Gomez
 */
public abstract class Person
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
        logTimes();
        setWaitTime(TimeManager.getInstance().getCurrentTime());
    }

    public void endRide()
    {
        setRideTime(TimeManager.getInstance().getCurrentTime() - rideTime);
        logTimes();
    }

    public void enterElevator()
    {
        setWaitTime(TimeManager.getInstance().getCurrentTime() - waitTime);
        logTimes();
        setRideTime(TimeManager.getInstance().getCurrentTime());
    }

    public int getStart()
    {
        return start;
    }

    private void logTimes()
    {
        Building.getInstance().addToBuildingLog(new PersonProperties(name, destination, start, waitTime, rideTime));
    }
}
