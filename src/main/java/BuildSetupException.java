package main.java;

/**
 * Custom Exception class that's thrown when there's a build error constructing the Building
 * object
 *
 * @author Brandon Gomez
 */
public class BuildSetupException extends Exception
{
    public BuildSetupException(String message)
    {
        super(message);
    }
}
