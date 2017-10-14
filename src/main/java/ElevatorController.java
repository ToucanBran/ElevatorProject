package main.java;

import main.elevatorgui.gui.ElevatorDisplay;

import java.util.HashMap;

public class ElevatorController implements Requestable
{
    private Requestable requestable;
    private HashMap<Integer, Elevator> elevators = new HashMap<>();
    private boolean isRunning = false;

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

    public boolean isRunning()
    {
        return isRunning;
    }

    public void start()
    {
        if (!isRunning())
        {
            Building.getInstance().getFloors().forEach((floorNo, floor)->
            {
                if(floor.getWaiting().stream().anyMatch((person) -> person.getDestination() < floorNo))
                    request(floorNo, -1);
                else if(floor.getWaiting().stream().anyMatch((person) -> person.getDestination() > floorNo))
                    request(floorNo, 1);
            });

            double time = 0;
            while (true)
            {
                time = System.currentTimeMillis() / 1000;
                for (Elevator e : Building.getInstance().getElevators())
                {
                    e.doAction(time);
                }
            }
        }
    }
    @Override
    public void request(int destination, int direction)
    {
        requestable.request(destination, direction);
    }
}
