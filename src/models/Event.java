package models;

/** Contains all the data for the Event data table */
public class Event
{
    private int eventID;
    private String personID;
    private String descendant;
    private Float latitude;
    private Float longitude;
    private String country;
    private String city;
    private String type;
    private int year;

    public Event(int eventID, String personID, String descendant, Float latitude, Float longitude,
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

    public String getPersonID() {
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

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("'" + eventID + "',");
        str.append("'" + descendant + "',");
        str.append("'" + personID + "',");
        str.append("'" + latitude + "',");
        str.append("'" + longitude + "',");
        str.append("'" + country + "',");
        str.append("'" + city + "',");
        str.append("'" + type + "',");
        str.append("'" + year + "'");

        return str.toString();
    }
}
