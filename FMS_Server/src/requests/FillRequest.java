package requests;

/** This class contains the username and number of generations for the fill command */
public class FillRequest
{
    private String username;
    private int generations;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }
}
