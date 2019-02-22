package requests;
import models.User;

/**Contains all the data needed to request a register*/
public class RegisterRequest {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
