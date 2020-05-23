package JsonData;
import java.util.Random;

public class jsonNames
{
    public String[] data = null;

    public String[] getNames() {
        return data;
    }

    public void setNames(String[] data) {
        this.data = data;
    }

    public String getRandom()
    {
        Random r = new Random();
        int num = r.nextInt(data.length);
        return data[num];
    }
}
