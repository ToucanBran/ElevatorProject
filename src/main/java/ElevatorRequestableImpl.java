package main.java;

import org.apache.log4j.Logger;

import java.util.Collection;

public class ElevatorRequestableImpl implements Requestable
{
    private final Logger log = Logger.getRootLogger();
    final int DOWN = -1, IDLE = 0, UP = 1;

    // Performs the logic to choose the best elevator to pick up the person
    @Override
    public void request(int currentFloor, int direction)
    {
        boolean requestFilled = false;
        for (Elevator elevator : Building.getInstance().getElevators())
        {
            int elLocation = elevator.getLocation();

            //boolean that returns true if the elevator is on the way of the floor and is somewhat close.
            boolean isOnWayAndClose = Helpers.onWay(elLocation, elevator.getDirection(), currentFloor)
                    && Helpers.isClose(elLocation, currentFloor);

            if (isOnWayAndClose || elevator.isIdle())
            {
                addStop(currentFloor, direction, elevator);
                requestFilled = true;
                break;
            }
        }

        // This will happen when no elevators are close and all elevators are in use.
        if (!requestFilled)
        {
            Elevator e1 = Building.getInstance().getElevators().iterator().next();
            addStop (currentFloor, direction, e1);
        }
    }

    private void addStop(int currentFloor, int direction, Elevator elevator)
    {
        String dir = direction < 0 ? "down" : "up";

        log.info(String.format("Elevator %d is going to floor %d for %s request. " +
                        "[Floor Requests: %s][Rider Requests: %s]\n", elevator.elevatorId, currentFloor, dir, elevator.getFloorRequestsString(),
                elevator.getRiderRequestsString()));
        elevator.setRequestedDirection(direction);
        elevator.addStop(currentFloor, "Floor");

    }
}
