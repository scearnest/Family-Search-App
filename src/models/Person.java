package models;

/** Contains all the data for the Person data table */
public class Person {

    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private char gender;
    private String father;
    private String mother;
    private String spouse;


    public Person(String personID, String descendant, String firstName, String lastName, char gender, String father, String mother, String spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }


    public String getPersonID() {
        return personID;
    }

    public String getDecendant() {
        return descendant;
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

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("'" + personID + "',");
        str.append("'" + descendant + "',");
        str.append("'" + firstName + "',");
        str.append("'" + lastName + "',");
        str.append("'" + gender + "',");
        str.append("'" + father + "',");
        str.append("'" + mother + "',");
        str.append("'" + spouse + "'");

        return str.toString();
    }
}

