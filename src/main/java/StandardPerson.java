package main.java;

/**
 * Standard person is the most basic kind of person. The only real difference between this and the parent
 * is that the toString returns a name.
 */
public class StandardPerson extends Person
{
    public StandardPerson(int destination, String name, int start) throws IllegalArgumentException
    {
        super(destination, name, start);
    }

    public String toString()
    {
        return getName();
    }

}
