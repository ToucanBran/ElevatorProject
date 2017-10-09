package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Building
{
    private static Building building = new Building();
    private HashMap<Integer, Floor> floors = new HashMap<>();
    private HashMap<String, Elevator> elevators = new HashMap<>();

    private Building(){};

    public static Building getInstance()
    {
        return building;
    }

    public void setFloors(int amountOfFloors)
    {
        for(int i = 1; i <= amountOfFloors; i++)
        {
            Floor floor = new Floor();
            floors.put(i, floor);
        }
    }

    public void setElevators(int amountOfElevators, String type, int maxCapacity)
    {
        for(int i = 1; i <= amountOfElevators; i++)
        {
            Elevator elevator = new Elevator(type, maxCapacity);
            elevators.put(elevator.elevatorId, elevator);
        }
    }

    public Floor getFloor(int floorNumber)
    {
        return floors.get(floorNumber);
    }

}
