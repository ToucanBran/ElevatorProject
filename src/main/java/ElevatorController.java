package main.java;

import javafx.util.Pair;
import main.elevatorgui.gui.ElevatorDisplay;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ElevatorController class is responsible for handling floor requests and assigning them to the building's elevators *
 * @author Brandon Gomez
 */
public class ElevatorController
{
    private final Logger log = Logger.getRootLogger();
    private HashMap<Integer, Elevator> elevators = new HashMap<>();
    private Chooseable<Integer, ElevatorChoiceProperties> chooseableImpl = ChooseableFactory.createChooseable("Standard");
    private ArrayList<String> requestBackLog = new ArrayList<>();

    //TODO: Error check these
    private int maxWaitTime = 0;

    public void setPickingAlgorithm(String type)
    {
        Chooseable algorithm = ChooseableFactory.createChooseable(type);
        if (algorithm == null)
            System.out.println("Invalid elevator choice algorithm. Setting to standard.");
        else
            chooseableImpl = algorithm;
    }

    public void setMaxWaitTime(int maxWaitTime)
    {
        this.maxWaitTime = maxWaitTime;
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

    public ArrayList<Integer> request(int currentFloor, int direction)
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

            ElevatorChoiceProperties ecp = new ElevatorChoiceProperties(requestFloor, requestDirection, maxFloorTime,
                    maxWaitTime, Building.getInstance().getElevators());

            int elevatorId = chooseableImpl.choose(ecp);
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
