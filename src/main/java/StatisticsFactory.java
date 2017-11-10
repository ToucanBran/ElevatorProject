package main.java;

public class StatisticsFactory
{
    private StatisticsFactory(){};
    public static Statistics createStatistics(String type)
    {
        if (type.equals("Ride"))
            return new RiderStatistics();
        else if (type.equals("Person"))
            return new PersonStatistics();
        else return null;
    }
}
