package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Loan;
import ac.ku.oloo.models.Member;
import ac.ku.oloo.services.LoanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:59
 * Description: Loans panel for viewing and applying for loans
 **/

//public class MemberLoansPanel {
//
//    private final LoanService loanService = new LoanService();
//
//    public VBox createLoanPanel(Member member) throws SQLException {
//        VBox vbox = new VBox(10);
//        vbox.setPadding(new Insets(10));
//
//        // Section 1: Viewing and Paying Loans
//        Label viewLoansLabel = new Label("Your Applied Loans");
//        VBox appliedLoansBox = createAppliedLoansView(member);
//
//        // Section 2: Applying for New Loans
//        Label applyLoansLabel = new Label("Apply for a New Loan");
//        VBox applyLoanBox = createApplyLoanView(member);
//
//        // Add both sections to the VBox
//        vbox.getChildren().addAll(viewLoansLabel, appliedLoansBox, applyLoansLabel, applyLoanBox);
//
//        return vbox;
//    }
//
//    // Create a view for listing applied loans and handling payments
//    private VBox createAppliedLoansView(Member member) {
//        VBox vbox = new VBox(10);
//
//        List<Loan> appliedLoans = loanService.getAppliedLoans(member.getMemberId());
//
//        if (appliedLoans.isEmpty()) {
//            Label noLoansLabel = new Label("You have no applied loans.");
//            vbox.getChildren().add(noLoansLabel);
//        } else {
//            // Create TableView and define columns
//            TableView<Loan> loanTable = new TableView<>();
//            loanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//            // Define columns for loan attributes
//            TableColumn<Loan, String> loanIdColumn = new TableColumn<>("Loan ID");
//            loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));
//
//            TableColumn<Loan, String> loanTypeColumn = new TableColumn<>("Loan Type");
//            loanTypeColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));
//
//            TableColumn<Loan, Double> loanAmountColumn = new TableColumn<>("Loan Amount");
//            loanAmountColumn.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
//
//            TableColumn<Loan, Integer> repaymentPeriodColumn = new TableColumn<>("Repayment Period (months)");
//            repaymentPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("repaymentPeriod"));
//
//            TableColumn<Loan, Double> interestRateColumn = new TableColumn<>("Interest Rate (%)");
//            interestRateColumn.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
//
//            TableColumn<Loan, Double> guaranteedAmountColumn = new TableColumn<>("Guaranteed Amount");
//            guaranteedAmountColumn.setCellValueFactory(new PropertyValueFactory<>("guaranteedAmount"));
//
//            TableColumn<Loan, String> statusColumn = new TableColumn<>("Status");
//            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
//
//            // Add columns to the table
//            loanTable.getColumns().addAll(
//                    loanIdColumn, loanTypeColumn, loanAmountColumn, repaymentPeriodColumn,
//                    interestRateColumn, guaranteedAmountColumn, statusColumn
//            );
//
//            // Convert the appliedLoans list to an observable list and set it as the table data
//            ObservableList<Loan> loanData = FXCollections.observableArrayList(appliedLoans);
//            loanTable.setItems(loanData);
//
//            // Add the TableView to the VBox
//            vbox.getChildren().add(loanTable);
//        }
//
//        return vbox;
//    }
//
//
//    private HBox getLoanRow(Member member, Loan loan) {
//        Label loanLabel = new Label("Loan ID: " + loan.getLoanId() + ", Amount: " + loan.getAmount() +
//                ", Remaining: " + loan.getRemainingBalance());
//        Button payButton = new Button("Pay Loan");
//
//        // Handle loan payment
//        payButton.setOnAction(e -> {
//            loanService.payLoan(loan.getLoanId(), member.getMemberId());
//            showAlert("Payment Successful", "You have successfully paid for the loan.");
//        });
//
//        return new HBox(10, loanLabel, payButton);
//    }
//
//    // Create a view for applying new loans
//    private VBox createApplyLoanView(Member member) throws SQLException {
//        VBox vbox = new VBox(10);
//
//        // Dropdown for loan types
//        Label loanTypeLabel = new Label("Select Loan Type:");
//        ComboBox<String> loanTypeComboBox = new ComboBox<>();
//        loanTypeComboBox.getItems().addAll("Business Loan", "Emergency Loan", "Personal Loan");
//
//        // Input for loan amount
//        Label loanAmountLabel = new Label("Enter Loan Amount:");
//        TextField loanAmountField = new TextField();
//
//        // Input for repayment period
//        Label repaymentLabel = new Label("Enter Repayment Period (months):");
//        TextField repaymentField = new TextField();
//
//        // Display total shares for eligibility
//        Label sharesLabel = new Label("Total Shares: " + member.getShares());
//
//        // Maximum loan amount display
//        double maxLoan = loanService.calculateMaxLoan(member);
//        Label maxLoanLabel = new Label("Maximum Loan You Can Borrow: " + maxLoan);
//
//        // Button to apply for loan
//        Button applyButton = new Button("Apply for Loan");
//        applyButton.setOnAction(e -> {
//            // Get loan details
//            String loanType = loanTypeComboBox.getValue();
//            double loanAmount = Double.parseDouble(loanAmountField.getText());
//            int repaymentPeriod = Integer.parseInt(repaymentField.getText());
//            double interestRate = loanService.getInterestRate(loanType);
//
//            // Validate loan amount against max loan
//            if (loanAmount > maxLoan) {
//                showAlert("Loan Application Failed", "You cannot borrow more than your maximum eligible loan.");
//                return;
//            }
//
//            // TODO: Add logic to handle guarantors' validation
//            // Assuming guarantors are pre-selected and validated for now
//            double guaranteedAmount = 0;
//            try {
//                guaranteedAmount = loanAmount - member.getShares();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//            try {
//                if (!loanService.validateGuarantors(guaranteedAmount, loanAmount, member.getShares())) {
//                    showAlert("Guarantor Validation Failed", "Guarantors must guarantee the loan.");
//                    return;
//                }
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            // Apply the loan using loan service
//            loanService.applyLoan(member.getMemberId(), loanType, loanAmount, repaymentPeriod,guaranteedAmount, interestRate);
//
//            showAlert("Loan Application Successful", "Loan of " + loanAmount + " has been applied successfully.");
//        });
//
//        // Add all components to the VBox
//        vbox.getChildren().addAll(loanTypeLabel, loanTypeComboBox, loanAmountLabel, loanAmountField,
//                repaymentLabel, repaymentField, sharesLabel, maxLoanLabel, applyButton);
//
//        return vbox;
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}





