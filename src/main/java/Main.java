package main.java;

/**
 * Main method. This will run the entire program. Based on inputs, it might take time to
 * create a person since it's based on a percentage so you might have to wait.
 *
 * Make sure building.json is filled out with all the fields before proceeding.
 */
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            TimeManager.getInstance().runTime();
        } catch(BuildSetupException b)
        {
            System.out.println(b.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }
}
