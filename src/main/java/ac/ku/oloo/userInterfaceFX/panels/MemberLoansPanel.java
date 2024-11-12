package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Loan;
import ac.ku.oloo.models.Member;
import ac.ku.oloo.services.LoanService;
import ac.ku.oloo.utils.databaseUtil.QueryExecutor;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:59
 * Description: Loans panel for viewing and applying for loans
 **/

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MemberLoansPanel {

    private final LoanService loanService = new LoanService();
    TableView<Loan> loanTable = null;
    Member member = null;

    public TabPane createLoanPanel(Member member) throws SQLException {
        TabPane tabPane = new TabPane();
        this.member = member;

        Tab viewLoansTab = new Tab("View Loans", createAppliedLoansView(member));
        Tab applyLoansTab = new Tab("Apply for Loan", createApplyLoanView(member));
        Tab guaranteeLoansTab = new Tab("Guarantee Loans", createGuaranteeLoanView(member));

        tabPane.getTabs().addAll(viewLoansTab, applyLoansTab, guaranteeLoansTab);

        return tabPane;
    }

    private VBox createAppliedLoansView(Member member) {
        VBox vbox = new VBox(10);
        List<Loan> appliedLoans = loanService.getAppliedLoans(member.getMemberId());

        if (appliedLoans.isEmpty()) {
            vbox.getChildren().add(new Label("You have no applied loans."));
        } else {
            TableView<Loan> loanTable = buildLoanTable(appliedLoans, member);
            vbox.getChildren().add(loanTable);
        }

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> refreshLoanData());
        vbox.getChildren().add(refreshButton);

        return vbox;
    }



    private TableView<Loan> buildLoanTable(List<Loan> loans, Member member) {
        loanTable = new TableView<>(FXCollections.observableArrayList(loans));

        loanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Define columns for loan attributes
        TableColumn<Loan, String> loanIdColumn = new TableColumn<>("Loan ID");
        loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));

        TableColumn<Loan, String> loanTypeColumn = new TableColumn<>("Loan Type");
        loanTypeColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));

        TableColumn<Loan, Double> loanAmountColumn = new TableColumn<>("Loan Amount");
        loanAmountColumn.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
        loanAmountColumn.setCellFactory(col -> new TableCell<Loan, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    DecimalFormat df = new DecimalFormat("#,###.00");
                    setText(df.format(item));
                }
            }
        });

        TableColumn<Loan, Integer> repaymentPeriodColumn = new TableColumn<>("Repayment Period (months)");
        repaymentPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("repaymentPeriod"));

        TableColumn<Loan, Double> interestRateColumn = new TableColumn<>("Interest Rate (%)");
        interestRateColumn.setCellValueFactory(new PropertyValueFactory<>("interestRate"));

        TableColumn<Loan, Double> guaranteedAmountColumn = new TableColumn<>("Guaranteed Amount");
        guaranteedAmountColumn.setCellValueFactory(new PropertyValueFactory<>("guaranteedAmount"));
        guaranteedAmountColumn.setCellFactory(col -> new TableCell<Loan, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    DecimalFormat df = new DecimalFormat("#,###.00");
                    setText(df.format(item));
                }
            }
        });

        TableColumn<Loan, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Add columns to the table
        loanTable.getColumns().addAll(
                loanIdColumn, loanTypeColumn, loanAmountColumn, repaymentPeriodColumn,
                interestRateColumn, guaranteedAmountColumn, statusColumn
        );

        // Payment section with conditional display
        loanTable.setRowFactory(tv -> {
            TableRow<Loan> row = new TableRow<>();

            // Context menu for "Pay Loan"
            ContextMenu contextMenu = new ContextMenu();
            MenuItem payLoanItem = new MenuItem("Pay Loan");
            payLoanItem.setOnAction(e -> handlePayment(row.getItem(), member));

            // Only show "Pay Loan" if the status is "DISBURSED" or "OVERDUE"
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                    Loan loan = row.getItem();
                    if (loan.getStatus().equalsIgnoreCase("DISBURSED") || loan.getStatus().equalsIgnoreCase("OVERDUE")) {
                        contextMenu.getItems().setAll(payLoanItem); // Show "Pay Loan" item
                        contextMenu.show(row, event.getScreenX(), event.getScreenY());
                    } else {
                        contextMenu.hide(); // Hide if the status doesn't match
                    }
                }
            });

            // Hide context menu if row is empty
            row.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    contextMenu.hide();
                }
            });

            return row;
        });

        return loanTable;
    }


    private void handlePayment(Loan loan, Member member) {
        // Dialog to select payment method (Mpesa or Card)
        ChoiceDialog<String> paymentMethodDialog = new ChoiceDialog<>("Mpesa", "Mpesa", "Card");
        paymentMethodDialog.setTitle("Select Payment Method");
        paymentMethodDialog.setHeaderText("Choose payment method");

        paymentMethodDialog.showAndWait().ifPresent(method -> {
            if ("Mpesa".equals(method)) {
                initiateMpesaPayment(loan, member);
            } else {
                initiateCardPayment(loan, member);
            }
        });
    }

    private void initiateMpesaPayment(Loan loan, Member member) {
        // Implement STK push for Mpesa
        showAlert("Payment via Mpesa", "STK Push sent. Enter your pin to complete the transaction.");
    }

    private void initiateCardPayment(Loan loan, Member member) {
        // Set up the dialog for card payment details
        Dialog<ButtonType> cardPaymentDialog = new Dialog<>();
        cardPaymentDialog.setTitle("Card Payment");
        cardPaymentDialog.setHeaderText("Enter Card Details");

        // Create input fields for card details
        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number");

        TextField expiryDateField = new TextField();
        expiryDateField.setPromptText("MM/YY");

        TextField cvvField = new TextField();
        cvvField.setPromptText("CVV");

        // Arrange fields in a grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Card Number:"), 0, 0);
        grid.add(cardNumberField, 1, 0);
        grid.add(new Label("Expiry Date:"), 0, 1);
        grid.add(expiryDateField, 1, 1);
        grid.add(new Label("CVV:"), 0, 2);
        grid.add(cvvField, 1, 2);

        cardPaymentDialog.getDialogPane().setContent(grid);

        // Add OK and Cancel buttons
        cardPaymentDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Show dialog and process payment if OK is pressed
        Optional<ButtonType> result = cardPaymentDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String cardNumber = cardNumberField.getText();
            String expiryDate = expiryDateField.getText();
            String cvv = cvvField.getText();

            // Validate card details before processing
            if (validateCardDetails(cardNumber, expiryDate, cvv)) {
                processCardPayment(loan, member, cardNumber, expiryDate, cvv);
                showAlert("Payment Successful", "Your payment for loan " + loan.getLoanId() + " has been processed.");
            } else {
                showAlert("Invalid Details", "Please enter valid card details.");
            }
        }
    }

    // Validates card details (basic example, can be expanded)
    private boolean validateCardDetails(String cardNumber, String expiryDate, String cvv) {
        // Simple validation logic: Ensure fields are non-empty
        return !cardNumber.isEmpty() && !expiryDate.isEmpty() && !cvv.isEmpty();
    }

    private void processCardPayment(Loan loan,Member member,  String cardNumber, String expiryDate, String cvv) {
        // TODO: Logic for processing card payment (e.g., contacting a payment API)
        // Placeholder: Print the details as a simulation
        System.out.println("Processing card payment:");
        System.out.println("Loan ID: " + loan.getLoanId());
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("CVV: " + cvv);

        loanService.payLoan(loan.getLoanId(), member.getMemberId());
        loan.setStatus("PAID");
        refreshLoanTableView();
        showAlert("Payment Successful", "Your payment for loan " + loan.getLoanId() + " has been processed.");
    }

    private void refreshLoanTableView() {
        loanTable.refresh();
    }

    // Refresh the loan data by re-fetching it from the database
    private void refreshLoanData() {
        List<Loan> updatedLoans = loanService.getAppliedLoans(member.getMemberId());
        ObservableList<Loan> updatedData = FXCollections.observableArrayList(updatedLoans);

        // Clear old data and update the table with new data
        loanTable.setItems(updatedData);
    }

    private VBox createApplyLoanView(Member member) throws SQLException {
        VBox vbox = new VBox(10);

        // Components for loan application: loan type, amount, repayment period, guarantors
        ComboBox<String> loanTypeComboBox = new ComboBox<>(FXCollections.observableArrayList("Business Loan", "Personal Loan", "Emergency Loan"));
        TextField loanAmountField = new TextField();

        // Drop-down for repayment period with predefined options
        ComboBox<Integer> repaymentPeriodComboBox = new ComboBox<>(FXCollections.observableArrayList(6, 12, 24, 36, 48, 60));
        repaymentPeriodComboBox.setPromptText("Select Repayment Period");

        // Max loan label
        Label maxLoanLabel = new Label("Maximum Loan You Can Borrow: " + formatAmount(loanService.calculateMaxLoan(member)));


        // Apply button for submitting the loan application
        Button applyButton = new Button("Apply");

        applyButton.setOnAction(e -> {
            Integer selectedRepaymentPeriod = repaymentPeriodComboBox.getValue();
            if (selectedRepaymentPeriod != null) {
                applyForLoan(member, loanTypeComboBox.getValue(), loanAmountField.getText(), selectedRepaymentPeriod.toString());
            } else {
                showAlert("Error", "Please select a repayment period.");
            }
        });

        // Add components to VBox
        vbox.getChildren().addAll(
                new Label("Loan Type"), loanTypeComboBox,
                new Label("Amount"), loanAmountField,
                new Label("Repayment Period (months)"), repaymentPeriodComboBox,
                maxLoanLabel, applyButton
        );

        return vbox;
    }


    private void applyForLoan(Member member, String loanType, String amount, String repaymentPeriod) {
        double loanAmount = Double.parseDouble(amount);
        int repaymentMonths = Integer.parseInt(repaymentPeriod);
        double interestRate = loanService.getInterestRate(loanType); // retrieve interest rate based on type

        // Calculate maximum eligible loan for validation
        double maxLoanEligible = 0;
        try {
            maxLoanEligible = loanService.calculateMaxLoan(member);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Validate loan amount
        if (loanAmount > maxLoanEligible) {
            showAlert("Loan Application Failed", "You cannot borrow more than your maximum eligible loan.");
            return;
        }

        // Calculate guaranteed amount requirement
        double guaranteedAmount = 0;
        try {
            guaranteedAmount = loanAmount - member.getShares();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Validate guarantors
        try {
            if (!loanService.validateGuarantors(guaranteedAmount, loanAmount, member.getShares())) {
                showAlert("Guarantor Validation", "Awaiting sufficient guarantor approval for your loan.");
                refreshLoanTableView();
                return;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Error validating guarantors.");
            return;
        }

        // Insert loan application in DB, setting initial status to 'PENDING'
        loanService.applyLoan(member.getMemberId(), loanType, loanAmount, repaymentMonths, guaranteedAmount, interestRate);
        showAlert("Loan Application", "Loan application submitted. Awaiting guarantor approvals.");
        refreshLoanTableView();
    }

    private VBox createGuaranteeLoanView(Member member) {
        VBox vbox = new VBox(10);
        Label infoLabel = new Label("Select a loan to guarantee. Enter an amount up to your total shares.");

        TableView<Loan> tableView = new TableView<>();

        // Loan ID column
        TableColumn<Loan, Long> loanIdCol = new TableColumn<>("Loan ID");
        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("loanId"));

        // Loan Amount column
        TableColumn<Loan, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(cellData -> {
            double amount = cellData.getValue().getAmount();
            // Convert the formatted amount to a SimpleStringProperty
            return new SimpleStringProperty(formatAmount(amount));
        });

        // Remaining Guarantee column
        TableColumn<Loan, String> remainingGuaranteeCol = new TableColumn<>("Remaining Guarantee");
        remainingGuaranteeCol.setCellValueFactory(cellData -> {
            double remaining = cellData.getValue().getAmount() - cellData.getValue().getGuaranteedAmount();
            // Convert the formatted amount to a SimpleStringProperty
            return new SimpleStringProperty(formatAmount(remaining));
        });

        // Guarantee Amount column (button to trigger dialog)
        TableColumn<Loan, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> new TableCell<Loan, Void>() {
            private final Button guaranteeButton = new Button("Guarantee");

            {
                guaranteeButton.setOnAction(event -> {
                    Loan loan = getTableView().getItems().get(getIndex());

                    // Create the dialog for the guarantee amount
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Guarantee Loan");
                    dialog.setHeaderText("Enter the amount you want to guarantee for this loan.");
                    dialog.setContentText("Guarantee Amount:");

                    // Show the dialog and handle the input
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(amountStr -> {
                        double enteredAmount = 0;
                        try {
                            enteredAmount = Double.parseDouble(amountStr);
                        } catch (NumberFormatException ex) {
                            infoLabel.setText("Please enter a valid number.");
                            return;
                        }

                        // Validate guarantee amount
                        try {
                            if (enteredAmount > member.getShares()) {
                                infoLabel.setText("Guarantee amount cannot exceed your total shares.");
                                return;
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        double remainingGuarantee = loan.getAmount() - loan.getGuaranteedAmount();
                        if (enteredAmount > remainingGuarantee) {
                            infoLabel.setText("Amount exceeds remaining required guarantee for this loan.");
                            return;
                        }

                        // Guarantee the loan
                        boolean success = loanService.guaranteeLoan(loan, member, enteredAmount);
                        if (success) {
                            infoLabel.setText("Guaranteed successfully!");
                            loan.setGuaranteedAmount(loan.getGuaranteedAmount() + enteredAmount);
                            tableView.refresh();
                        } else {
                            infoLabel.setText("Error guaranteeing loan.");
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : guaranteeButton);
            }
        });

        // Add columns to the table
        tableView.getColumns().addAll(loanIdCol, amountCol, remainingGuaranteeCol, actionCol);

        // Populate table with loans
        List<Loan> loans = loanService.getLoansToGuarantee(member);
        tableView.setItems(FXCollections.observableArrayList(loans));

        vbox.getChildren().addAll(infoLabel, tableView);
        return vbox;
    }






    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private String formatAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(amount);
    }
}

