package main.java;

/**
 * The FloorReqest object is used to store a request from a floor and includes both the floor where the
 * request came from and the requested direction.
 *
 * @author Brandon Gomez
 */
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

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }
}
