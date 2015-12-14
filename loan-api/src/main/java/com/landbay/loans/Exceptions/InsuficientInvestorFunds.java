package com.landbay.loans.Exceptions;


import com.landbay.loans.model.Investment;
import com.landbay.loans.model.Loan;

import java.math.BigDecimal;

public class InsuficientInvestorFunds extends Exception{
    public InsuficientInvestorFunds(Investment investment, Loan loan, BigDecimal amount) {
        super("Insuficient funds from investor " + investment.getInvestor() + "(id=" + investment.getId() +
                ") to lend " + amount + " to " + loan.getDebtor() + "(id="+ loan.getId() + ")");
    }
}
