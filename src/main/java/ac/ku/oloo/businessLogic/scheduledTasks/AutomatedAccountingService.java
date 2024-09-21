package ac.ku.oloo.businessLogic.scheduledTasks;

import ac.ku.oloo.utils.databaseUtil.QueryExecutor;
import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.businessLogic.scheduledTasks)
 * Created by: oloo
 * On: 21/09/2024. 20:13
 * Description:
 **/

public class AutomatedAccountingService {

    private static final double INTEREST_RATE_FIXED_DEPOSIT = 0.006; // 0.6% monthly
    private static final double OFFICE_EXPENSES_PERCENTAGE = 0.10; // 10%

    public void updateAccounts() throws SQLException {
        updateInterestOnDeposits();
        updateLoanRepayments();
        handleDividends();
    }

    private void updateInterestOnDeposits() throws SQLException {
        // Get all fixed deposits
        String query = "SELECT deposit_id, member_id, amount FROM loans_deposits.deposits";
        List<Deposit> deposits = QueryExecutor.executeQuery(query, rs -> new Deposit(
                rs.getInt("deposit_id"),
                rs.getInt("member_id"),
                rs.getBigDecimal("amount")
        ));

        for (Deposit deposit : deposits) {
            double interest = deposit.getAmount().doubleValue() * INTEREST_RATE_FIXED_DEPOSIT;
            // Update deposit with interest (assuming a method to add interest)
            QueryExecutor.executeUpdate("UPDATE loans_deposits.deposits SET amount = amount + ? WHERE deposit_id = ?",
                    interest, deposit.getDepositId());
        }
    }

    private void updateLoanRepayments() throws SQLException {
        // Get all loans with repayments due
        String query = "SELECT loan_id, member_id, loan_amount FROM loans_deposits.loans WHERE status = 'ACTIVE'";
        List<Loan> loans = QueryExecutor.executeQuery(query, rs -> new Loan(
                rs.getInt("loan_id"),
                rs.getInt("member_id"),
                rs.getBigDecimal("loan_amount")
        ));

        for (Loan loan : loans) {
            double repaymentAmount = calculateMonthlyRepayment(loan);
            // Update the loan status or remaining amount (assuming a method for that)
            QueryExecutor.executeUpdate("UPDATE loans_deposits.loans SET loan_amount = loan_amount - ? WHERE loan_id = ?",
                    repaymentAmount, loan.getLoanId());
        }
    }

    private double calculateMonthlyRepayment(Loan loan) {
        // Implement logic to calculate monthly repayment based on loan details
        return loan.getLoanAmount().doubleValue() * 0.05; // Example: 5% of loan amount
    }

    private void handleDividends() throws SQLException {
        // Calculate total revenue for the year
        String query = "SELECT SUM(interest_income) as total_income FROM accounting.journal_entries WHERE transaction_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)";
        double totalRevenue = QueryExecutor.executeQuery(query, rs -> rs.getDouble("total_income")).get(0);

        double officeExpenses = totalRevenue * OFFICE_EXPENSES_PERCENTAGE;
        double dividendsPool = totalRevenue - officeExpenses;

        // Get members' shares for dividend calculation
        String sharesQuery = "SELECT member_id, SUM(amount) as total_shares FROM member_accounts.contributions GROUP BY member_id";
        List<Share> shares = QueryExecutor.executeQuery(sharesQuery, rs -> new Share(
                rs.getInt("member_id"),
                rs.getBigDecimal("total_shares")
        ));

        for (Share share : shares) {
            double memberDividend = (share.getTotalShares().doubleValue() / getTotalShares()) * dividendsPool;
            // Update member dividends (assuming a method to record dividends)
            QueryExecutor.executeUpdate("UPDATE member_accounts.members SET dividend = ? WHERE member_id = ?",
                    memberDividend, share.getMemberId());
        }
    }

    private double getTotalShares() throws SQLException {
        String query = "SELECT SUM(amount) as total_shares FROM member_accounts.contributions";
        return QueryExecutor.executeQuery(query, rs -> rs.getDouble("total_shares")).get(0);
    }
}
