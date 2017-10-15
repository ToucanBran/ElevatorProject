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
            boolean isOnWayAndClose = Helpers.onWay(elLocation, elevator.getDirection(), currentFloor)
                    && Helpers.isClose(elLocation, currentFloor);

            if (isOnWayAndClose || elevator.isIdle())
            {
                elevator.setRequestedDirection(direction);
                elevator.addStop(currentFloor);
                break;
            }
        }
    }
}
