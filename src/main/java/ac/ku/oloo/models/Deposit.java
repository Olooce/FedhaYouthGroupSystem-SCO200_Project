package ac.ku.oloo.models;

import java.math.BigDecimal;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 21/09/2024. 20:18
 * Description:
 **/

public class Deposit {
    private final int depositId;
    private final int memberId;
    private final BigDecimal amount;

    public Deposit(int depositId, int memberId, BigDecimal amount) {
        this.depositId = depositId;
        this.memberId = memberId;
        this.amount = amount;
    }

    public int getDepositId() {
        return depositId;
    }

    public int getMemberId() {
        return memberId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

