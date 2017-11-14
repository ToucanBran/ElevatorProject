package main.java;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.elevatorgui.gui.ElevatorDisplay;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * Building class Singleton object which holds all relevant information for the entire building
 *
 * @author Brandon Gomez
 */
public class Building
{
    // Always going to have a building instance so just creating one here
    private static Building building;
    private final Logger log = Logger.getRootLogger();
    private ElevatorController ec = new ElevatorController();
    private HashMap<Integer, Floor> floors = new HashMap<>();
    private ElevatorProperties elevatorProperties;

    // Building log keeps track of both ride statistics and person statistics
    private BuildingLog buildingLog = new BuildingLog();
    private int topFloor;

    private Building()
    {
        try
        {
            setupBuilding();
        } catch (BuildSetupException e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static Building getInstance()
    {
        if (building == null)
            building = new Building();

        return building;
    }

    private void setFloors(int amountOfFloors, String type) throws BuildSetupException
    {
        for (int i = 1; i <= amountOfFloors; i++)
        {
            Floor floor = FloorFactory.createFloor(type);
            if (floor == null)
                throw new BuildSetupException("Error with creating floor. Make sure the floor is a valid type.");
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
        people.stream().forEach((person) ->
        {
            String direction = person.getDestination() > floor ? "up" : "down";
            log.info(String.format("Person %s created on Floor %d, wants to go %s to Floor %d",
                    person.getName(), floor, direction, person.getDestination()));
            floors.get(floor).setWaiting(people);
        });

    }

    public ArrayList<Integer> floorButtonPress(int currentFloor, int direction)
    {
        return ec.request(currentFloor, direction);
    }


    // SetupBuilding reads in the JSON file and sets up building/elevator properties.
    private void setupBuilding() throws BuildSetupException
    {
        Gson gson = new Gson();

        ElevatorProperties ep = gson.fromJson(Helpers.getBuildingJson("elevatorProperties"), ElevatorProperties.class);
        String typeOfFloor = Helpers.getBuildingJson("typeOfFloor").getAsString();
        String controllerAlgorithm = Helpers.getBuildingJson("controllerAlgorithm").getAsString();
        int totalFloors = Helpers.getBuildingJson("amountOfFloors").getAsInt();
        int totalElevators = Helpers.getBuildingJson("amountOfElevators").getAsInt();

        ElevatorDisplay.getInstance().initialize(totalFloors);
        ec.setMaxWaitTime(Helpers.calculateMaxWaitTime(totalFloors, ep.getMaxFloorTime(), ep.getMaxOpenTime()));
        ec.setPickingAlgorithm(controllerAlgorithm);

        this.elevatorProperties = ep;
        setElevators(totalElevators, ep);
        setFloors(totalFloors, typeOfFloor);
        topFloor = totalFloors;
    }

    public void addToFloor(int floor, Person p)
    {
        floors.get(floor).setWaiting(p);
    }

    public ElevatorProperties getElevatorProperties()
    {
        return elevatorProperties;
    }


    //  Overloaded entry adder for different types of logs the building keeps track of.
    public void addToBuildingLog(TravelProperties travelProperties)
    {
        buildingLog.addEntry(travelProperties);
    }

    //  Overloaded getter for returning the rider statistics log
    public HashMap<String, TravelProperties> getRiderStats()
    {
        return buildingLog.getStatistics("rider");
    }

    //  Overloaded entry adder for different types of logs the building keeps track of.
    public void addToBuildingLog(PersonProperties personProperties)
    {
        buildingLog.addEntry(personProperties);
    }

    //  Overloaded getter for returning the person statistics log
    public HashMap<String, PersonProperties> getPersonStats()
    {
        return buildingLog.getStatistics("person");
    }

    public int getTopFloor()
    {
        return topFloor;
    }
}
