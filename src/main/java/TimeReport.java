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

    private String getAllTimesByPerson()
    {
        int totalPeople = Building.getInstance().getPersonStats().size();
        StringBuilder sb = new StringBuilder();
        String format = "%1$-15s%2$-20s%3$-20s%4$-20s%5$-20s%6$-20s%n";
        sb.append(String.format(format, "Person", "Start Floor", "End Floor", "Wait Time", "Ride Time", "Total Time"));
        String entryFormat = "%1$-15s%2$-20d%3$-20d%4$-20.2f%5$-20.2f%6$-20.2f%n";
        Building.getInstance().getPersonStats().entrySet().forEach(entry ->
        {
            PersonProperties p = entry.getValue();
            sb.append(String.format(entryFormat, p.getId(), p.getStart(), p.getDestination(), p.getWaitTime(),
                    p.getRideTime(), p.getTotalTime()));
        });
        return sb.toString();
    }
    private String buildRideTimeReport(String type)
    {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s","Floor"));
        int totalFloors = Building.getInstance().getFloors().size();

        for (int i = 1; i <= totalFloors; i++)
        {
            sb.append(String.format("%-10d", i));
        }
        sb.append("\n");
        double[][] averageTimes = new double[totalFloors][totalFloors];
        Building.getInstance().getRiderStats().entrySet().forEach(entry ->
                {
                    String[] startEndFloors = entry.getKey().split("-");
                    int startFloor = Integer.parseInt(startEndFloors[0]) - 1;
                    int endFloor = Integer.parseInt(startEndFloors[1]) - 1;
                    List<Double> times = entry.getValue();

                    double time = 0;
                    if (type.equalsIgnoreCase("average"))
                        time = calcAverage(times);
                    else if (type.equalsIgnoreCase("max"))
                        time = calcMax(times);
                    else if (type.equalsIgnoreCase("min"))
                        time = calcMin(times);

                    averageTimes[startFloor][endFloor] = time;
                }
        );

        for (int i = 0; i < averageTimes.length; i++)
        {
            String floorLine = String.format("%-10d", i + 1);
            for (int j = 0; j < averageTimes[i].length;j++)
            {
                floorLine += String.format("%-10.2f", averageTimes[i][j]);
            }
            sb.append(floorLine + "\n");
        }

        return sb.toString();
    }

    @Override
    public void printReport()
    {

        System.out.println(waitTimesByFloor());
        System.out.println(buildRideTimeReport("average"));
        System.out.println(buildRideTimeReport("max"));
        System.out.println(buildRideTimeReport("min"));
        System.out.println(getAllTimesByPerson());

    }

    private double calcAverage(List<Double> times)
    {
        if (times.size() == 0)
            return 0;
        else
        {
            double sum = times.stream().mapToDouble(d -> d.doubleValue()).sum();
            return sum / times.size();
        }
    }

    private double calcMin(List<Double> times)
    {
        if (times.size() == 0)
            return 0;
        else
        {
            return times.stream().min(Double::compare).get();
        }
    }

    private double calcMax(List<Double> times)
    {
        if (times.size() == 0)
            return 0;
        else
        {
            return times.stream().max(Double::compare).get();
        }
    }


}
