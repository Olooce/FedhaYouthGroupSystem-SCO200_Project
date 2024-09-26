package ac.ku.oloo.userInterface;

import ac.ku.oloo.userInterface.panels.DepositPanel;
import ac.ku.oloo.userInterface.panels.LoanPanel;
import ac.ku.oloo.userInterface.panels.MemberPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

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

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());
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
