package main.java;

import java.util.*;
import java.util.stream.IntStream;

public class Building
{
    private static Building building = new Building();
    private ElevatorController ec = new ElevatorController(null);
    private HashMap<Integer, Floor> floors = new HashMap<>();

    private Building(){}

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

    public void setElevators(int amountOfElevators, String type, int maxCapacity,  int maxIdleTime, int maxOpenTime, int maxFloorTime)
    {
        ec.setElevators(amountOfElevators, type, maxCapacity, maxIdleTime, maxOpenTime, maxFloorTime);
    }

    public Floor getFloor(int floorNumber)
    {
        return floors.get(floorNumber);
    }

    public HashMap<Integer, Floor> getFloors()
    {
        return floors;
    }

    public Collection<Elevator> getElevators()
    {
        return ec.getElevators().values();
    }

    public void addPeopleToFloor(ArrayList<Person> people, int floor)
    {
        floors.get(floor).setWaiting(people);
    }

    public ElevatorController getElevatorController()
    {
        return ec;
    }
}
