package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Loan;
import ac.ku.oloo.models.Member;
import ac.ku.oloo.services.LoanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:59
 * Description: Loans panel for viewing and applying for loans
 **/

public class MemberLoansPanel {

    private final LoanService loanService = new LoanService();

    public VBox createLoanPanel(Member member) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Section 1: Viewing and Paying Loans
        Label viewLoansLabel = new Label("Your Applied Loans");
        VBox appliedLoansBox = createAppliedLoansView(member);

        // Section 2: Applying for New Loans
        Label applyLoansLabel = new Label("Apply for a New Loan");
        VBox applyLoanBox = createApplyLoanView(member);

        // Add both sections to the VBox
        vbox.getChildren().addAll(viewLoansLabel, appliedLoansBox, applyLoansLabel, applyLoanBox);

        return vbox;
    }

    // Create a view for listing applied loans and handling payments
    private VBox createAppliedLoansView(Member member) {
        VBox vbox = new VBox(10);

        List<Loan> appliedLoans = loanService.getAppliedLoans(member.getMemberId());

        if (appliedLoans.isEmpty()) {
            Label noLoansLabel = new Label("You have no applied loans.");
            vbox.getChildren().add(noLoansLabel);
        } else {
            // Create TableView and define columns
            TableView<Loan> loanTable = new TableView<>();
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

            // Convert the appliedLoans list to an observable list and set it as the table data
            ObservableList<Loan> loanData = FXCollections.observableArrayList(appliedLoans);
            loanTable.setItems(loanData);

            // Add the TableView to the VBox
            vbox.getChildren().add(loanTable);
        }

        return vbox;
    }


    private HBox getLoanRow(Member member, Loan loan) {
        Label loanLabel = new Label("Loan ID: " + loan.getLoanId() + ", Amount: " + loan.getAmount() +
                ", Remaining: " + loan.getRemainingBalance());
        Button payButton = new Button("Pay Loan");

        // Handle loan payment
        payButton.setOnAction(e -> {
            loanService.payLoan(loan.getLoanId(), member.getMemberId());
            showAlert("Payment Successful", "You have successfully paid for the loan.");
        });

        return new HBox(10, loanLabel, payButton);
    }

    // Create a view for applying new loans
    private VBox createApplyLoanView(Member member) {
        VBox vbox = new VBox(10);

        // Dropdown for loan types
        Label loanTypeLabel = new Label("Select Loan Type:");
        ComboBox<String> loanTypeComboBox = new ComboBox<>();
        loanTypeComboBox.getItems().addAll("Business Loan", "Emergency Loan", "Personal Loan");

        // Input for loan amount
        Label loanAmountLabel = new Label("Enter Loan Amount:");
        TextField loanAmountField = new TextField();

        // Input for repayment period
        Label repaymentLabel = new Label("Enter Repayment Period (months):");
        TextField repaymentField = new TextField();

        // Display total shares for eligibility
        Label sharesLabel = new Label("Total Shares: " + member.getShares());

        // Maximum loan amount display
        double maxLoan = loanService.calculateMaxLoan(member);
        Label maxLoanLabel = new Label("Maximum Loan You Can Borrow: " + maxLoan);

        // Button to apply for loan
        Button applyButton = new Button("Apply for Loan");
        applyButton.setOnAction(e -> {
            // Get loan details
            String loanType = loanTypeComboBox.getValue();
            double loanAmount = Double.parseDouble(loanAmountField.getText());
            int repaymentPeriod = Integer.parseInt(repaymentField.getText());
            double interestRate = loanService.getInterestRate(loanType);

            // Validate loan amount against max loan
            if (loanAmount > maxLoan) {
                showAlert("Loan Application Failed", "You cannot borrow more than your maximum eligible loan.");
                return;
            }

            // TODO: Add logic to handle guarantors' validation
            // Assuming guarantors are pre-selected and validated for now
            double guaranteedAmount = loanAmount - member.getShares();
            if (!loanService.validateGuarantors(guaranteedAmount, loanAmount, member.getShares())) {
                showAlert("Guarantor Validation Failed", "Guarantors must guarantee the loan.");
                return;
            }

            // Apply the loan using loan service
            loanService.applyLoan(member.getMemberId(), loanType, loanAmount, repaymentPeriod,guaranteedAmount, interestRate);

            showAlert("Loan Application Successful", "Loan of " + loanAmount + " has been applied successfully.");
        });

        // Add all components to the VBox
        vbox.getChildren().addAll(loanTypeLabel, loanTypeComboBox, loanAmountLabel, loanAmountField,
                repaymentLabel, repaymentField, sharesLabel, maxLoanLabel, applyButton);

        return vbox;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
