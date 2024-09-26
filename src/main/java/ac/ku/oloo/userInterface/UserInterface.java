package ac.ku.oloo.userInterface;

import ac.ku.oloo.userInterface.panels.DepositPanel;
import ac.ku.oloo.userInterface.panels.LoanPanel;
import ac.ku.oloo.userInterface.panels.MemberPanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.Year;
import java.util.Objects;

public class UserInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fedha Youth Group");

        // Show the start screen
        StackPane startScreen = createStartScreen();
        Scene startScene = new Scene(startScreen, 800, 600);
        primaryStage.setScene(startScene);
        primaryStage.show();

        // Timeline to switch screens after a delay
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> showLoginScreen(primaryStage)));
        timeline.play();
    }

    private StackPane createStartScreen() {
        StackPane startScreen = new StackPane();

        // Load the image
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/logo.jpg")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);
//        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // Loading animation
        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(true);

        // Add components to the start screen
        startScreen.getChildren().addAll(imageView, loadingIndicator);

        return startScreen;
    }

    private void showLoginScreen(Stage primaryStage) {
        // Create the login screen layout
        VBox loginLayout = new VBox(15);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(20));

        Label loginLabel = new Label("Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            // TODO: Replace with login logic
            if (usernameField.getText().equals("user") && passwordField.getText().equals("password")) {
                showMainApp(primaryStage);
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        loginLayout.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton);

        Scene loginScene = new Scene(loginLayout, 800, 600);
        primaryStage.setScene(loginScene);
    }

    private void showMainApp(Stage primaryStage) {
        // Main layout for the application
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

        Scene mainScene = new Scene(root, 800, 600);
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());
        primaryStage.setScene(mainScene);
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
