package requests;
import models.*;

/** This class contains the data needed to preform the load command */

public class LoadRequest
{
    private User[] users;
    private Person[] persons;
    private Event[] events;

    public User[] getUser() {
        return users;
    }

    public void setUser(User[] users) {
        this.users = users;
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
