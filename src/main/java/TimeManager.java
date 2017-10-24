package main.java;

import org.apache.log4j.Logger;

import java.util.function.Function;

public class TimeManager
{
    private final Logger log = Logger.getRootLogger();
    final int DOWN = -1, IDLE = 0, UP = 1;
    private static TimeManager timeManager = new TimeManager();
    long currentTime = 0;

    private TimeManager()
    {
    }


    public static TimeManager getInstance()
    {
        return timeManager;
    }

    public long getCurrentTime()
    {
        return currentTime;
    }

    public void runTime()
    {
        runTime(()->requestForElevator());
    }

    // I have a runnable type param here so users can pass in their own simulation people/floor presses
    public void runTime(Runnable f)
    {
        long duration = 120;

        while (currentTime <= duration)
        {
            f.run();
            for (Elevator e : Building.getInstance().getElevators())
            {
                e.doAction(currentTime);

            }
            currentTime += 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    // Fallback method for runTime simulation
    private void requestForElevator()
    {
        if (currentTime == 0)
        {

            Person p = new Person(20, "P1");

            Building.getInstance().addToFloor(1, p);
            log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                    p.getName(), 1, "up", p.getDestination()));

            Building.getInstance().floorButtonPress(1, UP);
            log.info(String.format("Person %s presses %s button on floor %d.",
                    p.getName(), "up", 1));
        }

        if (currentTime == 1)
        {
            Person p = new Person(10, "P3");

            Building.getInstance().addToFloor(2, p);
            log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                    p.getName(), 2, "up", p.getDestination()));

            Building.getInstance().floorButtonPress(2, UP);
            log.info(String.format("Person %s presses %s button on floor %d.",
                    p.getName(), "up", 1));
        }

        if (currentTime == 2)
        {
            Person p = new Person(10, "P4");

            Building.getInstance().addToFloor(3, p);
            log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                    p.getName(), 3, "up", p.getDestination()));

            Building.getInstance().floorButtonPress(3, UP);
            log.info(String.format("Person %s presses %s button on floor %d.",
                    p.getName(), "up", 3));
        }

        if (currentTime == 9)
        {
            Person p = new Person(10, "P2");

            Building.getInstance().addToFloor(1, p);
            log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                    p.getName(), 1, "up", p.getDestination()));

            Building.getInstance().floorButtonPress(1, UP);
            log.info(String.format("Person %s presses %s button on floor %d.",
                    p.getName(), "up", 1));
        }

    }
}
