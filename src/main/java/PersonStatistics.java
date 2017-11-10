package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PersonStatistics implements Statistics<PersonProperties, HashMap<String, PersonProperties>>
{
    private HashMap<String, PersonProperties> statLog = new HashMap<>();

    @Override
    public void addEntry(PersonProperties[] entry)
    {
        PersonProperties p = entry[0];
        statLog.put(p.getId(), p);
    }

    @Override
    public HashMap<String, PersonProperties> getStatistics()
    {
        // Not sure if this makes sense. I want to return this object but I also don't want
        // someone to accidently modify the statistics. So here I'm just creating a copy of it.
        // This way, every call to getStats returns the same values even if the user/dev modifies their copy.
        HashMap<String, PersonProperties> statLogCopy = new HashMap<>();

        statLog.keySet().stream().forEach(key ->
                statLogCopy.put(key, new PersonProperties(
                                statLog.get(key).getId(),
                                statLog.get(key).getDestination(),
                                statLog.get(key).getStart(),
                                statLog.get(key).getWaitTime(),
                                statLog.get(key).getRideTime()))
        );

        return statLogCopy;
    }
}
