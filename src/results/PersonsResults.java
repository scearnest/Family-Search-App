package results;
import models.Person;

/**
 * This class contains the data for the return of a persons command
 */
public class PersonsResults
{
    private Person[] persons;
    private String message;
    private boolean success;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }
}
