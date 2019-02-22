package requests;
import models.*;

/** This class contains the data needed to preform the load command */

public class LoadRequest
{
    private User[] user;
    private Person[] persons;
    private Event[] events;

    public User[] getUser() {
        return user;
    }

    public void setUser(User[] user) {
        this.user = user;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
