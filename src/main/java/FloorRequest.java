package main.java;

public class FloorRequest
{
    private int floorNumber;
    private int direction;

    public FloorRequest(int floorNumber, int direction)
    {
        this.floorNumber = floorNumber;
        this.direction = direction;
    }

    public int getFloorNumber()
    {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber)
    {
        this.floorNumber = floorNumber;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }
}
