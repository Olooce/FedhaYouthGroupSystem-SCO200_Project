package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Member;
import ac.ku.oloo.services.DepositService;
import ac.ku.oloo.services.MemberService;
import ac.ku.oloo.services.LoanService;
import ac.ku.oloo.services.ShareService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 09/12/2024. 23:35
 * Description:
 **/

public class ReportsPanel {

    public TabPane createReportsPanel() {
        TabPane tabPane = new TabPane();

        // Tab for Shares per Member
        Tab sharesTab = new Tab("Member Dividends", createSharesTabContent());
        sharesTab.setClosable(false);

        // Tab for Revenue Summary
        Tab revenueTab = new Tab("Revenue Summary", createRevenueTabContent());
        revenueTab.setClosable(false);

        // Add tabs to the TabPane
        tabPane.getTabs().addAll(sharesTab, revenueTab);
        return tabPane;
    }

    private VBox createSharesTabContent() {
        VBox sharesTabContent = new VBox();
        TableView<Member> sharesTable = new TableView<>();

        // Columns
        TableColumn<Member, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> data.getValue().getFullName());

        TableColumn<Member, Double> sharesColumn = new TableColumn<>("Shares");
        sharesColumn.setCellValueFactory(data -> data.getValue().getOShares());

        TableColumn<Member, Double> dividendsColumn = new TableColumn<>("Dividends Owed");
        dividendsColumn.setCellValueFactory(cellData -> {
            Member member = cellData.getValue();
            double memberShares = 0;
            try {
                memberShares = member.getShares();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            double dividendsOwed = calculateDividendsOwed(memberShares);
            return new SimpleDoubleProperty(dividendsOwed).asObject();
        });

        sharesTable.getColumns().addAll(nameColumn, sharesColumn, dividendsColumn);

        // Populate data
        try {
            List<Member> members = MemberService.getMembers(1, 100); // Fetch members with shares
            ObservableList<Member> memberList = FXCollections.observableArrayList(members);
            sharesTable.setItems(memberList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sharesTabContent.getChildren().add(sharesTable);
        return sharesTabContent;
    }

    private VBox createRevenueTabContent() {
        VBox revenueTabContent = new VBox();
        revenueTabContent.setSpacing(10);

        // Labels to display revenue data
        Label totalRegistrationFees = new Label("Total Registration Fees: " + MemberService.getTotalRegistrationFees());
        Label totalSharesLabel = new Label();
        Label totalLoansLabel = new Label();
        Label interestEarnedLabel = new Label();
        Label fixedDepositInterestLabel = new Label();
        Label totalRevenueLabel = new Label();
        Label dividendsLabel = new Label();
        Label officeExpensesLabel = new Label();
        Label fixedDepositLabel = new Label();

        // Fetch data
        double totalShares = ShareService.getAllTotalShares();
        double totalLoans = LoanService.getTotalLoans();
        double interestEarned = LoanService.getTotalLoanInterest();
        double totalDeposits = DepositService.getAllTotalDeposits();
        double fixedDepositInterest = (totalDeposits - totalLoans) * 0.006;

        // Populate labels
        totalSharesLabel.setText("Total Shares: " + String.format("%.2f", totalShares));
        totalLoansLabel.setText("Total Loans Given: " + String.format("%.2f", totalLoans));
        interestEarnedLabel.setText("Interest Earned on Loans: " + String.format("%.2f", interestEarned));
        fixedDepositLabel.setText("Fixed Deposits: " + String.format("%.2f", (totalDeposits - totalLoans)));
        fixedDepositInterestLabel.setText("Interest Earned on Fixed Deposits: " + String.format("%.2f", fixedDepositInterest));
        totalRevenueLabel.setText("Total Interest Revenue: " + String.format("%.2f", interestEarned + fixedDepositInterest));
        officeExpensesLabel.setText("Reserved amount: " + String.format("%.2f", (interestEarned + fixedDepositInterest) * 0.1));
        dividendsLabel.setText("Total dividends: " + String.format("%.2f", (interestEarned + fixedDepositInterest) * 0.9));


        revenueTabContent.getChildren().addAll(
                totalRegistrationFees,
                totalSharesLabel,
                totalLoansLabel,
                interestEarnedLabel,
                fixedDepositLabel,
                fixedDepositInterestLabel,
                totalRevenueLabel,
                officeExpensesLabel,
                dividendsLabel
        );
        return revenueTabContent;
    }


    public double calculateDividendsOwed(double memberShares) {
        double totalLoans = LoanService.getTotalLoans();
        double interestEarned = LoanService.getTotalLoanInterest();
        double totalDeposits = DepositService.getAllTotalDeposits();
        double fixedDepositInterest = (totalDeposits - totalLoans) * 0.006;
        double totalShares = ShareService.getAllTotalShares();
        double totalDividends = (interestEarned + fixedDepositInterest) * 0.9;
        return (memberShares / totalShares) * totalDividends;
    }
}
