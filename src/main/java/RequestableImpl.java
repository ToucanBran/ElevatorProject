package main.java;

public class RequestableImpl implements Requestable
{
    final int DOWN = -1, IDLE = 0, UP = 1;
    @Override
    public void request(int currentFloor, int direction)
    {
        for (Elevator elevator : Building.getInstance().getElevators())
        {
            if (elevator.getDirection() == direction && (elevator.getLocation() + direction) == currentFloor)
            {
                elevator.addStop(currentFloor);
                break;
            }
            else if (elevator.isIdle())
            {
                elevator.addStop(currentFloor);
                break;
            }
            else if (onWay(elevator.getLocation(), elevator.getDirection(), currentFloor))
            {
                elevator.addStop(currentFloor);
                break;
            }
        }
    }

    public boolean onWay(int currentFloor, int direction, int destination)
    {
        return Math.abs(currentFloor - destination) > Math.abs((currentFloor + direction) - destination);
    }
}
