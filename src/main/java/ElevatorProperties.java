package main.java;
/**
 * ElevatorProperties holds information about the Building's elevators such as the maxmimum capacity, how long the doors
 * stay open, etc.
 *
 * @author Brandon Gomez
 */
public class ElevatorProperties
{
    private int maxCapacity;
    private int maxIdleTime;
    private int maxOpenTime;
    private int maxFloorTime;
    private String elevatorMovement;
    private String type;

    public String getElevatorMovement()
    {
        return elevatorMovement;
    }

    public void setElevatorMovement(String elevatorMovement)
    {
        this.elevatorMovement = elevatorMovement;
    }

    public int getMaxCapacity()
    {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxIdleTime()
    {
        return maxIdleTime;
    }


    public int getMaxOpenTime()
    {
        return maxOpenTime;
    }

    public void setMaxOpenTime(int maxOpenTime)
    {
        this.maxOpenTime = maxOpenTime;
    }

    public int getMaxFloorTime()
    {
        return maxFloorTime;
    }

    public void setMaxFloorTime(int maxFloorTime)
    {
        this.maxFloorTime = maxFloorTime;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
