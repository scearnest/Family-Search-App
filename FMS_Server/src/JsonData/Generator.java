package JsonData;
import DAOs.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import models.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.UUID;

public class Generator
{
    private jsonNames maleNames = null;
    private jsonNames femaleNames = null;
    private jsonNames sNames = null;
    private jsonLocations locations = null;

    private int genMax = 0;
    private int gen = 0;
    private int numUserAdded = 0;
    private int numEventAdded = 0;

    private Database db = new Database();
    private User rootUser = null;

    private String message;

    private int BASE_BIRTH_YEAR = 1990;
    private int BASE_MARRIAGE_YEAR = 2020;
    private int BASE_BAPTISM_YEAR = 1998;

    public Generator(int maxGen, User rootUser)
    {
        this.genMax = maxGen;
        this.rootUser = rootUser;
        generateNames();
        generateLocations();
    }

    public String getMessage()
    {
        message = "Successfully added " + numUserAdded + " persons and " + numEventAdded + " events to the database";
        return message;
    }

    //Recursively fills the persons ancestors, adds events, and then adds the person to the database
    public String fillGenerations(Person person)
    {
        if(gen == genMax)
        {
            person.setPersonID(null);
            --gen;
            return null;
        }

        String spouseID = UUID.randomUUID().toString();
        jsonLocations.Location marriageEventLocation = locations.getRandom();

        Person father = generateFather(person, spouseID, marriageEventLocation);
        person.setFather(father.getPersonID());


        Person mother = generateMother(person, father.getLastName(), spouseID, father.getPersonID(), marriageEventLocation);
        person.setMother(mother.getPersonID());


        addPersonToDatabase(person);
        ++numUserAdded;
        if(gen == 0)
        {
            String birthEventID = (UUID.randomUUID().toString());
            String baptismEventID = (UUID.randomUUID().toString());
            jsonLocations.Location birthEventLocation = locations.getRandom();
            Event birth = new Event(birthEventID, rootUser.getUsername(), person.getPersonID(), birthEventLocation.getLatitude(),
                    birthEventLocation.getLongitude(), birthEventLocation.getCountry(), birthEventLocation.getCity(),
                    "Birth", (BASE_BIRTH_YEAR));
            addEventToDatabase(birth);
        }

        --gen;
        return person.getPersonID();
    }

    private Person generateFather(Person child, String spouse, jsonLocations.Location marriageEventLocation)
    {
        ++gen;

        String personID = UUID.randomUUID().toString();

        Person father = new Person();
        father.setPersonID(personID);
        father.setDescendant(rootUser.getUsername());
        father.setFirstName(maleNames.getRandom());
        father.setLastName(sNames.getRandom());
        father.setGender("M");
        father.setSpouse(spouse);

        String birthEventID = (UUID.randomUUID().toString());
        String baptismEventID = (UUID.randomUUID().toString());

        jsonLocations.Location birthEventLocation = locations.getRandom();
        jsonLocations.Location baptismEventLocation = locations.getRandom();

        String marriageEventID = (UUID.randomUUID().toString());

        Event birth = new Event(birthEventID, rootUser.getUsername(), personID, birthEventLocation.getLatitude(),
                    birthEventLocation.getLongitude(), birthEventLocation.getCountry(), birthEventLocation.getCity(),
                "Birth", (BASE_BIRTH_YEAR - (30 * gen)));
        Event marriage = new Event(marriageEventID, rootUser.getUsername(), personID, marriageEventLocation.getLatitude(),
                    marriageEventLocation.getLongitude(), marriageEventLocation.getCountry(), marriageEventLocation.getCity(),
                    "Marriage", (BASE_MARRIAGE_YEAR - (40 * gen)));
        Event baptism = new Event(baptismEventID, rootUser.getUsername(), personID, baptismEventLocation.getLatitude(),
                    baptismEventLocation.getLongitude(), baptismEventLocation.getCountry(), baptismEventLocation.getCity(),
                    "Baptism", (BASE_BAPTISM_YEAR - (30 * gen)));

        if(gen != genMax)
        {
            addEventToDatabase(birth);
            addEventToDatabase(marriage);
            addEventToDatabase(baptism);
        }

        father.setFather(fillGenerations(father));
        return father;
    }

