package ac.ku.oloo.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceConfig {

    static final HikariConfig hikariConfig = new HikariConfig();

    static {
        try (InputStream input = DataSourceConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
            }

            Properties prop = new Properties();
            prop.load(input);

            hikariConfig.setJdbcUrl(prop.getProperty("db.url"));
            hikariConfig.setUsername(prop.getProperty("db.username"));
            hikariConfig.setPassword(prop.getProperty("db.password"));
            hikariConfig.setMaximumPoolSize(Integer.parseInt(prop.getProperty("maximumPoolSize")));
            hikariConfig.setMinimumIdle(Integer.parseInt(prop.getProperty("minimumIdle")));
            hikariConfig.setIdleTimeout(Long.parseLong(prop.getProperty("idleTimeout")));
            hikariConfig.setConnectionTimeout(Long.parseLong(prop.getProperty("connectionTimeout")));
            hikariConfig.setPoolName(prop.getProperty("poolName"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return new HikariDataSource(hikariConfig);
    }
}


