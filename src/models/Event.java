package models;

/** Contains all the data for the Event data table */
public class Event
{
    private int eventID;
    private int personID;
    private String descendant;
    private Float latitude;
    private Float longitude;
    private String country;
    private String city;
    private String type;
    private int year;

    public Event(int eventID, int personID, String descendant, Float latitude, Float longitude,
                 String country, String city, String type, int year )
    {
        this.eventID = eventID;
        this.personID = personID;
        this.descendant = descendant;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.type = type;
        this.year = year;
    }

    public int getEventID() {
        return eventID;
    }

    public int getPersonID() {
        return personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }
}
