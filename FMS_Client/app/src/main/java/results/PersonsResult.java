package results;
import models.Person;
import java.util.ArrayList;

/**
 * This class contains the data for the return of a persons command
 */
public class PersonsResult
{
    private ArrayList<Person> persons;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
}
