package main.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Main
{
    private static final Logger logger = LogManager.getLogger("HelloWorld");

    public static void main(String[] args)
    {
        ArrayList<Person> people = new ArrayList<>();

        Building.getInstance().setFloors(20);
        Person p1 = new Person(1);
        people.add(p1);
        Building.getInstance().setElevators(2, null, 10, 10, 5, 2 );
        Building.getInstance().addPeopleToFloor(people, 10);
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
