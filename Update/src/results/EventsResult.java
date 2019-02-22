package results;
import models.Event;

/**
 * Contains the data from the result of the events command
 */
public class EventsResult
{
    private Event[] event;
    private String message;
    private boolean success;

    public Event[] getEvent() {
        return event;
    }

    public void setEvent(Event[] event) {
        this.event = event;
    }

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
}
