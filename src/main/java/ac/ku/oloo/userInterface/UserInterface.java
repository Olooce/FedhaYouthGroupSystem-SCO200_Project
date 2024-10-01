package ac.ku.oloo.userInterface;

import ac.ku.oloo.models.AuthResult;
import ac.ku.oloo.models.User;
import ac.ku.oloo.services.AuthenticationService;
import ac.ku.oloo.userInterface.panels.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.time.Year;
import java.util.Objects;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo)
 * Created by: oloo
 * On: 21/09/2024. 22:34
 * Description:
 **/

public class UserInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fedha Group");

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
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/logo.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);
        imageView.setSmooth(true);

        // Loading animation
        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(true);

        // Add components to the start screen
        startScreen.getChildren().addAll(imageView, loadingIndicator);

        return startScreen;
    }
    private void showLoginScreen(Stage primaryStage) {
        // Create GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        gridPane.setPrefSize(500, 400);

        // Add shadow effect to the GridPane
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(5);
        shadow.setOffsetY(5);
        shadow.setColor(Color.color(0, 0, 0, 0.5));
        gridPane.setEffect(shadow);

        // Title label
        Label titleLabel = new Label("Login");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.WHITE);
        gridPane.add(titleLabel, 0, 0, 2, 1);

        // Username label and field
        Label usernameLabel = new Label("Username");
        usernameLabel.setTextFill(Color.WHITE);
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(300);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);

        // Password label and field
        Label passwordLabel = new Label("Password");
        passwordLabel.setTextFill(Color.WHITE);
        PasswordField passwordField = new PasswordField();
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);

        // Remember me checkbox
        CheckBox rememberMeCheckBox = new CheckBox("Remember me");
        rememberMeCheckBox.setTextFill(Color.WHITE);
        gridPane.add(rememberMeCheckBox, 1, 3);

        // Forgotten password link
        Hyperlink forgottenPasswordLink = new Hyperlink("Forgotten password?");
        forgottenPasswordLink.setTextFill(Color.LIGHTGRAY);
        gridPane.add(forgottenPasswordLink, 1, 4);
        GridPane.setHalignment(forgottenPasswordLink, HPos.RIGHT);

        // Login button
        Button loginButton = new Button("LOGIN");
        loginButton.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #ff0080, #ff8c00);" +
                "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        loginButton.setPrefWidth(300);
        gridPane.add(loginButton, 1, 5);

        loginButton.setOnAction(e -> {
            try {
                AuthResult authResult = AuthenticationService.authenticate(usernameField.getText(), passwordField.getText());

                if (authResult.isAuthenticated()) {
                    User user = authResult.getUser();
                    if (Objects.equals(user.getRole(), "STAFF")) {
                        showMainApp(primaryStage); // Show staff dashboard
                    } else if (Objects.equals(user.getRole(), "MEMBER")) {
                        showMemberApp(primaryStage, user); // Show member dashboard
                    }
                } else {
                    showAlert("Login Failed", "Invalid username or password.");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Register button
        Button registerButton = new Button("REGISTER");
        registerButton.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        registerButton.setPrefWidth(300);
        gridPane.add(registerButton, 1, 6);

        // Wrap the GridPane in a VBox
        VBox loginLayout = new VBox(15);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(20));
        loginLayout.getChildren().add(gridPane);

        // Create the scene with the login layout
        Scene loginScene = new Scene(loginLayout, 800, 600);
        primaryStage.setTitle("Login Panel");
        primaryStage.setScene(loginScene);
    }
//
//    private void showLoginScreen(Stage primaryStage) {
//        // Create the login screen layout
//        VBox loginLayout = new VBox(15);
//        loginLayout.setAlignment(Pos.CENTER);
//        loginLayout.setPadding(new Insets(20));
//
//        Label loginLabel = new Label("Login");
//        TextField usernameField = new TextField();
//        usernameField.setPromptText("Username");
//        PasswordField passwordField = new PasswordField();
//        passwordField.setPromptText("Password");
//        Button loginButton = new Button("Login");
//
//        loginButton.setOnAction(e -> {
//            try {
//                AuthResult authResult = AuthenticationService.authenticate(usernameField.getText(), passwordField.getText());
//
//                if (authResult.isAuthenticated()) {
//                    User user = authResult.getUser();
//                    if (Objects.equals(user.getRole(), "STAFF")) {
//                        showMainApp(primaryStage); // Show staff dashboard
//                    } else if (Objects.equals(user.getRole(), "MEMBER")) {
//                        showMemberApp(primaryStage, user); // Show member dashboard
//                    }
//                } else {
//                    showAlert("Login Failed", "Invalid username or password.");
//                }
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//
//        loginLayout.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton);
//
//        Scene loginScene = new Scene(loginLayout, 800, 600);
//        primaryStage.setScene(loginScene); // Set the login scene on primaryStage
//    }

    private void showMainApp(Stage primaryStage) {
        primaryStage.setTitle("Staff Dashboard"); // Change title to "Staff Dashboard"

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

    private void showMemberApp(Stage primaryStage, User user) {
        primaryStage.setTitle("Member Dashboard"); // Change title to "Member Dashboard"

        // Main layout for member app
        BorderPane root = new BorderPane();

        // Create tabbed interface
        TabPane tabPane = new TabPane();

        // Add tabs for user information, account statements, and loans
        Tab userTab = new Tab("User Info", new UserPanel(user).createUserPanel());
        Tab statementsTab = new Tab("Account Statements", new AccountStatementsPanel().createAccountStatementsPanel());
        Tab loanTab = new Tab("Loans", new MemberLoansPanel().createLoanPanel());

        tabPane.getTabs().addAll(userTab, statementsTab, loanTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);

        root.setCenter(tabPane);

        int currentYear = Year.now().getValue();
        Label footerLabel = new Label("© " + currentYear + " Oloo Stephen");
        footerLabel.getStyleClass().add("footerLabel");

        HBox footerBox = new HBox();
        footerBox.setAlignment(Pos.CENTER);
        footerBox.getChildren().add(footerLabel);

        root.setBottom(footerBox);

        Scene memberScene = new Scene(root, 800, 600);
        primaryStage.setScene(memberScene);
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

        Tab memberTab = new Tab("Members", new MembersPanel().createMemberPanel());
        Tab loanTab = new Tab("Loans", new LoansPanel().createLoanPanel());
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