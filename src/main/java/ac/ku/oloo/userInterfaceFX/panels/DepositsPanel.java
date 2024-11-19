package ac.ku.oloo.userInterfaceFX.panels;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:58
 * Description:
 **/

public class DepositsPanel {

    public VBox createAccountStatementsPanel() {
        VBox mainPanel = new VBox(10);
        mainPanel.setPadding(new Insets(20));

        // Create tabs
        TabPane tabPane = new TabPane();

        // Tab 1: Deposit Funds
        Tab depositTab = new Tab("Deposit");
        VBox depositContent = new VBox(10);
        depositContent.setPadding(new Insets(10));

        // Deposit form
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to deposit");
        Button depositButton = new Button("Deposit");
        Label depositMessage = new Label();

        depositButton.setOnAction(event -> {
            String amountText = amountField.getText();
            if (!amountText.isEmpty() && amountText.matches("\\d+(\\.\\d{1,2})?")) {
                double amount = Double.parseDouble(amountText);
                // Simulate deposit logic (e.g., add to account balance)
                depositMessage.setText("Successfully deposited: $" + amount);
                amountField.clear();
            } else {
                depositMessage.setText("Invalid amount. Please try again.");
            }
        });

        depositContent.getChildren().addAll(
                new Label("Deposit Funds"),
                amountField,
                depositButton,
                depositMessage
        );
        depositTab.setContent(depositContent);

        // Tab 2: Latest Deposits
        Tab latestDepositsTab = new Tab("Statements");
        VBox latestDepositsContent = new VBox(10);
        latestDepositsContent.setPadding(new Insets(10));

        // Simulated deposit history
        String[] latestDeposits = {
                "$100.00 - Nov 18, 2024",
                "$50.00 - Nov 17, 2024",
                "$75.00 - Nov 16, 2024",
                "$200.00 - Nov 15, 2024",
                "$150.00 - Nov 14, 2024",
                "$300.00 - Nov 13, 2024",
                "$500.00 - Nov 12, 2024",
                "$250.00 - Nov 11, 2024",
                "$400.00 - Nov 10, 2024",
                "$350.00 - Nov 9, 2024"
        };

        ListView<String> depositsListView = new ListView<>();
        depositsListView.getItems().addAll(latestDeposits);

        latestDepositsContent.getChildren().addAll(
                new Label("Here are your latest 10 deposits:"),
                depositsListView
        );
        latestDepositsTab.setContent(latestDepositsContent);

        // Tab 3: Buy Shares
        Tab buySharesTab = new Tab("Buy Shares");
        VBox buySharesContent = new VBox(10);
        buySharesContent.setPadding(new Insets(10));

        // Buy Shares form
        TextField sharesField = new TextField();
        sharesField.setPromptText("Enter number of shares");
        TextField priceField = new TextField();
        priceField.setPromptText("Enter price per share");
        Button buyButton = new Button("Buy Shares");
        Label buyMessage = new Label();

        buyButton.setOnAction(event -> {
            String sharesText = sharesField.getText();
            String priceText = priceField.getText();
            if (!sharesText.isEmpty() && sharesText.matches("\\d+") && !priceText.isEmpty() && priceText.matches("\\d+(\\.\\d{1,2})?")) {
                int shares = Integer.parseInt(sharesText);
                double price = Double.parseDouble(priceText);
                double totalCost = shares * price;
                buyMessage.setText("Successfully purchased " + shares + " shares for a total cost of $" + totalCost);
                sharesField.clear();
                priceField.clear();
            } else {
                buyMessage.setText("Invalid input. Please enter valid numbers for shares and price.");
            }
        });

        buySharesContent.getChildren().addAll(
                new Label("Buy Shares"),
                sharesField,
                priceField,
                buyButton,
                buyMessage
        );
        buySharesTab.setContent(buySharesContent);

        // Add tabs to tab pane
        tabPane.getTabs().addAll(depositTab, latestDepositsTab, buySharesTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add tab pane to the main panel
        mainPanel.getChildren().add(tabPane);

        return mainPanel;
    }

}
