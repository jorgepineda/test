package com.landbay.loans.Exceptions;

public class UnexistentLoan extends Exception {
    public UnexistentLoan(int loanId) {
        super("Could not find a loan with Id=" + loanId);
    }
}
