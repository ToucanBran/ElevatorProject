package tests;

import main.java.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElevatorTests
{

    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = Building.class.getDeclaredField("building");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void ElevatorControllerTest_Floors_OnWay_Different_Requested_Directions()
    {
        final int EXPECTED_ELEVATOR_ID = 1;
        Person p1 = PersonFactory.createPerson(8, "P1", 15, "Normal");
        Person p2 = PersonFactory.createPerson(16, "P2", 11, "Normal");
        Person p3 = PersonFactory.createPerson(16, "P3", 20, "Normal");
        Building.getInstance().addToFloor(11,p1);
        Building.getInstance().addToFloor(15,p2);
        Building.getInstance().addToFloor(20,p3);

        assertTrue(Building.getInstance().floorButtonPress(11, Directions.UP).contains(EXPECTED_ELEVATOR_ID));
        assertFalse(Building.getInstance().floorButtonPress(15, Directions.DOWN).contains(EXPECTED_ELEVATOR_ID));
        assertTrue(Building.getInstance().floorButtonPress(20, Directions.DOWN).contains(EXPECTED_ELEVATOR_ID));
    }

    @Test
    public void ElevatorControllerTest_Floors_OnWay_Same_Requested_Directions()
    {
        final int EXPECTED_ELEVATOR_ID = 1;
        Person p1 = PersonFactory.createPerson(8, "P1", 15, "Normal");
        Person p2 = PersonFactory.createPerson(16, "P2", 11, "Normal");

        Building.getInstance().addToFloor(11,p1);
        Building.getInstance().addToFloor(15,p2);

        assertTrue(Building.getInstance().floorButtonPress(11, Directions.UP).contains(EXPECTED_ELEVATOR_ID));
        assertTrue(Building.getInstance().floorButtonPress(15, Directions.UP).contains(EXPECTED_ELEVATOR_ID));
    }

    @Test
    public void ElevatorControllerTest_Floors_NotOnWay_Same_Requested_Directions()
    {
        final int EXPECTED_ELEVATOR_ID = 1;
        Person p1 = PersonFactory.createPerson(8, "P1", 15, "Normal");
        Person p2 = PersonFactory.createPerson(16, "P2", 5, "Normal");

        Building.getInstance().addToFloor(11,p1);
        Building.getInstance().addToFloor(5,p2);


        Elevator elevator1 = Building.getInstance().getElevators().iterator().next();
        assertTrue(Building.getInstance().floorButtonPress(11, Directions.UP).contains(EXPECTED_ELEVATOR_ID));

        // manually moving elevator for testing purposes
        elevator1.setDirection(Directions.UP);
        elevator1.doAction(1);
        elevator1.doAction(2);
        elevator1.doAction(3);
        elevator1.doAction(4);
        elevator1.doAction(5);
        elevator1.doAction(6);

        assertFalse(Building.getInstance().floorButtonPress(5, Directions.UP).contains(EXPECTED_ELEVATOR_ID));

    }

    @Test
    public void ElevatorControllerTest_Floors_OnWay_Same_Requested_Directions_But_Higher_Floor()
    {
        final int EXPECTED_ELEVATOR_ID = 1;
        Person p1 = PersonFactory.createPerson(2, "P1", 7, "Normal");
        Person p2 = PersonFactory.createPerson(3, "P2", 10, "Normal");

        Building.getInstance().addToFloor(7,p1);
        Building.getInstance().addToFloor(10,p2);


        Elevator elevator1 = Building.getInstance().getElevators().iterator().next();
        assertTrue(Building.getInstance().floorButtonPress(7, Directions.DOWN).contains(EXPECTED_ELEVATOR_ID));

        // manually moving elevator for testing purposes
        elevator1.setDirection(Directions.UP);
        elevator1.doAction(1);
        elevator1.doAction(2);
        elevator1.doAction(3);
        elevator1.doAction(4);
        elevator1.doAction(5);
        elevator1.doAction(6);

        assertFalse(Building.getInstance().floorButtonPress(10, Directions.DOWN).contains(EXPECTED_ELEVATOR_ID));
    }
}
