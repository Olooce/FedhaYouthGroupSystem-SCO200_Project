//package ac.ku.oloo.businessLogic.scheduledTasks;
//
//import ac.ku.oloo.utils.databaseUtil.QueryExecutor;
//import static ac.ku.oloo.configs.LoadConfig.*;
//import ac.ku.oloo.models.*;
//import java.sql.SQLException;
//import java.util.List;
//
///**
// * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.businessLogic.scheduledTasks)
// * Created by: oloo
// * On: 21/09/2024. 20:13
// * Description: Handles accounting tasks that are "non-event driven" (for lack of a better word).
// **/
//
//public class AutomatedAccountingService {
//
//    private static final double INTEREST_RATE_FIXED_DEPOSIT = getFixedDepositInterestRate();
//    private static final double OFFICE_EXPENSES_PERCENTAGE = getOfficeExpensesPercentage();
//
//    public void updateAccounts() throws SQLException {
//        updateInterestOnDeposits();
//        updateLoanRepayments();
//        handleDividends();
//    }
//
//    private void updateInterestOnDeposits() throws SQLException {
//        // Get all fixed deposits
//        String query = "SELECT deposit_id, member_id, amount FROM loans_deposits.deposits";
//        List<Deposit> deposits = QueryExecutor.executeQuery(query, rs -> new Deposit(
//                rs.getInt("deposit_id"),
//                rs.getInt("member_id"),
//                rs.getBigDecimal("amount")
//        ));
//
//        for (Deposit deposit : deposits) {
//            double interest = deposit.getAmount().doubleValue() * INTEREST_RATE_FIXED_DEPOSIT;
//            QueryExecutor.executeUpdate("UPDATE loans_deposits.deposits SET amount = amount + ? WHERE deposit_id = ?",
//                    interest, deposit.getDepositId());
//        }
//    }
//
//    private void updateLoanRepayments() throws SQLException {
//        String query = "SELECT loan_id, member_id, loan_amount FROM loans_deposits.loans WHERE status = 'ACTIVE'";
//        List<Loan> loans = QueryExecutor.executeQuery(query, rs -> new Loan(
//                rs.getInt("loan_id"),
//                rs.getInt("member_id"),
//                rs.getBigDecimal("loan_amount")
//        ));
//
//        for (Loan loan : loans) {
//            double repaymentAmount = calculateMonthlyRepayment(loan);
//            QueryExecutor.executeUpdate("UPDATE loans_deposits.loans SET loan_amount = loan_amount - ? WHERE loan_id = ?",
//                    repaymentAmount, loan.getLoanId());
//        }
//    }
//
//    private double calculateMonthlyRepayment(Loan loan) {
//        // TODO: Implement logic to calculate monthly repayment based on loan details
//        return loan.getLoanAmount().doubleValue() * 0.05;
//    }
//
//    private void handleDividends() throws SQLException {
//        // Calculate total revenue for the year
//        String query = "SELECT SUM(interest_income) as total_income FROM accounting.journal_entries WHERE transaction_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)";
//        double totalRevenue = QueryExecutor.executeQuery(query, rs -> rs.getDouble("total_income")).get(0);
//
//        double officeExpenses = totalRevenue * OFFICE_EXPENSES_PERCENTAGE;
//        double dividendsPool = totalRevenue - officeExpenses;
//
//        // Get members' shares for dividend calculation
//        String sharesQuery = "SELECT member_id, SUM(amount) as total_shares FROM member_accounts.contributions GROUP BY member_id";
//        List<Share> shares = QueryExecutor.executeQuery(sharesQuery, rs -> new Share(
//                rs.getInt("member_id"),
//                rs.getBigDecimal("total_shares")
//        ));
//
//        for (Share share : shares) {
//            double memberDividend = (share.getTotalShares().doubleValue() / getTotalShares()) * dividendsPool;
//            QueryExecutor.executeUpdate("UPDATE member_accounts.members SET dividend = ? WHERE member_id = ?",
//                    memberDividend, share.getMemberId());
//        }
//    }
//
//    private double getTotalShares() throws SQLException {
//        String query = "SELECT SUM(amount) as total_shares FROM member_accounts.contributions";
//        return QueryExecutor.executeQuery(query, rs -> rs.getDouble("total_shares")).get(0);
//    }
//}
