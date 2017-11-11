package tests;

import main.java.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElevatorTests
{
    @Test
    public void ElevatorConflictingDirectionsTest()
    {
        Person p1 = new Person(8, "P1", 15);
        Person p2 = new Person(16, "P2", 11);
        ElevatorProperties ep = new ElevatorProperties();
        ep.setMaxCapacity(10);
        Elevator elevator = new Elevator(1, ep);
        // Add a floor request
        elevator.addStop(15, Directions.DOWN, "Floor");
        elevator.setRequestedDirection(new FloorRequest(15, Directions.DOWN));
        //in this scenario, elevator starts at a floor below 3.
        elevator.setDirection(Directions.UP);

        // Add new floor request going the same direction as elevator but with a different requesting
        // direction
        elevator.addStop(11, Directions.UP, "Floor");
        elevator.setRequestedDirection(new FloorRequest(11, Directions.UP));
        //in this scenario, elevator starts at a floor below 3.
        elevator.setDirection(Directions.UP);

        assertTrue(elevator.getFloorRequests().size() == 2);
        // let's say elevator picks up a person and he/she wants to go to a floor above one of the
        // floor requests. The new rider stop is in the same direction of the elevator but is
        // contradicting the initial stop's requested direction. When this is the case, the
        // all floor requests with conflicting directions should be sent back to the EC for
        // a new elevator assignment

        // elevator gets to floor 11...
        Building.getInstance().addToFloor(11, p2);
        elevator.openDoors(11);

        assertTrue(!elevator.getFloorRequests().contains(15));

    }
}
