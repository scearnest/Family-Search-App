package results;

/** Contains the message and data for the clear result*/
public class ClearResult
{
    private String message;

    public ClearResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
