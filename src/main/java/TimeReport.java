package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeReport implements Report
{
    private HashMap<Integer, ArrayList<Double>> floorWaitTimes = new HashMap<>();

    public TimeReport()
    {
        Building.getInstance().getFloors().entrySet().stream().forEach((floorEntry) ->
                floorWaitTimes.put(floorEntry.getKey(), floorEntry.getValue().getWaitTimes()));
    }

    private String waitTimesByFloor()
    {
        StringBuilder sb = new StringBuilder();
        String format = "%1$-15s%2$-20s%3$-20s%4$-20s%n";
        sb.append(String.format(format, "Start Floor", "Average Wait Time", "Min Wait Time", "Max Wait Time"));
        String timeFormat = "Floor %1$-10s%2$-20s%3$-20s%4$-20s%n";
        floorWaitTimes.entrySet().stream().forEach(floor ->
        {
            //TODO:Possibly combine avg min max
            sb.append(String.format(timeFormat,
                    floor.getKey(),
                    String.format("%.2f Seconds", calcAverage(floor.getValue())),
                    String.format("%.2f Seconds", calcMin(floor.getValue())),
                    String.format("%.2f Seconds", calcMax(floor.getValue()))
            ));
        });

        return sb.toString();
    }

//    private String averageRideTimeFloorToFloorByPerson()
//    {
//
//    }
//
//    private String minRideTimeFloorToFloorByPerson()
//    {
//
//    }
//
//    private String maxRideTimeFloorToFloorByPerson()
//    {
//
//    }

    //    private String totalTimesByPerson()
//    {
//
//    }
    @Override
    public void printReport()
    {
        System.out.println(waitTimesByFloor());
    }

    private double calcAverage(ArrayList<Double> times)
    {
        if (times.size() == 0)
            return 0;
        else
        {
            double sum = times.stream().mapToDouble(d -> d.doubleValue()).sum();
            return sum / times.size();
        }
    }

    private double calcMin(ArrayList<Double> times)
    {
        if (times.size() == 0)
            return 0;
        else
        {
            return times.stream().min(Double::compare).get();
        }
    }

    private double calcMax(ArrayList<Double> times)
    {
        if (times.size() == 0)
            return 0;
        else
        {
            return times.stream().max(Double::compare).get();
        }
    }
}
