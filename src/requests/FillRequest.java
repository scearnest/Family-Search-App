package requests;

/** This class contains the username and number of generations for the fill command */
public class FillRequest
{

    private String username;
    private int generations;

    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }


}