import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MemberLoansPanel {

    private final LoanService loanService = new LoanService();

    public TabPane createLoanPanel(Member member) throws SQLException {
        TabPane tabPane = new TabPane();

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

        return vbox;
    }

    private TableView<Loan> buildLoanTable(List<Loan> loans, Member member) {
        TableView<Loan> loanTable = new TableView<>(FXCollections.observableArrayList(loans));
        loanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Define columns for loan attributes
        TableColumn<Loan, String> loanIdColumn = new TableColumn<>("Loan ID");
        loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));

        TableColumn<Loan, String> loanTypeColumn = new TableColumn<>("Loan Type");
        loanTypeColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));

        TableColumn<Loan, Double> loanAmountColumn = new TableColumn<>("Loan Amount");
        loanAmountColumn.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));

        TableColumn<Loan, Integer> repaymentPeriodColumn = new TableColumn<>("Repayment Period (months)");
        repaymentPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("repaymentPeriod"));

        TableColumn<Loan, Double> interestRateColumn = new TableColumn<>("Interest Rate (%)");
        interestRateColumn.setCellValueFactory(new PropertyValueFactory<>("interestRate"));

        TableColumn<Loan, Double> guaranteedAmountColumn = new TableColumn<>("Guaranteed Amount");
        guaranteedAmountColumn.setCellValueFactory(new PropertyValueFactory<>("guaranteedAmount"));

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
                initiateCardPayment(loan);
            }
        });
    }

    private void initiateMpesaPayment(Loan loan, Member member) {
        // Implement STK push for Mpesa
        showAlert("Payment via Mpesa", "STK Push sent. Enter your pin to complete the transaction.");
    }

    private void initiateCardPayment(Loan loan) {
        // Show card payment dialog for user to enter card details
        showAlert("Payment via Card", "Enter card details to complete the transaction.");
    }

    private VBox createApplyLoanView(Member member) throws SQLException {
        VBox vbox = new VBox(10);

        // Components for loan application: loan type, amount, repayment period, guarantors
        ComboBox<String> loanTypeComboBox = new ComboBox<>(FXCollections.observableArrayList("Business", "Personal"));
        TextField loanAmountField = new TextField();
        TextField repaymentField = new TextField();
        Label maxLoanLabel = new Label("Maximum Loan: " + loanService.calculateMaxLoan(member));
        Button applyButton = new Button("Apply");

        applyButton.setOnAction(e -> applyForLoan(member, loanTypeComboBox.getValue(), loanAmountField.getText(), repaymentField.getText()));

        vbox.getChildren().addAll(new Label("Loan Type"), loanTypeComboBox, new Label("Amount"), loanAmountField,
                new Label("Repayment Period"), repaymentField, maxLoanLabel, applyButton);

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
    }

    private VBox createGuaranteeLoanView(Member member) {
        VBox vbox = new VBox(10);
        // List loans of others requiring guarantors, with buttons to guarantee

        return vbox;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}

