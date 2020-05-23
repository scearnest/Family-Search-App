package DAOs;


public class DataAccessException extends Exception {

    String message;

    @Override
    public String getMessage() {
        return message;
    }

    DataAccessException(String message)
    {
        this.message = message;
    }

    DataAccessException()
    {
        super();
    }
}
