package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Member;
import ac.ku.oloo.services.LoanService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:59
 * Description: Loans panel for viewing and applying for loans
 **/

public class MemberLoansPanel {

    private final LoanService loanService = new LoanService(); // Initialize the loan service

    public VBox createLoanPanel(Member member) {
        VBox vbox = new VBox();
        Label label = new Label("Loans");

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

            // Successful loan application (TODO: add more details to this)
            showAlert("Loan Application Successful", "Loan of " + loanAmount + " has been applied successfully.");
        });

        // Add all components to the VBox
        vbox.getChildren().addAll(label, loanTypeLabel, loanTypeComboBox, loanAmountLabel, loanAmountField,
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


