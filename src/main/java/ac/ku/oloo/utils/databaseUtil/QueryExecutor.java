package ac.ku.oloo.utils.databaseUtil;

import ac.ku.oloo.configs.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CoreNexus-Utils (co.ke.coreNexus.utils.QueryExecutor)
 * Created by: oloo
 * On: 11/06/2024. 23:37
 * Description: Executes SQL queries
 **/

public class QueryExecutor {

    private static final DataSource dataSource = DataSourceConfig.getDataSource();

    public static <T> List<T> executeQuery(String query, RowMapper<T> rowMapper, Object... params) throws SQLException {
        List<T> results = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(rowMapper.mapRow(rs));
                }
            }
        }
        return results;
    }

    public static <T> T executeInsert(String sql, RowMapper<T> rowMapper, Object... params) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                return rowMapper.mapRow(generatedKeys);
            }
        }
    }


    public static void executeUpdate(String query, Object... params) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        }
    }

    public static <T> T executeSingleResultQuery(String query, RowMapper<T> rowMapper, Object... params) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rowMapper.mapRow(rs);  // Return the first result
                }
            }
        }
        return null;  // Return null if no result is found
    }

    @FunctionalInterface
    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}
