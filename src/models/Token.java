package models;

/** Contains all the data for the Token data table */
public class Token {

    private int token;
    private int userID;

    public Token(int token, int userID) {
        this.token = token;
        this.userID = userID;
    }

    public int getToken() {
        return token;
    }

    public int getUserID() {
        return userID;
    }

}
