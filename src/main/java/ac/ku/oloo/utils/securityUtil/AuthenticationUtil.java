package ac.ku.oloo.utils.securityUtil;

import co.ke.coreAuth.Authenticator;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.utils.securityUtil)
 * Created by: oloo
 * On: 26/09/2024. 21:47
 * Description:
 **/
public class AuthenticationUtil {
    private static final String encryptionKey = "qwerqfgsbx8273nytgfdv27524snxjcu";

    public static boolean isAuthenticated(String username, String password,String password_hash) {
        
        return Authenticator.Authenticate(username, password_hash, createAuthHash(username,password), encryptionKey);
    }

    private static String createAuthHash(String username, String password) {
        return Authenticator.createAuthHash(username,password,encryptionKey);
    }
}
