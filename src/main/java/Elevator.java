package main.java;


import main.elevatorgui.gui.ElevatorDisplay;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Elevator implements Moveable
{
    private final Logger log = Logger.getRootLogger();
    private ArrayList<Person> riders = new ArrayList<>();
    private ArrayList<Integer> stops = new ArrayList<>();
    private Moveable moveable;
    public int elevatorId;
    private int requestedDirection;
    private ElevatorProperties properties;
    private double nextActionTime;
    private boolean doorsOpen = false;

    public Elevator(int id, ElevatorProperties properties)
    {
        this.properties = properties;
        elevatorId = id;
        moveable = MoveableFactory.createMoveable(properties.getType());
    }

    @Override
    public void move()
    {
        moveable.move();
    }

    @Override
    public int getDirection()
    {
        return moveable.getDirection();
    }

    @Override
    public void setDirection(int direction)
    {
        moveable.setDirection(direction);
    }

    @Override
    public int getLocation()
    {
        return moveable.getLocation();
    }

    public int getRequestedDirection()
    {
        return requestedDirection;
    }

    public void setRequestedDirection(int requestedDirection)
    {
        this.requestedDirection = requestedDirection;
    }

    public boolean isIdle()
    {
        return moveable.getDirection() == IDLE;
    }

    public boolean isFull()
    {
        return riders.size() == properties.getMaxCapacity();
    }

    public void addStop(int floor)
    {
        stops.add(floor);
        if (stops.size() == 1)
            checkNextDestination();
    }

    public void stop(int currentFloor)
    {
        stops.removeIf((floor) -> floor == currentFloor);
        openDoors(currentFloor);
    }

    public ArrayList<Person> addRiders(ArrayList<Person> people)
    {
        if (people.size() > 0)
        {
            for (Person person : people)
            {
                int direction = requestedDirection != IDLE ? requestedDirection : getDirection();
                if (!isFull() && Helpers.onWay(getLocation(), direction, person.getDestination()))
                {
                    log.info(String.format("Elevator %s - Adding person %s\n", elevatorId, person.getName()));
                    riders.add(person);
                    addStop(person.getDestination());
                }
            }
            people.removeAll(riders);
        }

        return people;

    }

    //TODO: Implement wait time
    public void openDoors(int currentFloorNumber)
    {
        log.info(String.format("Elevator %s - Opening door...\n", elevatorId));
        doorsOpen = true;
        ElevatorDisplay.getInstance().openDoors(elevatorId);
        Floor currentFloor = Building.getInstance().getFloor(currentFloorNumber);
        Iterator<Person> iterator = riders.iterator();
        while (iterator.hasNext())
        {
            Person rider = iterator.next();
            if (rider.getDestination() == currentFloorNumber)
            {
                log.info(String.format("Elevator %s - Person %s is leaving elevator.\n", elevatorId, rider.getName()));
                currentFloor.setOffLoaded(rider);
                iterator.remove();
            }
        }

        ArrayList<Person> peopleLeftOnFloor = addRiders(currentFloor.getWaiting());
        currentFloor.setWaiting(peopleLeftOnFloor);
    }

    //TODO: Implement
    public void closeDoors()
    {
        log.info(String.format("Elevator %s - Closing doors.", elevatorId));
        doorsOpen = false;
        ElevatorDisplay.getInstance().closeDoors(elevatorId);
        checkNextDestination();
    }

    public void checkNextDestination()
    {
        if (stops.size() == 0)
        {
            log.info(String.format("Elevator %s - setting direction to idle\n", elevatorId));
            setDirection(IDLE);
        }
        else if (getDirection() == IDLE && stops.size() > 0)
        {
            int direction = (getLocation() - stops.get(0)) < 0 ? UP : DOWN;
            setDirection(direction);
        }
        else if (!stops.stream().anyMatch((floor) -> Helpers.onWay(getLocation(), getDirection(), floor)))
        {
            if (getDirection() == requestedDirection)
            {
                // TODO: make this make more sense.
                setRequestedDirection(IDLE);
            }
            // Hack. This causes the direction to switch from either up to down or down to up.
            setDirection(getDirection() * -1);
        }
    }

    public void doAction(double actionTime)
    {
        if (actionTime >= nextActionTime)
        {

            int currentFloor = moveable.getLocation();
            if (doorsOpen)
            {
                closeDoors();
                if (isIdle())
                    nextActionTime = actionTime;
           } else if (stops.contains(currentFloor))
            {
                log.info(String.format("Elevator %s - stopping.\n", elevatorId));
                nextActionTime = actionTime + properties.getMaxOpenTime();
                stop(currentFloor);
            } else if (stops.size() > 0)
            {
                checkNextDestination();
                log.info(String.format("Elevator %s - Moving from %d to %d\n", elevatorId, getLocation(),
                        moveable.getLocation() + moveable.getDirection()));

                nextActionTime = actionTime + properties.getMaxFloorTime();
                move();
            } else if (isIdle() && (actionTime - nextActionTime) == properties.getMaxIdleTime() && currentFloor != 1)
            {
                setRequestedDirection(DOWN);
                stops.add(1);
            }
            // Hack to get display direction. This code uses an int value for direction because it helps move floors
            // and other cool math stuff.
            ElevatorDisplay.Direction dir = getDirection() > 0 ? ElevatorDisplay.Direction.UP : ElevatorDisplay.Direction.DOWN;
            ElevatorDisplay.getInstance().updateElevator(elevatorId, moveable.getLocation(), riders.size(), dir);
        }
    }
}
