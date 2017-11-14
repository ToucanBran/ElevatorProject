package main.java;

import java.util.HashMap;

/**
BuildingLog

Purpose of this class is to keep track of person statistics and ride statistics. These
logs are used for building reports for the building.
 @author Brandon Gomez
 */
public class BuildingLog
{
    private HashMap<String, PersonProperties> personLog = new HashMap<>();
    private HashMap<String, TravelProperties> riderLog = new HashMap<>();

    public HashMap getStatistics(String type)
    {
        if (type.equalsIgnoreCase("person")){

            // send back copy of log instead of reference to original
            HashMap<String, PersonProperties> copy = new HashMap<>();
            for (String key : personLog.keySet())
            {
                copy.put(key, new PersonProperties(personLog.get(key)));
            }
            return personLog;
        }
        else if(type.equalsIgnoreCase("rider"))
        {
            // send back copy of log instead of reference to original
            HashMap<String, TravelProperties> copy = new HashMap<>();
            for (String key : riderLog.keySet())
            {
                copy.put(key, new TravelProperties(riderLog.get(key)));
            }
            return riderLog;
        }

        return null;
    }

    public void addEntry(PersonProperties entry)
    {
        personLog.put(entry.getId(), entry);
    }

    public void addEntry(TravelProperties entry)
    {
        // This is done to map all the ride times from one floor to another
        String key = entry.getStartFloor() + "-" + entry.getDestination();

        if (riderLog.containsKey(key))
        {
            riderLog.get(key).addRideTime(entry.getRideTimes());
        }
        else
        {
            riderLog.put(key, entry);
        }
    }
}
