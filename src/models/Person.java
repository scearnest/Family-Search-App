package models;

/** Contains all the data for the Person data table */
public class Person {

    private int personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private char gender;
    private int father;
    private int mother;
    private int spouse;


    public Person(int personID, String descendant, String firstName, String lastName, char gender, int father, int mother, int spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }


    public int getPersonID() {
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

    public int getFather() {
        return father;
    }

    public int getMother() {
        return mother;
    }

    public int getSpouse() {
        return spouse;
    }
}

