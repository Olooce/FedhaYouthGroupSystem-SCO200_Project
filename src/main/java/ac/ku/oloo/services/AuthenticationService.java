package ac.ku.oloo.services;

import ac.ku.oloo.models.AuthResult;
import ac.ku.oloo.models.Member;
import ac.ku.oloo.models.User;
import ac.ku.oloo.utils.databaseUtil.QueryExecutor;
import ac.ku.oloo.utils.securityUtil.AuthenticationUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 26/09/2024. 23:38
 * Description:
 **/
public class AuthenticationService {
    public static AuthResult authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? LIMIT ?";

        List<User> userList = QueryExecutor.executeQuery(sql, rs -> {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setRole(rs.getString("role"));
            user.setMemberId(rs.getLong("member_id"));
            user.setPasswordHash(rs.getString("password_hash"));
            user.setDateCreated(rs.getTimestamp("date_created"));
            user.setDateModified(rs.getTimestamp("date_modified"));
            return user;
        }, username, 1);

        if (userList.isEmpty()) {
            return new AuthResult(false, null);
        }

        User user = userList.get(0);
        boolean isAuthenticated = AuthenticationUtil.isAuthenticated(username, password, user.getPasswordHash());

        return new AuthResult(isAuthenticated, isAuthenticated ? user : null);
    }

}
