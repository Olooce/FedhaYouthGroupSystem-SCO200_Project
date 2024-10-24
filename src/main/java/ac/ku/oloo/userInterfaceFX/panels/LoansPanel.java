package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Loan;
import ac.ku.oloo.services.LoanService;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX)
 * Created by: oloo
 * On: 26/09/2024. 13:19
 * Description:
 **/


public class LoansPanel {

    public VBox createLoanPanel() {
        VBox vbox = new VBox(10);  // Vertical box with 10px spacing
        Label titleLabel = new Label("Loans");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        vbox.getChildren().add(titleLabel);

        // Retrieve all loans
        List<Loan> loans = LoanService.getAllLoans();

        // If no loans are found, show a message
        if (loans.isEmpty()) {
            Label noLoansLabel = new Label("No loans available.");
            vbox.getChildren().add(noLoansLabel);
        } else {
            // Display each loan's details
            for (Loan loan : loans) {
                VBox loanBox = new VBox(5); // A box for each loan with 5px spacing between elements
                loanBox.setStyle("-fx-padding: 10px; -fx-border-color: #ccc; -fx-border-radius: 5px;");

                // Create labels for each loan attribute
                Label loanIdLabel = new Label("Loan ID: " + loan.getLoanId());
                Label memberIdLabel = new Label("Member ID: " + loan.getMemberId());
                Label loanTypeLabel = new Label("Loan Type: " + loan.getLoanType());
                Label loanAmountLabel = new Label(String.format("Loan Amount: %.2f", loan.getLoanAmount()));
                Label repaymentPeriodLabel = new Label("Repayment Period: " + loan.getRepaymentPeriod() + " months");
                Label interestRateLabel = new Label(String.format("Interest Rate: %.2f%%", loan.getInterestRate()));
                Label guaranteedAmountLabel = new Label(String.format("Guaranteed Amount: %.2f", loan.getGuaranteedAmount()));
                Label statusLabel = new Label("Status: " + loan.getStatus());

                // Add loan details to the loanBox
                loanBox.getChildren().addAll(
                        loanIdLabel, memberIdLabel, loanTypeLabel, loanAmountLabel,
                        repaymentPeriodLabel, interestRateLabel, guaranteedAmountLabel, statusLabel
                );

                // Add the loanBox to the main vbox
                vbox.getChildren().add(loanBox);
            }
        }

        return vbox;
    }

}
