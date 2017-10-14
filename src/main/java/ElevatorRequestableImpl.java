package main.java;

public class ElevatorRequestableImpl implements Requestable
{
    final int DOWN = -1, IDLE = 0, UP = 1;
    @Override
    public void request(int currentFloor, int direction)
    {
        for (Elevator elevator : Building.getInstance().getElevators())
        {
            int elLocation = elevator.getLocation();

            if (elevator.getDirection() == direction && (elLocation + direction) == currentFloor)
            {
                elevator.addStop(currentFloor);
                break;
            }
            else if (elevator.isIdle())
            {
                elevator.addStop(currentFloor);
                break;
            }
            else if (Helpers.onWay(elLocation, elevator.getDirection(), currentFloor) && Helpers.isClose(elLocation, currentFloor))
            {
                elevator.addStop(currentFloor);
                break;
            }
        }
    }
}
