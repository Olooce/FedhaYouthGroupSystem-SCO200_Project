package ac.ku.oloo.models;

import java.math.BigDecimal;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 21/09/2024. 20:20
 * Description:
 **/

public class Share {
    private int memberId;
    private BigDecimal totalShares;

    public Share(int memberId, BigDecimal totalShares) {
        this.memberId = memberId;
        this.totalShares = totalShares;
    }

    public int getMemberId() {
        return memberId;
    }

    public BigDecimal getTotalShares() {
        return totalShares;
    }
}
