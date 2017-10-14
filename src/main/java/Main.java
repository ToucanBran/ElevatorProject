package main.java;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main
{
    private static final Logger logger = LogManager.getLogger("HelloWorld");

    public static void main(String[] args)
    {

        Building.getInstance().setupBuilding();
        ElevatorController ec = Building.getInstance().getElevatorController();
        Building.getInstance().getFloors().forEach((floorNo, floor)->
        {
            if(floor.getWaiting().stream().anyMatch((person) -> person.getDestination() < floorNo))
                ec.request(floorNo, -1);
            else if(floor.getWaiting().stream().anyMatch((person) -> person.getDestination() > floorNo))
                ec.request(floorNo, 1);
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
