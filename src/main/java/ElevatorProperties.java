package main.java;

public class ElevatorProperties
{
    private int amountOfElevators;
    private int maxCapacity;
    private int maxIdleTime;
    private int maxOpenTime;
    private int maxFloorTime;
    private String type;

    public int getAmountOfElevators()
    {
        return amountOfElevators;
    }

    public void setAmountOfElevators(int amountOfElevators)
    {
        this.amountOfElevators = amountOfElevators;
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

    public void setMaxIdleTime(int maxIdleTime)
    {
        this.maxIdleTime = maxIdleTime;
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