    private Person generateMother(Person child, String lastname, String personID, String spouseID,
                                  jsonLocations.Location marriageEventLocation)
    {
        ++gen;

        Person mother = new Person();
        mother.setPersonID(personID);
        mother.setDescendant(rootUser.getUsername());
        mother.setFirstName(femaleNames.getRandom());
        mother.setLastName(lastname);
        mother.setGender("F");
        mother.setSpouse(spouseID);

        String birthEventID = (UUID.randomUUID().toString());
        String baptismEventID = (UUID.randomUUID().toString());

        jsonLocations.Location birthEventLocation = locations.getRandom();
        jsonLocations.Location baptismEventLocation = locations.getRandom();

        String marriageEventID = (UUID.randomUUID().toString());

        Event birth = new Event(birthEventID, rootUser.getUsername(), personID, birthEventLocation.getLatitude(),
                birthEventLocation.getLongitude(), birthEventLocation.getCountry(), birthEventLocation.getCity(),
                "Birth", (BASE_BIRTH_YEAR - (30 * gen)));
        Event marriage = new Event(marriageEventID, rootUser.getUsername(), personID, marriageEventLocation.getLatitude(),
                marriageEventLocation.getLongitude(), marriageEventLocation.getCountry(), marriageEventLocation.getCity(),
                "Marriage", (BASE_MARRIAGE_YEAR - (40 * gen)));
        Event baptism = new Event(baptismEventID, rootUser.getUsername(), personID, baptismEventLocation.getLatitude(),
                baptismEventLocation.getLongitude(), baptismEventLocation.getCountry(), baptismEventLocation.getCity(),
                "Baptism", (BASE_BAPTISM_YEAR - (30 * gen)));

        if(gen != genMax)
        {
            addEventToDatabase(birth);
            addEventToDatabase(marriage);
            addEventToDatabase(baptism);
        }

        mother.setMother(fillGenerations(mother));
        return mother;
    }

    private void addPersonToDatabase(Person person)
    {
        boolean success = false;

        try
        {
            PersonDAO dao = new PersonDAO(db.openConnection());
            dao.insert(person);
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        finally
        {
            try
            {
                db.closeConnection(success);
            }
            catch (DataAccessException g){}
        }
    }

    private void addEventToDatabase(Event event)
    {
        boolean success = false;
        try
        {
            EventDAO dao = new EventDAO(db.openConnection());
            dao.insert(event);
            success = true;
            ++numEventAdded;
        }
        catch (DataAccessException e)
        {
            message = "Could not add event";
            success = false;
        }
        finally
        {
            try
            {
                db.closeConnection(success);
            }
            catch (DataAccessException g){}
        }
    }

    private void generateNames()
    {
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        try
        {
            jsonReader = new JsonReader(new FileReader("JsonData/mnames.json"));
        }
        catch (FileNotFoundException e)
        {
            message = "m name file not found";
        }

        maleNames = gson.fromJson(jsonReader, jsonNames.class);

        try
        {
            jsonReader = new JsonReader(new FileReader("JsonData/fnames.json"));
        }
        catch (FileNotFoundException e)
        {
            message = "f name file not found";
        }

        femaleNames = gson.fromJson(jsonReader, jsonNames.class);

        try
        {
            jsonReader = new JsonReader(new FileReader("JsonData/snames.json"));
        }
        catch (FileNotFoundException e)
        {
            message = "s name file not found";
        }

        sNames = gson.fromJson(jsonReader, jsonNames.class);
    }

    private void generateLocations()
    {
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        try
        {
            jsonReader = new JsonReader(new FileReader("JsonData/locations.json"));
        }
        catch (FileNotFoundException e)
        {
            message = "location file not found";
        }

        locations = gson.fromJson(jsonReader, jsonLocations.class);
    }
}
