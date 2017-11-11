package tests;


import main.java.*;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildingTests
{
    private final Logger log = Logger.getRootLogger();

    @Test
    public void Test1()
    {
        TimeManager.getInstance().runTime(60, 30);
    }

    @Test
    public void elevatorControllerChooseElevatorTest()
    {
        ElevatorProperties ep = new ElevatorProperties();
        Elevator e1 = new Elevator(1, ep);
        e1.setDirection(Directions.UP);
        e1.setRequestedDirection(new FloorRequest(17, Directions.DOWN));
        e1.addStop(17, Directions.UP,"Floor");

        Elevator e2 = new Elevator(2, ep);
        e2.setDirection(Directions.UP);
        e2.setRequestedDirection(new FloorRequest(19, Directions.DOWN));
        e2.addStop(19, Directions.UP,"Rider");

        ElevatorController ec = new ElevatorController();
        ec.setMaxWaitTime(30);

        ArrayList<Elevator> elevators = new ArrayList<>();
        elevators.add(e1);
        elevators.add(e2);

     //   int chosenElevator = ec.chooseElevator(19, Directions.DOWN, elevators, 1);
       // assertTrue(chosenElevator == e2.elevatorId);
    }

}