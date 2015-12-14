package com.landbay.loans.Exceptions;

import com.landbay.loans.model.Investment;

public class InvestmentWithLoans extends Exception{
    public InvestmentWithLoans(Investment investment) {
        super("Cannot remove investment " + investment.getId() + " because it still has " + investment.getLendings().size() + " loans associated");
    }
}
