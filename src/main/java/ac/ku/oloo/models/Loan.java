package ac.ku.oloo.models;

import java.math.BigDecimal;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 21/09/2024. 20:19
 * Description:
 **/

public class Loan {
    private final long loadId;
    private final String type;
    private final double amount;
    private final int repaymentPeriod; // In months
    private final double interestRate;
    private final double guaranteedAmount;
    
    public Loan(long loadId, String type, double amount, int repaymentPeriod, double interestRate, double guaranteedAmount) {
        this.loadId = loadId;
        this.type = type;
        this.amount = amount;
        this.repaymentPeriod = repaymentPeriod;
        this.interestRate = interestRate;
        this.guaranteedAmount = guaranteedAmount;
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
        return String.valueOf(loadId);
    }

    public String getRemainingBalance() {
        return String.valueOf(amount);
    }
}

