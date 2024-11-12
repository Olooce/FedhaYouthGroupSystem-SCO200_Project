package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Loan;
import ac.ku.oloo.models.Member;
import ac.ku.oloo.services.LoanService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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
            for (Loan loan : appliedLoans) {
                HBox loanRow = getLoanRow(member, loan);
                vbox.getChildren().add(loanRow);
            }
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
