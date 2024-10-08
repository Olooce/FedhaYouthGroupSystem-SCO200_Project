package ac.ku.oloo;

import ac.ku.oloo.configs.DB_Config;
import ac.ku.oloo.userInterfaceFX.UserInterface;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo)
 * Created by: oloo
 * On: 19/09/2024. 11:43
 * Description: Would normally handle initialization
 * and starting of the backend server but now just starts
 * the application because I just had to couple the UI with the business logic ...sorry (XD)
 **/
public class fedhaApp {
    public static void main(String[] args) {
//
        DB_Config dbConfig = new DB_Config();
//
//        //Uncomment this to export the database schema
//        System.out.println("Starting database export...");
//        dbConfig.exportDB();
//        System.out.println("Database export completed.");

//        // Uncomment this to import the database schema
//        System.out.println("Starting database import...");
//        dbConfig.importDB();
//        System.out.println("Database import completed.");

        UserInterface.main(args);
    }

}