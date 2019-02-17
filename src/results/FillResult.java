package results;

/** Contains the message and data for the fill result*/
public class FillResult
{
    private String message;
    private boolean success;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
