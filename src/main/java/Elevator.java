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
    private int MAX_CAPACITY, MAX_IDLE_TIME, MAX_DOOR_OPEN_TIME, MAX_FLOOR_TIME;
    public String elevatorId;
    private double nextActionTime;
    private boolean doorsOpen = false;

    public Elevator(String type, int maxCapacity, int maxIdleTime, int maxOpenTime, int maxFloorTime)
    {
        MAX_CAPACITY = maxCapacity;
        MAX_IDLE_TIME = maxIdleTime;
        MAX_DOOR_OPEN_TIME = maxOpenTime;
        MAX_FLOOR_TIME = maxFloorTime;
        elevatorId = UUID.randomUUID().toString();
        moveable = MoveableFactory.createMoveable(type);
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
        return riders.size() == MAX_CAPACITY;
    }


    public void addStop(int floor)
    {
        stops.add(floor);
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
                    log.info(String.format("Adding person %s\n", person.hashCode()));
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
        log.info(String.format("Opening door...\n"));
        doorsOpen = true;

        Floor currentFloor = Building.getInstance().getFloor(currentFloorNumber);
        Iterator<Person> iterator = riders.iterator();
        while (iterator.hasNext())
        {
            Person rider = iterator.next();
            if (rider.getDestination() == currentFloorNumber)
            {
                log.info(String.format("Person %s is leaving the elevator\n", rider.hashCode()));
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
        doorsOpen = false;
        checkNextDestination();
    }

    //TODO: Handle when elevator is idle and has floors to go to
    public void checkNextDestination()
    {
       if (stops.size() == 0)
        {
            log.info(String.format("setting direction to idle\n"));
            setDirection(IDLE);
        } else if (getDirection() == UP && !stops.stream().anyMatch((floor) -> floor > getLocation()))
        {
            log.info(String.format("setting direction to down\n"));
            setDirection(DOWN);
        } else if (getDirection() == DOWN && !stops.stream().anyMatch((floor) -> floor < getLocation()))
        {
            log.info(String.format("setting direction to up\n"));
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
                if(isIdle())
                    nextActionTime = actionTime;
            }
            else if (stops.contains(currentFloor))
            {
                log.info(String.format("stop\n"));
                nextActionTime = actionTime + MAX_DOOR_OPEN_TIME;
                stop(currentFloor);
            }
            else if (stops.size() > 0)
            {
                log.info(String.format("move\n"));
                checkNextDestination();
                nextActionTime = actionTime + MAX_FLOOR_TIME;
                move();
            }
            else if (isIdle() && (actionTime - nextActionTime) == MAX_IDLE_TIME && currentFloor != 1)
            {
                stops.add(1);
            }
        }
    }
}
