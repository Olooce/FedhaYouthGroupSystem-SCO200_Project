package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Loan;
import ac.ku.oloo.services.LoanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

        if (loans.isEmpty()) {
            Label noLoansLabel = new Label("No loans available.");
            vbox.getChildren().add(noLoansLabel);
        } else {
            // Create TableView and set up columns
            TableView<Loan> loanTable = new TableView<>();
            loanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // Define columns
            TableColumn<Loan, String> loanIdColumn = new TableColumn<>("Loan ID");
            loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));

            TableColumn<Loan, String> memberIdColumn = new TableColumn<>("Member ID");
            memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));

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
                    loanIdColumn, memberIdColumn, loanTypeColumn, loanAmountColumn,
                    repaymentPeriodColumn, interestRateColumn, guaranteedAmountColumn, statusColumn
            );

            // Convert loans list to observable list and set it as table data
            ObservableList<Loan> loanData = FXCollections.observableArrayList(loans);
            loanTable.setItems(loanData);

            // Add the TableView to the VBox
            vbox.getChildren().add(loanTable);
        }

        return vbox;
    }

}
