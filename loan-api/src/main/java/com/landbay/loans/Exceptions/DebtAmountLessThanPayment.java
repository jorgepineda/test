package com.landbay.loans.Exceptions;

import com.landbay.loans.model.Investment;
import com.landbay.loans.model.Loan;

import java.math.BigDecimal;

public class DebtAmountLessThanPayment extends Exception{
    public DebtAmountLessThanPayment(Investment investment, Loan loan, BigDecimal amount) {
        super(loan.getDebtor() + "(id="+ loan.getId() +
                ") attempt to pay excesive amount " + amount + " to " + investment.getInvestor() + "(id=" + investment.getId() + ")");
    }
}
