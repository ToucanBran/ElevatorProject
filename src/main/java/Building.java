package main.java;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import main.elevatorgui.gui.ElevatorDisplay;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

public class Building
{
    private final Logger log = Logger.getRootLogger();
    // Always going to have a building instance so just creating one here
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

    private void setElevators(int amountOfElevators, ElevatorProperties ep)
    {
        ec.setElevators(amountOfElevators, ep);
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

    public void setupBuilding()
    {
        InputStream jsonStream = Main.class.getResourceAsStream("../resources/building.json");
        Reader reader = new InputStreamReader(jsonStream);
        Gson gson = new Gson();

        JsonObject jObj = gson.fromJson(reader,JsonObject.class);
        JsonObject pof = (jObj.get("peopleOnFloors").getAsJsonObject());
        ElevatorProperties ep = gson.fromJson(jObj.get("elevatorProperties"), ElevatorProperties.class);
        Type intArrayListMap = new TypeToken<HashMap<Integer, ArrayList<Person>>>(){}.getType();

        HashMap<Integer, ArrayList<Person>> map = gson.fromJson(pof, intArrayListMap);
        int totalFloors = jObj.get("amountOfFloors").getAsInt();
        int totalElevators = jObj.get("amountOfElevators").getAsInt();

        ElevatorDisplay.getInstance().initialize(totalFloors);
        setElevators(totalElevators, ep);
        setFloors(totalFloors);

        // Map is a key value pair of floor number to an arraylist of people waiting on that floor
        map.entrySet().forEach((people) -> addPeopleToFloor(people.getValue(), people.getKey()));
    }

    public void startElevators()
    {
        ec.start();
    }
}
