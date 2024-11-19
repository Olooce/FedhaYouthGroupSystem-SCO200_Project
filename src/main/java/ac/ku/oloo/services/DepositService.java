package ac.ku.oloo.services;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 19/11/2024. 05:38
 * Description:
 **/

import ac.ku.oloo.configs.DataSourceConfig;
import ac.ku.oloo.models.Deposit;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepositService {

    private final Connection connection;
    private static final DataSource dataSource = DataSourceConfig.getDataSource();

    public DepositService() throws SQLException {
        this.connection = dataSource.getConnection();
    }

    // 1. Add a deposit
    public boolean addDeposit(long memberId, double amount) {
        String sql = "INSERT INTO deposits (member_id, amount) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, memberId);
            stmt.setDouble(2, amount);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Get the latest 10 deposits for a member
    public List<String> getLatestDeposits(long memberId) {
        String sql = "SELECT amount, date_created FROM deposits WHERE member_id = ? ORDER BY date_created DESC LIMIT 10";
        List<String> deposits = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double amount = rs.getDouble("amount");
                Timestamp date = rs.getTimestamp("date_created");
                deposits.add("$" + amount + " - " + date.toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deposits;
    }

    // 3. Get the total deposits for a member
    public double getTotalDeposits(long memberId) {
        String sql = "SELECT SUM(amount) as total FROM deposits WHERE member_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


    // 4. Get all deposits
    public List<Deposit> getAllDeposits() {
        String sql = "SELECT deposit_id, member_id, amount, date_created FROM deposits";
        List<Deposit> deposits = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                long depositId = rs.getLong("deposit_id");
                long memberId = rs.getLong("member_id");
                double amount = rs.getDouble("amount");
                Timestamp dateCreated = rs.getTimestamp("date_created");

                deposits.add(new Deposit(depositId, memberId, amount, dateCreated.toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deposits;
    }
}
