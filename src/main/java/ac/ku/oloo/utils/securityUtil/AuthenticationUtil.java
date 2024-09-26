package ac.ku.oloo.utils.securityUtil;

import co.ke.coreAuth.Authenticator;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.utils.securityUtil)
 * Created by: oloo
 * On: 26/09/2024. 21:47
 * Description:
 **/
public class AuthenticationUtil {
    public boolean isAuthenticated(String username, String password) {


        return Authenticator.Authenticate(username, password_hash, createAuthHash(username,password), encryptionKey);
    }

    public String createAuthHash(String username, String password) {
        return null;
    }
}
