package JsonData;

import java.util.Random;

public class jsonLocations
{
    Location[] data = null;

    public class Location
    {
        public String country;
        public String city;
        public float latitude;
        public float longitude;

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public float getLatitude() {
            return latitude;
        }

        public float getLongitude() {
            return longitude;
        }
    }

    public Location getRandom()
    {
        Random r = new Random();
        int num = r.nextInt(data.length);
        return data[num];
    }

}
