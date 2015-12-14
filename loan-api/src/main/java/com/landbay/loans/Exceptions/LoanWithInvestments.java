package com.landbay.loans.Exceptions;

import com.landbay.loans.model.Loan;

public class LoanWithInvestments extends Exception {
    public LoanWithInvestments(Loan loan) {
        super("Cannot remove loan " + loan.getId() + " because it still has " + loan.getLendings().size() + " investments");
    }
}
