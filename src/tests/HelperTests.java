package tests;


import main.java.*;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelperTests
{
    //CD = Current Direction, RD = Requested Direction (i.e. the new direction the elevator will go once it reaches a
    // requested for) FRD = Floor requested direction
    @Test
    public void OnWayHelperTest()
    {
        int currentFloor = 13, destination = 17;
        assertTrue(Helpers.onWay(currentFloor, Directions.UP, destination));
        assertFalse(Helpers.onWay(currentFloor, Directions.DOWN, destination));
    }

    @Test
    public void onWayOverloadTest_GoingUp_Opposite_CDRD()
    {
        int topFloor = 20;
        int currentFloor = 10;
        int currentDirection = Directions.UP;
        int requestedDirection = Directions.DOWN;
        Set<Integer> elevatorCurrentRequestedStops = new HashSet<>();
        elevatorCurrentRequestedStops.add(17);

        int requestingFloor_Low = 15;
        int requestingFloorLowDirection_ScenarioOne = Directions.DOWN;
        int requestingFloorLowDirection_ScenarioTwo = Directions.UP;

        int requestingFloor_High = 20;
        int requestingFloorHighDirection_ScenarioOne = Directions.DOWN;
        int requestingFloorHighDirection_ScenarioTwo = Directions.UP;


        boolean onWay_One_Low = Helpers.onWay(currentFloor, currentDirection, requestingFloor_Low, requestedDirection,
                requestingFloorLowDirection_ScenarioOne, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_Two_Low = Helpers.onWay(currentFloor, currentDirection, requestingFloor_Low, requestedDirection,
                requestingFloorLowDirection_ScenarioTwo, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_One_High = Helpers.onWay(currentFloor, currentDirection, requestingFloor_High, requestedDirection,
                requestingFloorHighDirection_ScenarioOne, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_Two_High = Helpers.onWay(currentFloor, currentDirection, requestingFloor_High, requestedDirection,
                requestingFloorHighDirection_ScenarioTwo, elevatorCurrentRequestedStops, topFloor);

        assertFalse(onWay_One_Low);
        assertTrue(onWay_Two_Low);
        assertTrue(onWay_One_High);
        assertTrue(onWay_Two_High);
    }
    @Test
    public void onWayOverloadTest_GoingUp_Same_CDRD()
    {
        int topFloor = 20;
        int currentFloor = 10;
        int currentDirection = Directions.UP;
        int requestedDirection = Directions.UP;
        Set<Integer> elevatorCurrentRequestedStops = new HashSet<>();;
        elevatorCurrentRequestedStops.add(17);

        int requestingFloor_Low = 5;
        int requestingFloorLowDirection_ScenarioOne = Directions.DOWN;
        int requestingFloorLowDirection_ScenarioTwo = Directions.UP;

        int requestingFloor_High = 20;
        int requestingFloorHighDirection_ScenarioOne = Directions.DOWN;
        int requestingFloorHighDirection_ScenarioTwo = Directions.UP;


        boolean onWay_One_Low = Helpers.onWay(currentFloor, currentDirection, requestingFloor_Low, requestedDirection,
                requestingFloorLowDirection_ScenarioOne, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_Two_Low = Helpers.onWay(currentFloor, currentDirection, requestingFloor_Low, requestedDirection,
                requestingFloorLowDirection_ScenarioTwo, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_One_High = Helpers.onWay(currentFloor, currentDirection, requestingFloor_High, requestedDirection,
                requestingFloorHighDirection_ScenarioOne, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_Two_High = Helpers.onWay(currentFloor, currentDirection, requestingFloor_High, requestedDirection,
                requestingFloorHighDirection_ScenarioTwo, elevatorCurrentRequestedStops, topFloor);

        assertFalse(onWay_One_Low);
        assertFalse(onWay_Two_Low);
        assertTrue(onWay_One_High);
        assertTrue(onWay_Two_High);
    }
    @Test
    public void onWayOverloadTest_GoingDown_Opposite_CDRD()
    {
        int topFloor = 20;
        int currentFloor = 10;
        int currentDirection = Directions.DOWN;
        int requestedDirection = Directions.UP;
        Set<Integer> elevatorCurrentRequestedStops = new HashSet<>();;
        elevatorCurrentRequestedStops.add(5);

        int requestingFloor_Low = 1;
        int requestingFloorLowDirection_ScenarioOne = Directions.DOWN;
        int requestingFloorLowDirection_ScenarioTwo = Directions.UP;

        int requestingFloor_High = 6;
        int requestingFloorHighDirection_ScenarioOne = Directions.DOWN;
        int requestingFloorHighDirection_ScenarioTwo = Directions.UP;


        boolean onWay_One_Low = Helpers.onWay(currentFloor, currentDirection, requestingFloor_Low, requestedDirection,
                requestingFloorLowDirection_ScenarioOne, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_Two_Low = Helpers.onWay(currentFloor, currentDirection, requestingFloor_Low, requestedDirection,
                requestingFloorLowDirection_ScenarioTwo, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_One_High = Helpers.onWay(currentFloor, currentDirection, requestingFloor_High, requestedDirection,
                requestingFloorHighDirection_ScenarioOne, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_Two_High = Helpers.onWay(currentFloor, currentDirection, requestingFloor_High, requestedDirection,
                requestingFloorHighDirection_ScenarioTwo, elevatorCurrentRequestedStops, topFloor);

        assertTrue(onWay_One_Low);
        assertTrue(onWay_Two_Low);
        assertTrue(onWay_One_High);
        assertFalse(onWay_Two_High);
    }
    @Test
    public void onWayOverloadTest_GoingDown_Same_CDRD()
    {
        int topFloor = 20;
        int currentFloor = 10;
        int currentDirection = Directions.DOWN;
        int requestedDirection = Directions.DOWN;
        Set<Integer> elevatorCurrentRequestedStops = new HashSet<>();;
        elevatorCurrentRequestedStops.add(5);

        int requestingFloor_Low = 1;
        int requestingFloorLowDirection_ScenarioOne = Directions.DOWN;
        int requestingFloorLowDirection_ScenarioTwo = Directions.UP;

        int requestingFloor_High = 6;
        int requestingFloorHighDirection_ScenarioOne = Directions.DOWN;
        int requestingFloorHighDirection_ScenarioTwo = Directions.UP;


        boolean onWay_One_Low = Helpers.onWay(currentFloor, currentDirection, requestingFloor_Low, requestedDirection,
                requestingFloorLowDirection_ScenarioOne, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_Two_Low = Helpers.onWay(currentFloor, currentDirection, requestingFloor_Low, requestedDirection,
                requestingFloorLowDirection_ScenarioTwo, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_One_High = Helpers.onWay(currentFloor, currentDirection, requestingFloor_High, requestedDirection,
                requestingFloorHighDirection_ScenarioOne, elevatorCurrentRequestedStops, topFloor);

        boolean onWay_Two_High = Helpers.onWay(currentFloor, currentDirection, requestingFloor_High, requestedDirection,
                requestingFloorHighDirection_ScenarioTwo, elevatorCurrentRequestedStops, topFloor);

        assertTrue(onWay_One_Low);
        assertTrue(onWay_Two_Low);
        assertTrue(onWay_One_High);
        assertFalse(onWay_Two_High);
    }

}