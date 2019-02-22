package models;

/** Contains all the data for the User data table */
public class User {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String userID;

    public User (String username, String password, String email, String firstName, String lastName, String gender, String userID)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getuserID() {
        return userID;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString()
    {

        StringBuilder str = new StringBuilder();
        str.append("'" + username + "',");
        str.append("'" + password + "',");
        str.append("'" + email + "',");
        str.append("'" + firstName + "',");
        str.append("'" + lastName + "',");
        str.append("'" + gender + "',");
        str.append("'" + username + "',");
        str.append("'" + userID + "'");

        return str.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null)
            return false;
        if(o instanceof User)
        {
            User p = (User) o;
            if(p.getUsername().equals(getUsername()) &&
                p.getPassword().equals(getPassword()) &&
                p.getEmail().equals(getEmail()) &&
                p.getFirstName().equals(getFirstName()) &&
                p.getLastName().equals(getLastName()) &&
                p.getGender().equals(getGender()) &&
                p.getuserID().equals(getuserID()))
                return true;
        }
        return false;
    }
}
