package main.java;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
/**
 * Abstract floor class holds base information about floors. All floors should have the following
 * information and methods implemented in order to be considered a floor and work with this program.
 *
 * @author Brandon Gomez
 */
public abstract class Floor
{
    private final Logger log = Logger.getRootLogger();
    private ArrayList<Double> waitTimes = new ArrayList<>();
    private ArrayList<Person> waiting = new ArrayList<>();
    private ArrayList<Person> offLoaded = new ArrayList<>();

    public ArrayList<Person> getWaiting() {return waiting;};

    public abstract void setWaiting(ArrayList<Person> people);

    public abstract void setWaiting(Person person);

    public abstract void setOffLoaded(Person person);

    public void addWaitTime(double time) {
        waitTimes.add(time);
    };
    public ArrayList<Double> getWaitTimes()
    {
        return waitTimes;
    }
}
