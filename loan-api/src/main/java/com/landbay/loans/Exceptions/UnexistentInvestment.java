package com.landbay.loans.Exceptions;

public class UnexistentInvestment extends Exception {
    public UnexistentInvestment(int investmentId) {
        super("Could not find an investment with Id=" + investmentId);
    }
}
