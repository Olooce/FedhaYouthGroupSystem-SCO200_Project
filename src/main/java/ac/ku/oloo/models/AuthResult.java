package ac.ku.oloo.models;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 27/09/2024. 08:34
 * Description:
 **/

public class AuthResult {
    private boolean isAuthenticated;
    private final User user;

    public AuthResult(boolean isAuthenticated, User user) {
        this.isAuthenticated = isAuthenticated;
        this.user = user;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public User getUser() {
        return user;
    }
}
