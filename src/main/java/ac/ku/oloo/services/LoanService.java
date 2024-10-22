package ac.ku.oloo.services;

import ac.ku.oloo.configs.LoadConfig;
import ac.ku.oloo.models.Member;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 22/10/2024. 08:13
 * Description:
 **/

public class LoanService {

    public static final double MAX_LOAN_MULTIPLIER = 3; // Maximum loan is 3 times the shares
    public static final double BUSINESS_LOAN_RATE = LoadConfig.getBusinessLoanInterestRate();
    public static final double EMERGENCY_LOAN_RATE = LoadConfig.getEmergencyLoanInterestRate();
    public static final double DEVELOPMENT_LOAN_RATE = 0.08; // 8% for Development Loan

    public double calculateMaxLoan(Member member) {
        return member.getShares() * MAX_LOAN_MULTIPLIER;
    }

    public double getInterestRate(String loanType) {
        switch (loanType) {
            case "Business Loan":
                return BUSINESS_LOAN_RATE;
            case "Emergency Loan":
                return EMERGENCY_LOAN_RATE;
            case "Development Loan":
                return DEVELOPMENT_LOAN_RATE;
            default:
                return 0;
        }
    }

    public boolean validateGuarantors(double guaranteedAmount, double loanAmount, double memberShares) {
        return guaranteedAmount >= (loanAmount - memberShares);
    }
}