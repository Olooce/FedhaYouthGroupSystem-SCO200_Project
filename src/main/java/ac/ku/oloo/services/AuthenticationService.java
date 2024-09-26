package ac.ku.oloo.services;

import ac.ku.oloo.models.Member;
import ac.ku.oloo.models.User;
import ac.ku.oloo.utils.databaseUtil.QueryExecutor;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 26/09/2024. 23:38
 * Description:
 **/
public class AuthenticationService {
    public boolean authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user_accounts.users WHERE username = ? LIMIT ?";

        List<User> userList =  QueryExecutor.executeQuery(sql, rs -> {
            User user = new User;

            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));


            return user;
        }, username, 1);
    }
}
