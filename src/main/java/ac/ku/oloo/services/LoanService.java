package ac.ku.oloo.services;

import ac.ku.oloo.configs.LoadConfig;
import ac.ku.oloo.models.Loan;
import ac.ku.oloo.models.Member;
import ac.ku.oloo.utils.databaseUtil.QueryExecutor;

import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 22/10/2024. 08:13
 * Description:
 **/

public class LoanService {

    public static final double MAX_LOAN_MULTIPLIER = 3;
    public static final double BUSINESS_LOAN_RATE = LoadConfig.getBusinessLoanInterestRate();
    public static final double EMERGENCY_LOAN_RATE = LoadConfig.getEmergencyLoanInterestRate();
    public static final double PERSONAL_LOAN_RATE = LoadConfig.getPersonalLoanInterestRate();

    public double calculateMaxLoan(Member member) {
        return member.getShares() * MAX_LOAN_MULTIPLIER;
    }

    public double getInterestRate(String loanType) {
        return switch (loanType) {
            case "Business Loan" -> BUSINESS_LOAN_RATE;
            case "Emergency Loan" -> EMERGENCY_LOAN_RATE;
            case "Personal Loan" -> PERSONAL_LOAN_RATE;
            default -> 0;
        };
    }

    public boolean validateGuarantors(double guaranteedAmount, double loanAmount, double memberShares) {
        return guaranteedAmount >= (loanAmount - memberShares);
    }

    // Method to get all applied loans for a member
    public List<Loan> getAppliedLoans(int memberId) {
        String query = "SELECT * FROM loans WHERE member_id = ?";
        try {
            return QueryExecutor.executeQuery(query, rs -> {
                Loan loan = new Loan();
                loan.setLoanId(rs.getLong("loan_id"));
                loan.setMemberId(rs.getInt("member_id"));
                loan.setLoanType(rs.getString("loan_type"));
                loan.setLoanAmount(rs.getDouble("loan_amount"));
                loan.setRepaymentPeriod(rs.getInt("repayment_period"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setStatus(rs.getString("status"));
                return loan;
            }, memberId);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list on error
        }
    }

    // Method to pay a loan
    public void payLoan(String loanId, int memberId) {
        String query = "UPDATE loans SET status = 'PAID' WHERE loan_id = ? AND member_id = ?";
        try {
            QueryExecutor.executeUpdate(query, loanId, memberId);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it
            e.printStackTrace();
        }
    }

    // Method to apply for a loan
    public void applyLoan(int memberId, String loanType, double loanAmount, int repaymentPeriod, double interestRate) {
        String query = "INSERT INTO loans (member_id, loan_type, loan_amount, repayment_period, interest_rate, status) VALUES (?, ?, ?, ?, ?, 'PENDING')";
        try {
            QueryExecutor.executeInsert(query, rs -> {
                if (rs.next()) {
                    // Assuming the loan ID is auto-generated and returned
                    return rs.getString(1); // return the generated loan ID if needed
                }
                return null; // Handle if no keys are returned
            }, memberId, loanType, loanAmount, repaymentPeriod, interestRate);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it
            e.printStackTrace();
        }
    }
}