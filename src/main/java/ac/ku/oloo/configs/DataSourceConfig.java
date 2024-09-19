package ac.ku.oloo.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceConfig {

    private static final HikariConfig hikariConfig = new HikariConfig();

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
            hikariConfig.setMaximumPoolSize(10);
            hikariConfig.setMinimumIdle(2);
            hikariConfig.setIdleTimeout(30000);
            hikariConfig.setConnectionTimeout(30000);
            hikariConfig.setPoolName("MyHikariCPPool");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return new HikariDataSource(hikariConfig);
    }
}


