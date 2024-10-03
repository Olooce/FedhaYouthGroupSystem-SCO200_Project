package ac.ku.oloo.models;

import java.math.BigDecimal;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 21/09/2024. 20:19
 * Description:
 **/

public class Loan {
    private final int loanId;
    private final int memberId;
    private final BigDecimal loanAmount;

    public Loan(int loanId, int memberId, BigDecimal loanAmount) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.loanAmount = loanAmount;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getMemberId() {
        return memberId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }
}
