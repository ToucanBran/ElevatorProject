package main.java;

public class Main
{
    public static void main(String[] args)
    {
        Building.getInstance().setupBuilding();
        Building.getInstance().startElevators();
    }
}
