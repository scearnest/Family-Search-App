package models;

/** Contains all the data for the User data table */
public class User {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public User (String userName, String password, String email, String firstName, String lastName, String gender, String personID)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return userName;
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

    public String getPersonID() {
        return personID;
    }

    public void setpersonID(String personID) {
        this.personID = personID;
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
                p.getPersonID().equals(getPersonID()))
                return true;
        }
        return false;
    }
}
