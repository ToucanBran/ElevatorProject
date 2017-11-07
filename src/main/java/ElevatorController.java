package main.java;

import javafx.util.Pair;
import main.elevatorgui.gui.ElevatorDisplay;
import org.apache.log4j.Logger;

import java.util.HashMap;

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

    public void request(int currentFloor, int direction)
    {
        boolean requestFilled = false;
        int maxFloorTime = Building.getInstance().getElevatorProperties().getMaxFloorTime();
        Pair<Elevator, Integer> elevatorAndWaitTime = null;
        for (Elevator elevator : Building.getInstance().getElevators())
        {
            int elLocation = elevator.getLocation();

            //boolean that returns true if the elevator is on the way of the floor and is somewhat close.
            boolean isOnWayAndClose = Helpers.onWay(elLocation, elevator.getDirection(), currentFloor)
                    && Helpers.isClose(elLocation, currentFloor, maxFloorTime, maxWaitTime);

            if (isOnWayAndClose || elevator.isIdle())
            {
                // gets elevator with closest wait time
                int currentWaitTime = Helpers.getEstimatedWaitTime(elLocation, currentFloor, maxFloorTime);
                if (elevatorAndWaitTime == null || elevatorAndWaitTime.getValue() > currentWaitTime)
                    elevatorAndWaitTime = new Pair(elevator, currentWaitTime);
            }
        }

        if (elevatorAndWaitTime != null)
        {
            addStop(currentFloor, direction, elevatorAndWaitTime.getKey());
        }
        // This will happen when no elevators are close and all elevators are in use.
        else
        {
            requestBackLog.put(currentFloor, direction);
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
