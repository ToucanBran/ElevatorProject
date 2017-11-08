package tests;


import main.java.Building;
import main.java.Directions;
import main.java.Person;
import main.java.TimeManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class BuildingTests
{
    private final Logger log = Logger.getRootLogger();

    @Test
    public void Test1()
    {
        TimeManager.getInstance().runTime(60,10);
    }

}