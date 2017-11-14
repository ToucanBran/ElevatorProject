package main.java;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.Timer;
import java.util.function.Function;

/**
 * TimeManager is responsible for managing the simulation time. It moves time not by a real second but by use
 * of a counter. For every integer increment, actions are performed across all elevators. This class also is responsible
 * for generating random Person objects and requesting elevators.
 */
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

    public void runTime() throws BuildSetupException
    {
        int duration = Helpers.getBuildingJson("duration").getAsInt();
        int peoplePerMinute = Helpers.getBuildingJson("peoplePerMinute").getAsInt();
        runTime(duration,peoplePerMinute);
    }

    // I have a runnable type param here so users can pass in their own simulation people/floor presses
    public void runTime(int duration, int peoplePerMinute)
    {
        final int DURATION = duration;
        final double PEOPLE_PER_MINUTE = peoplePerMinute;
        System.out.println("Elevator program loading up...");
        while (currentTime < DURATION)
        {
            requestForElevator(PEOPLE_PER_MINUTE / 60);
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

    private void requestForElevator(double chance)
    {
        Random rand = new Random();

        if (rand.nextFloat() <= chance)
        {
            int randomFloor = rand.nextInt(Building.getInstance().getFloors().size()) + 1;
            int randomDestination, direction;
            try
            {
                String type = Helpers.getBuildingJson("typeOfPerson").getAsString();
                do
                {
                    randomDestination = rand.nextInt(Building.getInstance().getFloors().size()) + 1;
                } while (randomDestination == randomFloor);

                // TODO:// HACK - Fix this
                direction = randomDestination < randomFloor ? Directions.DOWN : Directions.UP;
                String directionString = randomDestination < randomFloor ? "DOWN" : "UP";


                Person p = PersonFactory.createPerson(randomDestination, String.format("P%d", ++peopleInRotation), randomFloor, type);
                p.beginWait();
                Building.getInstance().addToFloor(randomFloor, p);

                log.info(String.format("Person %s created on Floor %d, wants to go %s to floor %d",
                        p.getName(), randomFloor, directionString, p.getDestination()));

                log.info(String.format("Person %s presses %s button on floor %d.",
                        p.getName(), directionString, randomFloor));

                Building.getInstance().floorButtonPress(randomFloor, direction);
            } catch (BuildSetupException e)
            {
                System.out.printf("Missing type of person field in Building.json. Please enter in this field and try again.");
                System.exit(1);
            } catch (IllegalArgumentException e)
            {
                // This will only happen if for some reason the destination is above the max floor or below the first floor.
                // Shouldn't ever happen but just in case it does.
                peopleInRotation--;
            }
        }
    }
}
