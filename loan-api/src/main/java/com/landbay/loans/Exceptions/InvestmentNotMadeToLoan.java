package com.landbay.loans.Exceptions;

import com.landbay.loans.model.Investment;
import com.landbay.loans.model.Loan;

public class InvestmentNotMadeToLoan extends Exception {
    public InvestmentNotMadeToLoan(Investment investment, Loan loan) {
        super("Inverstor " +investment.getInvestor() +"(id=" + investment.getId() + ") has no lend money to debtor " + loan.getDebtor() + " (Id=" + loan.getId() + ")");
    }

}
