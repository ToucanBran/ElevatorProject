package main.java;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Floor
{
    private final Logger log = Logger.getRootLogger();
    private ArrayList<Double> waitTimes = new ArrayList<>();
    private ArrayList<Person> waiting = new ArrayList<>();
    private ArrayList<Person> offLoaded = new ArrayList<>();

    public ArrayList<Person> getWaiting()
    {
        return waiting;
    }

    public void setWaiting(ArrayList<Person> people)
    {
        this.waiting = people;
    }

    public void setWaiting(Person person)
    {
        waiting.add(person);
    }

    public void setOffLoaded(Person person)
    {
        offLoaded.add(person);
    }

    public List<Double> getOffLoadedRideTimes()
    {
       return offLoaded.stream().map(Person::getRideTime).collect(Collectors.toList());
    }

    public void addWaitTime(double time)
    {
        waitTimes.add(time);
    }
    public ArrayList<Double> getWaitTimes()
    {
        return waitTimes;
    }
}
