package models;

/** Contains all the data for the Token data table */
public class Token {

    private int token;
    private String userID;

    public Token(int token, String userID) {
        this.token = token;
        this.userID = userID;
    }

    public int getToken() {
        return token;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("'" + userID + "',");
        str.append("'" + token + "'");

        return str.toString();
    }
}
