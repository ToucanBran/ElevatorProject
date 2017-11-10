package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class RiderStatistics implements Statistics<Double, HashMap<String, List<Double>>>
{
    private HashMap<String, List<Double>> statLog = new HashMap<>();

    @Override
    public void addEntry(Double[] entry)
    {
        //TODO: Fix this hack if you have time
        final int FLOOR = entry[0].intValue(), DESTINATION = entry[1].intValue();
        final double RIDE_TIME = entry[2];
        String key = FLOOR + "-" + DESTINATION;

        if (statLog.containsKey(key))
            statLog.get(key).add(RIDE_TIME);
        else
            statLog.put(key, Arrays.asList(RIDE_TIME));
    }

    @Override
    public HashMap<String, List<Double>> getStatistics()
    {
        // Not sure if this makes sense. I want to return this object but I also don't want
        // someone to accidently modify the statistics. So here I'm just creating a copy of it.
        // This way, every call to getStats returns the same values even if the user/dev modifies their copy.
        HashMap<String, List<Double>> statLogCopy = new HashMap<>();

        statLog.keySet().stream().forEach(key ->
                statLogCopy.put(key, new ArrayList<Double>(statLog.get(key)))
        );

        return statLogCopy;
    }
}
