package models;

/** Contains all the data for the User data table */
public class User {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private char gender;

    public User (String username, String password, String email, String firstName, String lastName, char gender)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    public char getGender() {
        return gender;
    }
}
