package ac.ku.oloo.models;

import ac.ku.oloo.services.MemberService;

import java.sql.SQLException;
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
    private String name;
    private double guaranteeAmount;
    private LocalDateTime guaranteedAt;

    // Constructor
    public Guarantor(long guarantorId, long loanId, long memberId, double guaranteeAmount, LocalDateTime guaranteedAt) {
        this.guarantorId = guarantorId;
        this.loanId = loanId;
        this.memberId = memberId;
        this.guaranteeAmount = guaranteeAmount;
        this.guaranteedAt = guaranteedAt;
        try {
            this.name = MemberService.getMember(memberId).getFirstName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public String getName() {
        return name;
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
