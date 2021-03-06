package results;
import models.Person;

/** Contains the message and data for the person result*/
public class PersonResult
{
    private String message;
    private Person person;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
