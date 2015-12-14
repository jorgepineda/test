package com.landbay.loans.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name=Lending.TABLE_NAME)
public class Lending {

    public static final String TABLE_NAME = "LENDING";

    @EmbeddedId
    LendingPrimaryKey lendingPrimaryKey;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="LOAN_ID", nullable=false, insertable=false, updatable=false)
    private Loan loan;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="INVESTMENT_ID", nullable=false, insertable=false, updatable=false)
    private Investment investment;

    @Column(name="AMOUNT")
    BigDecimal amount;

    @Column(name="STARTING_DATE")
    Date startingDate;

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Investment getInvestment() {
        return investment;
    }

    public void setInvestment(Investment investment) {
        this.investment = investment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    @Embeddable
    public static class LendingPrimaryKey implements Serializable {

        @Column(name="LOAN_ID")
        private Integer loandId;

        @Column(name="INVESTMENT_ID")
        private Integer investmentId;

        public LendingPrimaryKey() {}

        public LendingPrimaryKey(Integer loanId,Integer investmentId) {
            this.loandId = loanId;
            this.investmentId = investmentId;
        }

    }

    public LendingPrimaryKey getLendingPrimaryKey() {
        return lendingPrimaryKey;
    }

    public void setLendingPrimaryKey(LendingPrimaryKey lendingPrimaryKey) {
        this.lendingPrimaryKey = lendingPrimaryKey;
    }
}
