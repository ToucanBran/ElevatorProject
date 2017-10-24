package main.java;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.elevatorgui.gui.ElevatorDisplay;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Building
{
    private final Logger log = Logger.getRootLogger();
    // Always going to have a building instance so just creating one here
    private static Building building = new Building();
    private ElevatorController ec = new ElevatorController(null);
    private HashMap<Integer, Floor> floors = new HashMap<>();

    private Building(){
        setupBuilding();
    }

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

    // Used if you want to create people in the JSON file.
    private void addPeopleToFloor(ArrayList<Person> people, int floor)
    {
        people.stream().forEach((person) -> {
            String direction = person.getDestination() > floor ? "up" : "down";
            log.info(String.format("Person %s created on Floor %d, wants to go %s to Floor %d",
                    person.getName(), floor, direction, person.getDestination()));
            floors.get(floor).setWaiting(people);
        });

    }

    public void floorButtonPress(int currentFloor, int direction)
    {
        ec.request(currentFloor, direction);
    }

    // SetupBuilding reads in the JSON file and sets up building/elevator properties.
    private void setupBuilding()
    {
        InputStream jsonStream = Main.class.getResourceAsStream("../resources/building.json");
        Reader reader = new InputStreamReader(jsonStream);
        Gson gson = new Gson();

        JsonObject jObj = gson.fromJson(reader,JsonObject.class);
        ElevatorProperties ep = gson.fromJson(jObj.get("elevatorProperties"), ElevatorProperties.class);

        int totalFloors = jObj.get("amountOfFloors").getAsInt();
        int totalElevators = jObj.get("amountOfElevators").getAsInt();

        ElevatorDisplay.getInstance().initialize(totalFloors);
        setElevators(totalElevators, ep);
        setFloors(totalFloors);
    }

    public void addToFloor(int floor, Person p)
    {
        floors.get(floor).setWaiting(p);
    }
}
