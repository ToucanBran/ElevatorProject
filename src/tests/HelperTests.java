package tests;


import main.java.*;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelperTests
{
    @Test
    public void OnWayHelperTest()
    {
        int currentFloor = 13, destination = 17;
        assertTrue(Helpers.onWay(currentFloor, Directions.UP, destination));
        assertFalse(Helpers.onWay(currentFloor, Directions.DOWN, destination));
    }
}