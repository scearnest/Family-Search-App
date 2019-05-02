package results;
import models.Event;
import java.util.ArrayList;

/**
 * Contains the data from the result of the events command
 */
public class EventsResult
{
    private ArrayList<Event> event;
    private String message;

    public ArrayList<Event> getEvents() {
        return event;
    }

    public void setEvents(ArrayList<Event> event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
