package ac.ku.oloo.userInterface;

import ac.ku.oloo.userInterface.panels.DepositPanel;
import ac.ku.oloo.userInterface.panels.LoanPanel;
import ac.ku.oloo.userInterface.panels.MemberPanel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Year;
import java.util.Objects;

public class UserInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fedha Youth Group");

        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing the application...");
            System.exit(0);
        });

        // Main layout
        BorderPane root = new BorderPane();

        // Menu bar
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        // Content
        VBox contentPanel = createContentPanel();
        root.setCenter(contentPanel);

        // Footer
        int currentYear = Year.now().getValue();
        Label footerLabel = new Label("© " + currentYear + " Oloo Stephen");
        footerLabel.getStyleClass().add("footerLabel");

        HBox footerBox = new HBox();
        footerBox.setAlignment(Pos.CENTER);
        footerBox.getChildren().add(footerLabel);

        root.setBottom(footerBox);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());
        primaryStage.setScene(scene);

        // Show the stage
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

        Tab memberTab = new Tab("Members", new MemberPanel().createMemberPanel());
        Tab loanTab = new Tab("Loans", new LoanPanel().createLoanPanel());
        Tab depositTab = new Tab("Deposits", new DepositPanel().createDepositPanel());

        tabPane.getTabs().addAll(memberTab, loanTab, depositTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);

        vbox.getChildren().add(tabPane);
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
