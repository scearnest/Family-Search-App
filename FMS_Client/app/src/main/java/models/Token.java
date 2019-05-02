package models;

/** Contains all the data for the Token data table */
public class Token {

    private String token;
    private String username;

    public Token(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null)
            return false;
        if(o instanceof Token)
        {
            Token p = (Token) o;
            if(p.getToken().equals(getToken()) && p.getUsername().equals(getUsername()))
                return true;
        }
        return false;
    }
}
