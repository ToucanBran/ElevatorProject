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
        TimeManager.getInstance().runTime(() ->
                {

                    if (TimeManager.getInstance().getCurrentTime() == 0)
                    {

                        Person p = new Person(20, "P1");

                        Building.getInstance().addToFloor(1, p);
                        log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                                p.getName(), 1, "up", p.getDestination()));

                        Building.getInstance().floorButtonPress(1, Directions.UP);
                        log.info(String.format("Person %s presses %s button on floor %d.",
                                p.getName(), "up", 1));
                    }
                }
        );
    }

    @Test
    public void Test3()
    {
        TimeManager.getInstance().runTime(() ->
                {

                    if (TimeManager.getInstance().getCurrentTime() == 0)
                    {

                        Person p = new Person(20, "P1");

                        Building.getInstance().addToFloor(1, p);
                        log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                                p.getName(), 1, "up", p.getDestination()));

                        Building.getInstance().floorButtonPress(1, Directions.UP);
                        log.info(String.format("Person %s presses %s button on floor %d.",
                                p.getName(), "up", 1));
                    }

                    if (TimeManager.getInstance().getCurrentTime() == 9)
                    {
                        Person p = new Person(10, "P2");

                        Building.getInstance().addToFloor(1, p);
                        log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                                p.getName(), 1, "up", p.getDestination()));

                        Building.getInstance().floorButtonPress(1, Directions.UP);
                        log.info(String.format("Person %s presses %s button on floor %d.",
                                p.getName(), "up", 1));
                    }

                    if (TimeManager.getInstance().getCurrentTime() == 2)
                    {
                        Person p = new Person(10, "P3");

                        Building.getInstance().addToFloor(3, p);
                        log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                                p.getName(), 3, "up", p.getDestination()));

                        Building.getInstance().floorButtonPress(3, Directions.UP);
                        log.info(String.format("Person %s presses %s button on floor %d.",
                                p.getName(), "up", 3));
                    }
                }
        );
    }

    @Test
    public void Test4()
    {
        TimeManager.getInstance().runTime(() ->
            {

                if (TimeManager.getInstance().getCurrentTime() == 0)
                {

                    Person p = new Person(20, "P1");

                    Building.getInstance().addToFloor(1, p);
                    log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                            p.getName(), 1, "up", p.getDestination()));

                    Building.getInstance().floorButtonPress(1, Directions.UP);
                    log.info(String.format("Person %s presses %s button on floor %d.",
                            p.getName(), "up", 1));
                }

                if (TimeManager.getInstance().getCurrentTime() == 6)
                {
                    Person p = new Person(20, "P2");

                    Building.getInstance().addToFloor(1, p);
                    log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                            p.getName(), 1, "up", p.getDestination()));

                    Building.getInstance().floorButtonPress(1, Directions.UP);
                    log.info(String.format("Person %s presses %s button on floor %d.",
                            p.getName(), "up", 1));
                }

                if (TimeManager.getInstance().getCurrentTime() == 12)
                {
                    Person p = new Person(20, "P3");

                    Building.getInstance().addToFloor(1, p);
                    log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                            p.getName(), 1, "up", p.getDestination()));

                    Building.getInstance().floorButtonPress(1, Directions.UP);
                    log.info(String.format("Person %s presses %s button on floor %d.",
                            p.getName(), "up", 1));
                }

                if (TimeManager.getInstance().getCurrentTime() == 18)
                {
                    Person p = new Person(20, "P4");

                    Building.getInstance().addToFloor(1, p);
                    log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                            p.getName(), 1, "up", p.getDestination()));

                    Building.getInstance().floorButtonPress(1, Directions.UP);
                    log.info(String.format("Person %s presses %s button on floor %d.",
                            p.getName(), "up", 1));
                }

                if (TimeManager.getInstance().getCurrentTime() == 24)
                {
                    Person p = new Person(10, "P5");

                    Building.getInstance().addToFloor(1, p);
                    log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                            p.getName(), 1, "up", p.getDestination()));

                    Building.getInstance().floorButtonPress(1, Directions.UP);
                    log.info(String.format("Person %s presses %s button on floor %d.",
                            p.getName(), "up", 1));
                }
            }
        );
    }
}