package main.java;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.Timer;
import java.util.function.Function;

public class TimeManager
{
    private final Logger log = Logger.getRootLogger();
    private static TimeManager timeManager = new TimeManager();
    private long currentTime = 0;
    private int peopleInRotation = 0;
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
        runTime(900,5);
    }

    // I have a runnable type param here so users can pass in their own simulation people/floor presses
    public void runTime(int duration, int peoplePerMinute)
    {
        final int DURATION = duration, PEOPLE_PER_MINUTE = peoplePerMinute;
        long totalPeople = (DURATION / 60) * PEOPLE_PER_MINUTE;

        while (currentTime < DURATION)
        {
            requestForElevator(DURATION/totalPeople);
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
        System.out.println(peopleInRotation);
        TimeReport tr = new TimeReport();
        tr.printReport();
    }

    // Fallback method for runTime simulation
    private void requestForElevator(long interval)
    {
        if (currentTime % interval == 0)
        {
            Random rand = new Random();
            int randomFloor = rand.nextInt(Building.getInstance().getFloors().size()) + 1;
            int randomDestination, direction;
            do
            {
                randomDestination = rand.nextInt(Building.getInstance().getFloors().size()) + 1;
            }while(randomDestination == randomFloor);

            // TODO:// HACK - Fix this
            direction = randomDestination < randomFloor ? Directions.DOWN : Directions.UP;
            String directionString = randomDestination < randomFloor ? "DOWN" : "UP";

            Person p = new Person(randomDestination, "P" + ++peopleInRotation);
            Building.getInstance().addToFloor(randomFloor, p);

            log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                    p.getName(), randomFloor, directionString, p.getDestination()));

            log.info(String.format("Person %s presses %s button on floor %d.",
                    p.getName(), directionString, randomFloor));

            Building.getInstance().floorButtonPress(randomFloor, direction);
        }
    }
}
