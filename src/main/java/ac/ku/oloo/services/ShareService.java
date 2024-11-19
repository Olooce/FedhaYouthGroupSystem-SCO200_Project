package ac.ku.oloo.services;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 19/11/2024. 05:39
 * Description:
 **/

import ac.ku.oloo.configs.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.*;

public class ShareService {

    private final Connection connection;
    private static final DataSource dataSource = DataSourceConfig.getDataSource();

    public ShareService() throws SQLException {
        this.connection = dataSource.getConnection();
    }

    // 1. Buy shares
    public boolean buyShares(long memberId, double shares) {
        String sql = "INSERT INTO shares (member_id, total_shares) VALUES (?, ?) ON DUPLICATE KEY UPDATE total_shares = total_shares + VALUES(total_shares)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, memberId);
            stmt.setDouble(2, shares);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Get total shares for a member
    public double getTotalShares(long memberId) {
        String sql = "SELECT total_shares FROM shares WHERE member_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_shares");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // 3. Increment total shares (alternative implementation for buying shares)
    public boolean incrementShares(long memberId, double additionalShares) {
        String sql = "UPDATE shares SET total_shares = total_shares + ? WHERE member_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, additionalShares);
            stmt.setLong(2, memberId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

