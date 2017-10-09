package main.java;

public class Main
{
    public static void main(String[] args)
    {
        Elevator d = new Elevator(null);
        d.move(1);
        System.out.printf("Current Floor: %d\n", d.getLocation());
        d.move(1);
        System.out.printf("Current Floor: %d\n", d.getLocation());
        d.move(1);
        System.out.printf("Current Floor: %d\n", d.getLocation());
        d.move(1);
        System.out.printf("Current Floor: %d\n", d.getLocation());
        d.move(-1);
        System.out.printf("Current Floor: %d\n", d.getLocation());
        d.move(-1);
        System.out.printf("Current Floor: %d\n", d.getLocation());
        d.move(-1);
        System.out.printf("Current Floor: %d\n", d.getLocation());
    }
}
