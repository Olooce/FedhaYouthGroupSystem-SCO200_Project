package ac.ku.oloo.models;

import java.time.LocalDateTime;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 22/10/2024. 08:22
 * Description:
 **/

public class Guarantor {
    private long guarantorId;
    private long loanId;
    private long memberId;
    private double guaranteeAmount;
    private LocalDateTime guaranteedAt;

    // Constructor
    public Guarantor(long guarantorId, long loanId, long memberId, double guaranteeAmount, LocalDateTime guaranteedAt) {
        this.guarantorId = guarantorId;
        this.loanId = loanId;
        this.memberId = memberId;
        this.guaranteeAmount = guaranteeAmount;
        this.guaranteedAt = guaranteedAt;
    }

      // Getters and Setters
    public long getGuarantorId() {
        return guarantorId;
    }

    public void setGuarantorId(long guarantorId) {
        this.guarantorId = guarantorId;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public double getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(double guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public LocalDateTime getGuaranteedAt() {
        return guaranteedAt;
    }

    public void setGuaranteedAt(LocalDateTime guaranteedAt) {
        this.guaranteedAt = guaranteedAt;
    }

    @Override
    public String toString() {
        return "Guarantor{" +
                "guarantorId=" + guarantorId +
                ", loanId=" + loanId +
                ", memberId=" + memberId +
                ", guaranteeAmount=" + guaranteeAmount +
                ", guaranteedAt=" + guaranteedAt +
                '}';
    }
}
