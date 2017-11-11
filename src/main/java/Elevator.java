package main.java;


import main.elevatorgui.gui.ElevatorDisplay;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Elevator implements Moveable
{
    private final Logger log = Logger.getRootLogger();
    public int elevatorId;
    private ArrayList<Person> riders = new ArrayList<>();
    private ArrayList<FloorRequest> stops = new ArrayList<>();
    // Originally, I only had a stops ArrayList but the project requirements asked that I print rider and floor
    // requests separately.
    private ArrayList<Integer> floorRequests = new ArrayList<>(), riderRequests = new ArrayList<>();
    private Moveable moveable;
    private FloorRequest requestedDirection = new FloorRequest(-1, Directions.IDLE);
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


    // Returns a list of floor requests in a nice string format
    public String getFloorRequestsString()
    {
        return floorRequests.stream().map(Object::toString).collect(Collectors.joining(","));

    }

    // Returns a list of rider requests in a nice string format
    public String getRiderRequestsString()
    {
        return riderRequests.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    // Returns a list of riders in a nice string format
    public String getRidersString()
    {
        return riders.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    public HashSet<Integer> getFloorRequests()
    {
        HashSet<Integer> floorReqsSet = new HashSet<>();
        floorReqsSet.addAll(floorRequests);
        return floorReqsSet;
    }

    public HashSet<Integer> getRiderRequests()
    {
        HashSet<Integer> riderReqsSet = new HashSet<>();
        riderReqsSet.addAll(riderRequests);
        return riderReqsSet;
    }

    public int getRequestedDirection()
    {
        return requestedDirection.getDirection();
    }

    // This sets a special variable called requestedDirection. The difference between direction and requestedDirection
    // are the requested direction is typically set when a floor is pressed whereas direction gets set based on the
    // direction of the next stop
    public void setRequestedDirection(FloorRequest requestedDirection)
    {
        this.requestedDirection = requestedDirection;
    }

    public boolean isIdle()
    {
        return moveable.getDirection() == Directions.IDLE;
    }

    public boolean isFull()
    {
        return riders.size() == properties.getMaxCapacity();
    }

    public void addStop(int floor, int direction, String type)
    {
        //TODO: possibly throw an error here
        if (floor <= Building.getInstance().getFloors().size())
        {
            stops.add(new FloorRequest(floor, direction));
            if (type.equals("Rider") && !riderRequests.contains(floor))
                riderRequests.add(floor);
            else if (type.equals("Floor") && !floorRequests.contains(floor))
                floorRequests.add(floor);

            if (stops.size() == 1)
                checkNextDestination();
        }

        // This will happen when a person picks a floor on the way of the elevator's current
        // direction but is opposite of a requests future direction. When this happens, the
        // elevator needs to throw that request back to the elevator controller to choose a new
        // elevator for it
//        HashSet s = new HashSet();
//        s.addAll(stops);
//        List<FloorRequest> rerequests = new ArrayList();
//
//
//        if (riderRequests.size() > 0)
//        {
//            if (getDirection() == Directions.UP)
//            {
//                int maxFloorRequest = Collections.max(riderRequests);
//
//                rerequests = stops.stream().filter(floorReq ->
//                   floorReq.getFloorNumber() <= maxFloorRequest
//                                && floorReq.getDirection() != Directions.UP
//                    ).collect(Collectors.toList());
//            }
//            else if (getDirection() == Directions.DOWN)
//            {
//                int minFloorRequest = Collections.min(riderRequests);
//
//                rerequests = stops.stream().filter(floorReq ->
//                        floorReq.getFloorNumber() >= minFloorRequest
//                                && floorReq.getDirection() != Directions.DOWN
//                ).collect(Collectors.toList());
//            }
//        }
//
//        if (rerequests.size() > 0)
//        {
//            // This removes the floor request that's now going the opposite direction that this elevator
//            // is going in
//            stops.removeAll(rerequests);
//
//            // If for some reason, we have two requests for the same floor (one up, one down), This makes sure
//            // to not remove it from the floor requests string unless we really only had a single request for that
//            // floor
//            ArrayList<Integer> removeFromFloorRequests = new ArrayList<>();
//            for (int request : floorRequests)
//            {
//                if (stops.stream().noneMatch(floorReq-> floorReq.getFloorNumber() == request))
//                    removeFromFloorRequests.add(request);
//            }
//
//            floorRequests.removeAll(removeFromFloorRequests);
//
//            setRequestedDirection(new FloorRequest(floor, direction));
//            Building.getInstance().floorButtonPress(requestedDirection.getFloorNumber(), requestedDirection.getDirection());
//        }
    }

    public void stop(int currentFloorNumber)
    {
        stops.removeIf((floor) -> floor.getFloorNumber() == currentFloorNumber);
        floorRequests.removeIf((floor) -> floor == currentFloorNumber);
        riderRequests.removeIf((floor) -> floor == currentFloorNumber);

        Floor currentFloor = Building.getInstance().getFloor(currentFloorNumber);

        //Checks if the elevator either has riders or the floor has waiting people before opening the doors
        if (riders.size() > 0 || currentFloor.getWaiting().size() > 0)
            openDoors(currentFloorNumber);
    }

    public ArrayList<Person> addRiders(ArrayList<Person> people)
    {
        if (people.size() > 0)
        {
            for (Person person : people)
            {
                // This sets the direction when situations arise where the requested direction is idle (think
                // when it's being reset to the first floor) and sets it to the direction of the next stop. This
                // seems kind of hacky so I'll fix this later
                int direction = requestedDirection.getDirection() != Directions.IDLE ?
                        requestedDirection.getDirection() : getDirection();

                // Adds the rider if the elevator isn't full and the person's destination is on the way
                if (!isFull() && Helpers.onWay(getLocation(), direction, person.getDestination()))
                {
                    log.info(String.format("Elevator %s - Person %s entered [Riders: %s]\n", elevatorId,
                            person.getName(), getRidersString()));

                    double pickupTime = TimeManager.getInstance().getCurrentTime();
                    person.setWaitTime(pickupTime - person.getWaitTime());
                    person.setRideTime(pickupTime);
                    Building.getInstance().getFloor(this.getLocation()).addWaitTime(person.getWaitTime());
                    riders.add(person);
                    addStop(person.getDestination(), direction, "Rider");

                    log.info(String.format("Elevator %s - Rider request made for floor %d.[Floor Requests: %s][Rider Requests: %s]\n",
                            elevatorId, person.getDestination(), getFloorRequestsString(), getRiderRequestsString()));
                }
            }
            // Removes any riders from the floor's getWaiting list if they got on the elevator.
            people.removeAll(riders);
        }

        return people;
    }

    public void openDoors(int currentFloorNumber)
    {
        log.info(String.format("Elevator %s - Opening door...\n", elevatorId));
        doorsOpen = true;
        ElevatorDisplay.getInstance().openDoors(elevatorId);
        Floor currentFloor = Building.getInstance().getFloor(currentFloorNumber);
        Iterator<Person> iterator = riders.iterator();

        //Code below allows riders to get off the elevator
        while (iterator.hasNext())
        {
            Person rider = iterator.next();
            if (rider.getDestination() == currentFloorNumber)
            {
                double totalRideTime = TimeManager.getInstance().getCurrentTime() - rider.getRideTime();
                rider.setRideTime(totalRideTime);
                currentFloor.setOffLoaded(rider);
                iterator.remove();
                log.info(String.format("Elevator %s - Person %s is leaving elevator.\n" +
                                "Floor wait time: %f. Ride time: %f. [Riders: %s]\n", elevatorId, rider.getName(),
                        rider.getWaitTime(), rider.getRideTime(), getRidersString()));

            }
        }

        // This takes into account situations where there are more people waiting on the floor than can fit on the elevator.
        // Add riders will return a list of people who were not able to get on the elevator and this new list is set
        // as the new waiting list of the floor. However, still need to handle that they need to push the button again.
        ArrayList<Person> peopleLeftOnFloor = addRiders(currentFloor.getWaiting());
        currentFloor.setWaiting(peopleLeftOnFloor);
    }

    public void closeDoors()
    {
        log.info(String.format("Elevator %s - Closing doors.\n", elevatorId));
        doorsOpen = false;
        ElevatorDisplay.getInstance().closeDoors(elevatorId);
        checkNextDestination();
    }

    // CheckNextDestination provides the logic to set the elevator's direction
    public void checkNextDestination()
    {
        if (stops.size() == 0)
        {
            log.info(String.format("Elevator %s - setting direction to idle\n", elevatorId));
            setDirection(Directions.IDLE);
        }
        // If direction is Directions.IDLE and we have stops, set to up if the stop is higher than current location. Set to
        // down if below
        else if (getDirection() == Directions.IDLE && stops.size() > 0)
        {
            int direction = (getLocation() - stops.get(0).getFloorNumber()) < 0 ? Directions.UP : Directions.DOWN;
            setDirection(direction);
        }
        //If there are no stops on the way of our current direction...
        else if (!stops.stream().anyMatch((floor) -> Helpers.onWay(getLocation(), getDirection(), floor.getFloorNumber())))
        {
            // if requested direction is the same as our current direction then there's no more floor requests
            // and we're probably resetting so just set the requested direction to idle since ultimately that's what
            // it will be.
            if (getDirection() == requestedDirection.getDirection())
            {
                // TODO: make this make more sense.
                setRequestedDirection(new FloorRequest(1, Directions.IDLE));
            }
            // Flip the elevator direction since there are no stops in our current direction.
            setDirection(getDirection() * -1);
        }
    }

    // DoAction is a method that performs an elevator action for one second.
    public void doAction(long actionTime)
    {
        // Each action lasts a certain amount of time (see the JSON file). For example, doors stay open for x amount of
        // seconds. When an action is executed, nextActionTime is set to the current action's time PLUS however long it takes
        // to perform than action. No actions can be performed until actionTime passes nextActionTime.
        if (actionTime >= nextActionTime)
        {

            int currentFloor = moveable.getLocation();
            if (doorsOpen)
            {
                closeDoors();
                if (isIdle())
                    nextActionTime = actionTime;
            }
            // If we have a stop on the current floor, stop here.
            else if (stops.stream().anyMatch(stop-> stop.getFloorNumber() == currentFloor))
            {
                log.info(String.format("Elevator %s - Arrived at floor %d for request.[Floor Requests: %s][Rider Requests: %s]\n",
                        elevatorId, currentFloor, getFloorRequestsString(), getRiderRequestsString()));
                nextActionTime = actionTime + properties.getMaxOpenTime();
                stop(currentFloor);
            }
            // If we still have stops check the next destination to set our next direction and move towards it
            else if (stops.size() > 0)
            {
                checkNextDestination();
                log.info(String.format("Elevator %s - Moving from %d to %d. [Floor Requests: %s][Rider Requests: %s]\n", elevatorId, getLocation(),
                        moveable.getLocation() + moveable.getDirection(), getFloorRequestsString(), getRiderRequestsString()));

                nextActionTime = actionTime + properties.getMaxFloorTime();
                move();
            }
            // If we're idle, we've hit the max idle time, and we're not already on the first floor, reset back to floor 1
            else if (isIdle() && (actionTime - nextActionTime) == properties.getMaxIdleTime() && currentFloor != 1)
            {
                setRequestedDirection(new FloorRequest(1, Directions.DOWN));
                stops.add(new FloorRequest(1, Directions.DOWN));
            }
            // Hack to get display direction. This code uses an int value for direction because it helps move floors
            // and other cool math stuff.
            ElevatorDisplay.Direction dir = getDirection() > 0 ? ElevatorDisplay.Direction.UP : ElevatorDisplay.Direction.DOWN;
            ElevatorDisplay.getInstance().updateElevator(elevatorId, moveable.getLocation(), riders.size(), dir);
        }
    }
}
