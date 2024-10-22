package ac.ku.oloo.userInterfaceFX.panels;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:59
 * Description:
 **/

public class MemberLoansPanel {

    public VBox createLoanPanel() {
        VBox vbox = new VBox();
        Label label = new Label("Loans");

        // Dropdown for loan types
        Label loanTypeLabel = new Label("Select Loan Type:");
        ComboBox<String> loanTypeComboBox = new ComboBox<>();
        loanTypeComboBox.getItems().addAll("Business Loan", "Emergency Loan", "Development Loan");

        // Input for loan amount
        Label loanAmountLabel = new Label("Enter Loan Amount:");
        TextField loanAmountField = new TextField();

        // Input for repayment period
        Label repaymentLabel = new Label("Enter Repayment Period (months):");
        TextField repaymentField = new TextField();

        // Display total shares for eligibility
        Label sharesLabel = new Label("Total Shares: 0 (Auto-calculate)");
        // Will be updated based on member data

        // Button to apply for loan
        Button applyButton = new Button("Apply for Loan");
        applyButton.setOnAction(e -> {
            // TODO: Add logic to handle loan application
            // e.g., check shares, update member's loan info, show eligibility
        });

        // Add all components to the VBox
        vbox.getChildren().addAll(label, loanTypeLabel, loanTypeComboBox, loanAmountLabel, loanAmountField,
                repaymentLabel, repaymentField, sharesLabel, applyButton);

        // TODO: Add loan-specific UI components (e.g., guarantors, loan conditions)

        return vbox;
    }
}

