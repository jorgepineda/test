package com.landbay.loans.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name=Loan.TABLE_NAME)
public class Loan {

    public static final String TABLE_NAME = "LOAN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="DEBTOR")
    private String debtor;

    @Column(name="REQUIRED_AMOUNT")
    private BigDecimal requiredAmount;

    @Column(name="LENDED_AMOUNT")
    private BigDecimal lendedAmount;

    @Column(name="INTEREST")
    private BigDecimal interest;

    @OneToMany(mappedBy="loan")
    private List<Lending> lendings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public BigDecimal getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(BigDecimal requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public BigDecimal getLendedAmount() {
        return lendedAmount;
    }

    public void setLendedAmount(BigDecimal lendedAmount) {
        this.lendedAmount = lendedAmount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public List<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }

    @Override
    public String toString() {
        return "Id=>" + this.id +"\n" +
                "Debtor=>" + this.debtor +"\n" +
                "RequiredAmount=>" + this.getRequiredAmount() + "\n" +
                "LendedAmount=>" + this.lendedAmount + "\n" +
                "Interest=>" + this.interest;
    }
}
