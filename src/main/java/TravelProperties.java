package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * TravelProperties is similar to PersonProperties in that it's primary use is to be entered into the building
 * log and have statistics built from it. A TravelProperties object will hold a start and end floor and a list of
 * times that it took one person to get from the start to the destination.
 *
 * @author Brandon Gomez
 */
public class TravelProperties
{
    private int startFloor, destination;
    private List<Double> rideTimes = new ArrayList<>();

    public TravelProperties(TravelProperties tp)
    {
        this.startFloor = tp.startFloor;
        this.destination = tp.destination;
        this.rideTimes = tp.getRideTimes();
    }

    public TravelProperties(int startFloor, int destination, double rideTime)
    {
        this.startFloor = startFloor;
        this.destination = destination;
        addRideTime(rideTimes);
    }

    public int getStartFloor()
    {
        return startFloor;
    }

    public void setStartFloor(int startFloor)
    {
        this.startFloor = startFloor;
    }

    public int getDestination()
    {
        return destination;
    }

    public void setDestination(int destination)
    {
        this.destination = destination;
    }

    public List<Double> getRideTimes()
    {
        ArrayList<Double> copy = new ArrayList<>();
        copy.addAll(rideTimes);
        return copy;
    }

    public void addRideTime(double rideTime)
    {
       rideTimes.add(rideTime);
    }

    public void addRideTime(List<Double> rideTimeList)
    {
        rideTimes.addAll(rideTimeList);
    }
}
