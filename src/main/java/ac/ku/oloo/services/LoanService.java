package ac.ku.oloo.services;

import ac.ku.oloo.configs.LoadConfig;
import ac.ku.oloo.models.Loan;
import ac.ku.oloo.models.Member;
import ac.ku.oloo.utils.databaseUtil.QueryExecutor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 22/10/2024. 08:13
 * Description:
 **/

public class LoanService {

    public double calculateMaxLoan(Member member, String loanType) throws SQLException {
        double multiplier;

        // Determine the multiplier based on the loan type
        multiplier = switch (loanType) {
            case "Emergency Loan" -> LoadConfig.getEmergencyLoanMultiplier();
            case "Short Loan" -> LoadConfig.getShortLoanMultiplier();
            case "Normal Loan" -> LoadConfig.getNormalLoanMultiplier();
            case "Development Loan" -> LoadConfig.getDevelopmentLoanMultiplier();
            default -> throw new IllegalArgumentException("Invalid loan type: " + loanType);
        };

        // Calculate the maximum loan
        double maxLoan = (member.getShares() * multiplier) - getUnpaidLoan(member.getMemberId());

        // Ensure the maximum loan is not negative
        return Math.max(maxLoan, 0);
    }

    private double getUnpaidLoan(int memberId) {
        AtomicReference<Double> totalUnpaidLoan = new AtomicReference<>(0.0);

        // SQL query to get all loans for the given memberId with status other than 'PAID'
        String query = "SELECT amount FROM loans WHERE member_id = ? AND status != 'PAID'";

        try {
            // Execute the query and accumulate the total of unpaid loans
            QueryExecutor.executeQuery(query, rs -> {
                while (rs.next()) {
                    // Accumulate loan amounts
                    totalUnpaidLoan.updateAndGet(v -> {
                        try {
                            return v + rs.getDouble("amount");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return null;
            }, new Object[] { memberId });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalUnpaidLoan.get();
    }

    // Method to get all applied loans for all Members
    public static List<Loan> getAllLoans() {
        String query = "SELECT * FROM loans";
        try {
            return QueryExecutor.executeQuery(query, rs -> new Loan(
                    rs.getLong("loan_id"),
                    rs.getInt("member_id"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getInt("repayment_period"),
                    rs.getDouble("interest_rate"),
                    rs.getDouble("guaranteed_amount"),
                    rs.getString("status"),
                    rs.getDate("date_created")
            ));
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list on error
        }
    }

    public double getInterestRate(String loanType) {
        return switch (loanType) {
            case "Emergency Loan" -> LoadConfig.getEmergencyLoanInterestRate();
            case "Short Loan" -> LoadConfig.getShortLoanInterestRate();
            case "Normal Loan" -> LoadConfig.getNormalLoanInterestRate();
            case "Development Loan" -> LoadConfig.getDevelopmentLoanInterestRate();
            default -> {
                System.err.println("Invalid loan type: " + loanType);
                yield 0; // Return 0 for invalid loan types
            }
        };
    }

    public boolean validateGuarantors(double guaranteedAmount, double loanAmount, double memberShares) {
        return guaranteedAmount >= (loanAmount - memberShares);
    }

    // Method to get all applied loans for a member
    public List<Loan> getAppliedLoans(int memberId) {
        String query = "SELECT * FROM loans WHERE member_id = ? AND status != 'PAID'";
        try {
            return QueryExecutor.executeQuery(query, rs -> new Loan(
                 rs.getLong("loan_id"),
                rs.getInt("member_id"),
                rs.getString("type"),
                rs.getDouble("amount"),
                rs.getInt("repayment_period"),
                rs.getDouble("interest_rate"),
                rs.getDouble("guaranteed_amount"),
                rs.getString("status")
            ), memberId);
        } catch (SQLException e) {
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
            e.printStackTrace();
        }
    }

    // Method to apply for a loan
    public void applyLoan(int memberId, String loanType, double loanAmount, int repaymentPeriod,double guaranteed_amount, double interestRate) {
        String query = "INSERT INTO loans (member_id, type, amount, repayment_period,guaranteed_amount, interest_rate, status) VALUES (?, ?, ?, ?, ?, ?, 'PENDING')";
        try {
            QueryExecutor.executeInsert(query, rs -> {
                if (rs.next()) {
                    return rs.getString(1);
                }
                return null;
            }, memberId, loanType, loanAmount, repaymentPeriod, guaranteed_amount, interestRate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Loan> getLoansToGuarantee(Member member) {
        String query = "SELECT loan_id, member_id, type, amount, repayment_period, guaranteed_amount, interest_rate, status " +
                "FROM loans " +
                "WHERE status = 'PENDING' AND member_id != ?";

        try {
            return QueryExecutor.executeQuery(query, rs -> new Loan(
                    rs.getLong("loan_id"),
                    rs.getInt("member_id"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getInt("repayment_period"),
                    rs.getDouble("interest_rate"),
                    rs.getDouble("guaranteed_amount"),
                    rs.getString("status")
            ), member.getMemberId());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean guaranteeLoan(Loan loan, Member guarantor, double amount) {
        String guarantorQuery = "INSERT INTO guarantors (loan_id, member_id, guarantee_amount) VALUES (?, ?, ?)";
        String updateLoanQuery = "UPDATE loans SET guaranteed_amount = guaranteed_amount + ? WHERE loan_id = ?";

        try {
            // Insert guarantor entry
            QueryExecutor.executeUpdate(guarantorQuery, loan.getLoanId(), guarantor.getMemberId(), amount);

            // Update loan guaranteed amount
            QueryExecutor.executeUpdate(updateLoanQuery, amount, loan.getLoanId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void updateLoanStatuses() {
        List<Loan> loans = getAllLoans(); // Fetch all loans
        Date currentDate = new Date();

        for (Loan loan : loans) {
            // Check if the guarantee is 0 (DISBURSED)
            if (loan.getGuaranteedAmount() == 0 && (!loan.getStatus().equals("PAID") || loan.getStatus().equals("OVERDUE"))) {
                loan.setStatus("DISBURSED");
                updateLoanInDatabase(loan);
            }

            // Check if the repayment period has passed and loan is not marked as PAID
            long monthsDifference = calculateMonthsDifference(loan.getLoanDate(), currentDate);
            if (monthsDifference > loan.getRepaymentPeriod() && !loan.getStatus().equals("PAID")) {
                loan.setStatus("OVERDUE");
                updateLoanInDatabase(loan);
            }


        }
    }

    private static void updateLoanInDatabase(Loan loan) {
        String updateLoanQuery = "UPDATE loans SET status = ? WHERE loan_id = ?";

        try {
            QueryExecutor.executeUpdate(updateLoanQuery,loan.getStatus(), loan.getLoanId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to calculate the months difference between two dates
    private static long calculateMonthsDifference(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / (1000L * 60 * 60 * 24 * 30); // Approximate month difference
    }


}