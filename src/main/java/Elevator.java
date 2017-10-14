package main.java;


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
    public String elevatorId;
    private ElevatorProperties properties;
    private double nextActionTime;
    private boolean doorsOpen = false;

    public Elevator(ElevatorProperties properties)
    {
        this.properties = properties;
        elevatorId = UUID.randomUUID().toString();
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
                if (!isFull())
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
        checkNextDestination();
    }

    //TODO: Handle when elevator is idle and has floors to go to
    public void checkNextDestination()
    {
        if (stops.size() == 0)
        {
            log.info(String.format("Elevator %s - setting direction to idle\n", elevatorId));
            setDirection(IDLE);
        } else if (getDirection() == UP && !stops.stream().anyMatch((floor) -> floor > getLocation()))
        {
            log.info(String.format("Elevator %s - setting direction to down\n", elevatorId));
            setDirection(DOWN);
        } else if (getDirection() == DOWN && !stops.stream().anyMatch((floor) -> floor < getLocation()))
        {
            log.info(String.format("Elevator %s - setting direction to up\n", elevatorId));
            setDirection(UP);
        } else if (getDirection() == IDLE && stops.size() > 0)
        {
            int direction = (getLocation() - stops.get(0)) < 0 ? UP : DOWN;
            setDirection(direction);
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
                log.info(String.format("Elevator %s - Moving from %d to %d\n", elevatorId, moveable.getLocation(),
                        moveable.getLocation() + moveable.getDirection()));
                checkNextDestination();
                nextActionTime = actionTime + properties.getMaxFloorTime();
                move();
            } else if (isIdle() && (actionTime - nextActionTime) == properties.getMaxIdleTime() && currentFloor != 1)
            {
                stops.add(1);
            }
        }
    }
}
