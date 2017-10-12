package main.java;

import java.util.ArrayList;
import java.util.List;

public class Floor
{
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
}
