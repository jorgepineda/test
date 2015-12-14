package com.landbay.loans.Exceptions;


import com.landbay.loans.model.Investment;
import com.landbay.loans.model.Loan;

import java.math.BigDecimal;

public class ExcesiveLendingAmount extends Exception  {
    public ExcesiveLendingAmount(Investment investment, Loan loan, BigDecimal amount) {
        super(investment.getInvestor() + "(id=" + investment.getId() +
                ") attempt to lend excesive amount " + amount + " to " + loan.getDebtor() + "(id="+ loan.getId() + ")");
    }
}
