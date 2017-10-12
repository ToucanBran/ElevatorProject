package main.java;

import java.util.HashMap;

public class ElevatorController implements Requestable
{
    private Requestable requestable;
    private HashMap<String, Elevator> elevators = new HashMap<>();

    public ElevatorController(String type)
    {
        requestable = RequestableFactory.createRequestable(type);
    }

    public void setElevators(int amountOfElevators, String type, int maxCapacity, int maxIdleTime, int maxOpenTime, int maxFloorTime)
    {
        for(int i = 0; i < amountOfElevators; i++)
        {
            Elevator elevator = new Elevator(type, maxCapacity, maxIdleTime, maxOpenTime, maxFloorTime);
            elevators.put(elevator.elevatorId, elevator);
        }
    }

    public HashMap<String, Elevator> getElevators()
    {
        return elevators;
    }

    @Override
    public void request(int destination, int direction)
    {
        requestable.request(destination, direction);
    }
}
