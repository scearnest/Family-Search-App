package requests;

/** Contains all the data needed to request a person*/
public class PersonRequest
{
    private String personID;
    private String authToken;

    public String getAuthToke() {
        return authToken;
    }

    public void setAuthToke(String authToke) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
