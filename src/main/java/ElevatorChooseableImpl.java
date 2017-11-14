package main.java;

import javafx.util.Pair;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the "Standard" algorithm for choosing an elevator based on the current
 * ElevatorChoiceProperties object passed to ti.
 *
 * @author Brandon Gomez
 */
public class ElevatorChooseableImpl implements Chooseable<Integer, ElevatorChoiceProperties>
{
    @Override
    public Integer choose(ElevatorChoiceProperties choices)
    {
        int currentFloor = choices.getCurrentFloor(),
                requestedDirection = choices.getRequestedDirection(),
                maxFloorTime = choices.getMaxFloorTime(),
                maxWaitTime = choices.getMaxWaitTime(),
                maxFloor = Building.getInstance().getTopFloor();

        Collection<Elevator> elevators = choices.getElevators();


        Pair<Elevator, Integer> elevatorAndWaitTime = null;

        List<Elevator> candidateElevators = elevators.stream().filter(elevator ->
                    onWay(elevator, currentFloor, maxFloor, requestedDirection))
                .collect(Collectors.toList());

        for (Elevator elevator : candidateElevators)
        {
            int elLocation = elevator.getLocation();

            //boolean that returns true if the elevator is on the way of the floor and is somewhat close.
            boolean isClose = Helpers.isClose(elLocation, currentFloor, maxFloorTime, maxWaitTime);

            if (isClose || elevator.isIdle())
            {
                // gets elevator with closest wait time
                int currentWaitTime = Helpers.getEstimatedWaitTime(elLocation, currentFloor, maxFloorTime);
                if (elevatorAndWaitTime == null || elevatorAndWaitTime.getValue() > currentWaitTime)
                {
                    elevatorAndWaitTime = new Pair(elevator, currentWaitTime);
                }
            }
        }
        if (elevatorAndWaitTime != null)
            return elevatorAndWaitTime.getKey().elevatorId;
        else
            return -1;
    }

    private boolean onWay(Elevator elevator, int currentFloor, int maxFloor, int requestedDirection)
    {
        boolean isTopOrBottomFloor = (currentFloor == maxFloor) || (currentFloor == 1);
        boolean onWay = Helpers.onWay(elevator.getLocation(), elevator.getDirection(), currentFloor)
                && (isTopOrBottomFloor
                || requestedDirection == elevator.getRequestedDirection()
                || elevator.getDirection() == Directions.IDLE);

            if (onWay && !isTopOrBottomFloor)
            {
                if (elevator.getDirection() != requestedDirection && elevator.getDirection() != Directions.IDLE)
                    onWay = elevator.isStoppingAt(currentFloor);
            }
        return onWay;
    }
}
