package models;

/** Contains all the data for the Event data table */
public class Event
{
    private String eventID;
    private String personID;
    private String descendant;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public Event(String eventID, String descendant, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year )
    {
        this.eventID = eventID;
        this.personID = personID;
        this.descendant = descendant;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null)
            return false;
        if(o instanceof Event)
        {
            Event p = (Event) o;
            if(p.getEventID().equals(getEventID()) &&
                    p.getPersonID().equals((getPersonID())) &&
                    p.getDescendant().equals(getDescendant()) &&
                    p.getLatitude() == (getLatitude()) &&
                    p.getLongitude() == (getLongitude()) &&
                    p.getCountry().equals(getCountry()) &&
                    p.getCity().equals(getCity()) &&
                    p.getEventType().equals(getEventType()) &&
                    p.getYear() == (getYear()))
                return true;
        }
        return false;
    }



}
