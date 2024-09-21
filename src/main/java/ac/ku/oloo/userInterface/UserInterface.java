package ac.ku.oloo.userInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo)
 * Created by: oloo
 * On: 21/09/2024. 22:34
 * Description: JavaFX based UI for the system.
 **/
public class UserInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fedha Youth Group System");

        // Main layout
        BorderPane root = new BorderPane();

        // Menu bar
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        // Content
        VBox contentPanel = createContentPanel();
        root.setCenter(contentPanel);

        // Footer
        Label footerLabel = new Label("© 2024 Oloo Stephen");
        root.setBottom(footerLabel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitItem);

        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    private VBox createContentPanel() {
        VBox vbox = new VBox();

        // Add tabs for different sections
        TabPane tabPane = new TabPane();

        Tab memberTab = new Tab("Members", createMemberPanel());
        Tab loanTab = new Tab("Loans", createLoanPanel());
        Tab depositTab = new Tab("Deposits", createDepositPanel());

        tabPane.getTabs().addAll(memberTab, loanTab, depositTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        vbox.getChildren().add(tabPane);
        return vbox;
    }

    private VBox createMemberPanel() {
        VBox vbox = new VBox();
        Label label = new Label("Member Information");
        vbox.getChildren().add(label);
        // TODO: Add more member-specific UI components here
        return vbox;
    }

    private VBox createLoanPanel() {
        VBox vbox = new VBox();
        Label label = new Label("Loan Information");
        vbox.getChildren().add(label);
        // TODO: Add more loan-specific UI components here
        return vbox;
    }

    private VBox createDepositPanel() {
        VBox vbox = new VBox();
        Label label = new Label("Deposit Information");
        vbox.getChildren().add(label);
        // TODO: Add more deposit-specific UI components here
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}