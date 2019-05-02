package results;
import models.Event;

/**
 * Contains the data result for the event command
 */
public class EventResult
{
    private Event event;
    private String message;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
