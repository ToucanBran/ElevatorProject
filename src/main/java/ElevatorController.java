package main.java;

import javafx.util.Pair;
import main.elevatorgui.gui.ElevatorDisplay;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class ElevatorController
{
    private final Logger log = Logger.getRootLogger();
    private HashMap<Integer, Elevator> elevators = new HashMap<>();

    //TODO: Implement this
    private HashMap<Integer, Integer> requestBackLog = new HashMap<>();

    //TODO: Error check these
    private int maxWaitTime = 0, maxRideTime = 0;

    public void setMaxWaitTime(int maxWaitTime)
    {
        this.maxWaitTime = maxWaitTime;
    }

    public void setMaxRideTime(int maxRideTime)
    {
        this.maxRideTime = maxRideTime;
    }

    public void setElevators(int amountOfElevators, ElevatorProperties ep)
    {
        for (int i = 0; i < amountOfElevators; i++)
        {
            Elevator elevator = new Elevator(i + 1, ep);
            elevators.put(elevator.elevatorId, elevator);
            ElevatorDisplay.getInstance().addElevator(elevator.elevatorId, 1);
        }
    }

    public HashMap<Integer, Elevator> getElevators()
    {
        return elevators;
    }

    public void request(int currentFloor, int requestedDirection, int excludeElevator)
    {
        boolean requestFilled = false;
        int maxFloorTime = Building.getInstance().getElevatorProperties().getMaxFloorTime();

        int elevatorId = chooseElevator(currentFloor, requestedDirection, Building.getInstance().getElevators(), maxFloorTime, excludeElevator);

        if (elevatorId != -1)
        {
            Elevator elevator = Building.getInstance().getElevators().stream().filter(e -> e.elevatorId == elevatorId).findAny().get();
            addStop(currentFloor, requestedDirection, elevator);
        }
        // This will happen when no elevators are close and all elevators are in use.
        else
        {
            requestBackLog.put(currentFloor, requestedDirection);
        }
    }
    public void request(int currentFloor, int requestedDirection)
    {
       request(currentFloor, requestedDirection, -1);
    }
    public int chooseElevator(int currentFloor, int requestedDirection, Collection<Elevator> elevators, int maxFloorTime,
                              int excludeElevatorId)
    {
        int maxFloor = Building.getInstance().getFloors().size();
        Pair<Elevator, Integer> elevatorAndWaitTime = null;

        List<Elevator> candidateElevators = elevators.stream().filter(elevator ->
                Helpers.onWay(elevator.getLocation(),elevator.getDirection(), currentFloor)
                && (requestedDirection == elevator.getRequestedDirection() || elevator.getDirection() == Directions.IDLE))
                .collect(Collectors.toList());

        for (Elevator elevator : candidateElevators)
        {
            int elLocation = elevator.getLocation();

            //boolean that returns true if the elevator is on the way of the floor and is somewhat close.
            boolean isOnWayAndClose =
                    Helpers.onWay(elLocation, elevator.getDirection(), currentFloor, elevator.getRequestedDirection(),
                            requestedDirection, elevator.getFloorRequests(), maxFloor)
                    && Helpers.isClose(elLocation, currentFloor, maxFloorTime, maxWaitTime)
                    && elevator.elevatorId != excludeElevatorId;

            if (isOnWayAndClose || elevator.isIdle())
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
    private void addStop(int currentFloor, int direction, Elevator elevator)
    {
        String dir = direction < 0 ? "down" : "up";

        log.info(String.format("Elevator %d is going to floor %d for %s request. " +
                        "[Floor Requests: %s][Rider Requests: %s]\n", elevator.elevatorId, currentFloor, dir, elevator.getFloorRequestsString(),
                elevator.getRiderRequestsString()));
        elevator.setRequestedDirection(new FloorRequest(currentFloor, direction));
        elevator.addStop(currentFloor, direction,"Floor");

    }
}
