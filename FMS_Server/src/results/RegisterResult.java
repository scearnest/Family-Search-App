package results;

/** Contains the message and data for the register result*/
public class RegisterResult
{
    private String authToken = null;
    private String userName = null;
    private String personID = null;
    private String message = null;

    public String getToken() {
        return authToken;
    }

    public void setToken(String token) {
        this.authToken = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getErrorMessage() {
        return message;
    }

    public void setErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }
}
