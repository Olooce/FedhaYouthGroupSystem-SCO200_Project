package ac.ku.oloo.configs;

import co.ke.coreNexus.dbInitializer.DatabaseInitializer;
import co.ke.coreNexus.dbInitializer.ModelLoader;
import co.ke.coreNexus.dbInitializer.models.DataModel;
import co.ke.coreNexus.dbScanner.DatabaseExporter;
import co.ke.coreNexus.dbScanner.DatabaseScanner;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.configs)
 * Created by: oloo
 * On: 05/10/2024. 21:18
 * Description: Handles the export and import of the database schema
 **/

public class DB_Config {

    private static final String SCHEMA_FILE_PATH = "docs/exported_schema.json";

    public void exportDB() {
        try {
            DataSource dataSource = DataSourceConfig.getDataSource();
            DatabaseScanner scanner = new DatabaseScanner(
                    DataSourceConfig.hikariConfig.getJdbcUrl(),
                    DataSourceConfig.hikariConfig.getUsername(),
                    DataSourceConfig.hikariConfig.getPassword()
            );

            List<DataModel> dataModels = scanner.scanDatabase();
            DatabaseExporter.exportDataModelsToFile(dataModels, SCHEMA_FILE_PATH);
            System.out.println("Database schema exported successfully to " + SCHEMA_FILE_PATH);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("Failed to export database schema.");
        }
    }

    public void importDB() {
        try {
            List<DataModel> dataModels = ModelLoader.loadDataModelsFromFile(SCHEMA_FILE_PATH);
            DatabaseInitializer initializer = new DatabaseInitializer(
                    DataSourceConfig.hikariConfig.getJdbcUrl(),
                    DataSourceConfig.hikariConfig.getUsername(),
                    DataSourceConfig.hikariConfig.getPassword()
            );

            initializer.initializeDatabase(dataModels);
            System.out.println("Database schema imported successfully from " + SCHEMA_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read the schema file from " + SCHEMA_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize the database.");
        }
    }
}
