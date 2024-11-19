package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.services.DepositService;
import ac.ku.oloo.services.ShareService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:58
 * Description:
 **/

public class DepositsPanel {

    private final DepositService depositService;
    private final ShareService shareService;
    private final long memberId;

    public DepositsPanel(long memberId) throws SQLException {
        this.depositService = new DepositService();
        this.shareService = new ShareService();
        this.memberId = memberId;
    }

    public VBox createAccountStatementsPanel() {
        VBox mainPanel = new VBox(10);
        mainPanel.setPadding(new Insets(20));

        // Create tabs
        TabPane tabPane = new TabPane();

        // Tab 1: Deposit Funds
        Tab depositTab = new Tab("Deposit");
        VBox depositContent = new VBox(10);
        depositContent.setPadding(new Insets(10));

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to deposit");
        Button depositButton = new Button("Deposit");
        Label depositMessage = new Label();

        depositButton.setOnAction(event -> {
            String amountText = amountField.getText();
            if (!amountText.isEmpty() && amountText.matches("\\d+(\\.\\d{1,2})?")) {
                double amount = Double.parseDouble(amountText);
                boolean success = depositService.addDeposit(memberId, amount);
                if (success) {
                    depositMessage.setText("Successfully deposited: $" + amount);
                    amountField.clear();
                } else {
                    depositMessage.setText("Failed to deposit. Please try again.");
                }
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

        ListView<String> depositsListView = new ListView<>();
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> {
            List<String> latestDeposits = depositService.getLatestDeposits(memberId);
            depositsListView.getItems().setAll(latestDeposits);
        });

        latestDepositsContent.getChildren().addAll(
                new Label("Here are your latest 10 deposits:"),
                depositsListView,
                refreshButton
        );
        latestDepositsTab.setContent(latestDepositsContent);

        // Tab 3: Buy Shares
        Tab buySharesTab = new Tab("Buy Shares");
        VBox buySharesContent = new VBox(10);
        buySharesContent.setPadding(new Insets(10));

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
                double shares = Double.parseDouble(sharesText);
                double price = Double.parseDouble(priceText);
                double totalCost = shares * price;

                boolean success = shareService.buyShares(memberId, shares);
                if (success) {
                    buyMessage.setText("Successfully purchased " + shares + " shares for a total cost of $" + totalCost);
                    sharesField.clear();
                    priceField.clear();
                } else {
                    buyMessage.setText("Failed to purchase shares. Please try again.");
                }
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
