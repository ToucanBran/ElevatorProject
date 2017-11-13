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

    private ArrayList<String> requestBackLog = new ArrayList<>();

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

    public ArrayList<Integer> request(int currentFloor, int direction, int excludeElevator)
    {
        String key = String.format("%d-%d", currentFloor, direction);
        requestBackLog.add(key);
        ArrayList<String> removeRequests = new ArrayList<>();
        ArrayList<Integer> requestedElevatorIds = new ArrayList<>();

        for (String request : requestBackLog)
        {
            int maxFloorTime = Building.getInstance().getElevatorProperties().getMaxFloorTime();
            int requestFloor = Integer.parseInt(request.split("-",2)[0]);
            int requestDirection = Integer.parseInt(request.split("-",2)[1]);
            int elevatorId = chooseElevator(requestFloor, requestDirection, Building.getInstance().getElevators(), maxFloorTime, excludeElevator);

            if (elevatorId != -1)
            {
                Elevator elevator = getElevators().values().stream().filter(e -> e.elevatorId == elevatorId).findAny().get();
                addStop(requestFloor, requestDirection, elevator);
                removeRequests.add(request);
                requestedElevatorIds.add(elevatorId);
            }
        }
        requestBackLog.removeAll(removeRequests);
        return requestedElevatorIds;
    }

    public int chooseElevator(int currentFloor, int requestedDirection, Collection<Elevator> elevators, int maxFloorTime,
                              int excludeElevatorId)
    {
        int maxFloor = Building.getInstance().getFloors().size();
        boolean isTopOrBottomFloor = (currentFloor == maxFloor) || (currentFloor == 1);
        Pair<Elevator, Integer> elevatorAndWaitTime = null;

        List<Elevator> candidateElevators = elevators.stream().filter(elevator ->
                Helpers.onWay(elevator.getLocation(), elevator.getDirection(), currentFloor)
                        && (isTopOrBottomFloor || requestedDirection == elevator.getRequestedDirection() || elevator.getDirection() == Directions.IDLE))
                .collect(Collectors.toList());

        for (Elevator elevator : candidateElevators)
        {
            int elLocation = elevator.getLocation();

            //boolean that returns true if the elevator is on the way of the floor and is somewhat close.
            boolean isClose = Helpers.isClose(elLocation, currentFloor, maxFloorTime, maxWaitTime)
                            && elevator.elevatorId != excludeElevatorId;

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

    private void addStop(int currentFloor, int direction, Elevator elevator)
    {
        String dir = direction < 0 ? "down" : "up";

        log.info(String.format("Elevator %d is going to floor %d for %s request. " +
                        "[Floor Requests: %s][Rider Requests: %s]\n", elevator.elevatorId, currentFloor, dir, elevator.getFloorRequestsString(),
                elevator.getRiderRequestsString()));
        elevator.setRequestedDirection(new FloorRequest(currentFloor, direction));
        elevator.addStop(currentFloor, direction, "Floor");

    }
}
