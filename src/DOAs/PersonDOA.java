package DOAs;
import models.Person;

/**
 * This class handles all the interactions with the persons table in the database
 */
public class PersonDOA
{
    Person person;

    public PersonDOA()
    {
    }

    /**
     * This function inserts the Person into the database
     * @param person object containing Person data
     * @return true of false for the success of operation
     */
    public boolean insert(Person person)
    {
        boolean success = false;
        return success;
    }

    /**
     * This function deletes the requested Person from the database
     * @param person object containing Person data
     * @return true of false for the success of operation
     */
    public boolean delete(Person person)
    {
        boolean success = false;
        return success;
    }


    /**
     * This function finds the Person based on their ID
     * @param PersonID ID for requested Person
     * @return object containing the Persons data
     */
    public Person find(String PersonID)
    {
        return person;
    }
}
