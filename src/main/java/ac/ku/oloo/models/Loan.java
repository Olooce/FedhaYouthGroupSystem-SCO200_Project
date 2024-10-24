package ac.ku.oloo.models;

import java.math.BigDecimal;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 21/09/2024. 20:19
 * Description:
 **/

public class Loan {
    private long loanId;
    private String type;
    private double amount;
    private int repaymentPeriod; // In months
    private double interestRate;
    private double guaranteedAmount;
    private int memberId;
    private String status;

    public Loan(long loanId, int memberId, String loanType, double loanAmount, int repaymentPeriod, double interestRate, double guaranteedAmount, String status) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.type = loanType;
        this.amount = loanAmount;
        this.repaymentPeriod = repaymentPeriod;
        this.interestRate = interestRate;
        this.guaranteedAmount = guaranteedAmount;
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public int getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getGuaranteedAmount() {
        return guaranteedAmount;
    }

    public String getLoanId() {
        return String.valueOf(loanId);
    }

    public String getRemainingBalance() {
        return String.valueOf(amount);
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setLoanType(String loanType) {
        this.type = loanType;
    }

    public void setLoanAmount(double loanAmount) {
        this.amount = loanAmount;
    }

    public void setRepaymentPeriod(int repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGuaranteedAmount(double guaranteedAmount) {
        this.guaranteedAmount = guaranteedAmount;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getLoanType() {
        return type;
    }

    public Object getLoanAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}

