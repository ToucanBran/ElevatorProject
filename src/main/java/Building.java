package main.java;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class Building
{
    private final Logger log = Logger.getRootLogger();
    private static Building building = new Building();
    private ElevatorController ec = new ElevatorController(null);
    private HashMap<Integer, Floor> floors = new HashMap<>();

    private Building(){}

    public static Building getInstance()
    {
        return building;
    }

    private void setFloors(int amountOfFloors)
    {
        for(int i = 1; i <= amountOfFloors; i++)
        {
            Floor floor = new Floor();
            floors.put(i, floor);
        }
    }

    private void setElevators(int amountOfElevators, String type, int maxCapacity,  int maxIdleTime, int maxOpenTime, int maxFloorTime)
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

    private void addPeopleToFloor(ArrayList<Person> people, int floor)
    {
        people.stream().forEach((person) -> {
            String direction = person.getDestination() > floor ? "up" : "down";
            log.info(String.format("Person %s created on Floor %d, wants to go %s to Floor %d",
                    person.getName(), floor, direction, person.getDestination()));
            floors.get(floor).setWaiting(people);
        });

    }

    public ElevatorController getElevatorController()
    {
        return ec;
    }

    public void setupBuilding(JsonObject jObj)
    {
        setElevators(jObj.get("amountOfElevators").getAsInt(), jObj.get("type").getAsString(), jObj.get("maxCapacity").getAsInt(),
                jObj.get("maxIdleTime").getAsInt(), jObj.get("maxOpenTime").getAsInt(), jObj.get("maxFloorTime").getAsInt());

        setFloors(jObj.get("amountOfFloors").getAsInt());
        JsonArray jArr = new Gson().fromJson(jObj.get("floors"), JsonArray.class);



    }
}
