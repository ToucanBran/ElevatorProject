package main.java;

import main.elevatorgui.gui.ElevatorDisplay;

import java.util.HashMap;

public class ElevatorController implements Requestable
{
    private Requestable requestable;
    private HashMap<Integer, Elevator> elevators = new HashMap<>();

    public ElevatorController(String type)
    {
        requestable = RequestableFactory.createRequestable(type);
    }

    public void setElevators(int amountOfElevators, ElevatorProperties ep)
    {
        for(int i = 0; i < amountOfElevators; i++)
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

    @Override
    public void request(int currentFloor, int direction)
    {
        requestable.request(currentFloor, direction);
    }
}
