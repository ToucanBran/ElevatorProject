package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class Elevator implements Moveable
{
    private int MAX_CAPACITY;
    String elevatorId;

    private ArrayList<Person> riders = new ArrayList<>();
    // TODO: make this some kind of heap data structure that puts
    // the floor closest to the current floor based and in same direction
    private ArrayList<Integer> stops = new ArrayList<>();
    private Moveable moveable;

    public Elevator(String type, int maxCapacity)
    {
        MAX_CAPACITY = maxCapacity;
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

    public void stop()
    {
        if (moveable.getLocation() == stops.peek())
        {
            openDoors(stops.pop());
            closeDoors();
            getNextFloor();
        }
    }

    public List<Person> addRiders(List<Person> people)
    {
        for (Person person : people)
        {
            if (!isFull())
            {
                riders.add(person);
                addStop(person.destination);
                people.remove(person);
            }
        }
        return people;
    }

    //TODO: Implement wait time
    public void openDoors(int currentFloorNumber)
    {
        riders.removeIf((rider) -> rider.destination == currentFloorNumber);

        Floor currentFloor = Building.getInstance().getFloor(currentFloorNumber);

        List<Person> peopleLeftOnFloor = addRiders(currentFloor.getPeople());
        currentFloor.setPeople(peopleLeftOnFloor);
    }

    //TODO: Implement
    public void closeDoors()
    {
    }

    public void checkNextDestination()
    {
        if (stops.size() == 0)
            setDirection(IDLE);
        else if (getLocation() == UP && !stops.stream().anyMatch((floor) -> floor > getLocation()))
        {
            setDirection(DOWN);
        }
        else if (getLocation() == DOWN && !stops.stream().anyMatch((floor) -> floor < getLocation()))
        {
            setDirection(UP);
        }
    }
}
