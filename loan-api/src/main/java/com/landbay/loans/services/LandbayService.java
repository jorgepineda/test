package com.landbay.loans.services;

import com.landbay.loans.Exceptions.*;
import com.landbay.loans.model.Investment;
import com.landbay.loans.model.Lending;
import com.landbay.loans.model.Loan;
import com.landbay.loans.repository.InvestmentRepository;
import com.landbay.loans.repository.LendingRepository;
import com.landbay.loans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LandbayService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    LendingRepository lendingRepository;

    public int createLoan(String debtor, BigDecimal amount, BigDecimal interest) {
        Loan loan = new Loan();
        loan.setDebtor(debtor);
        loan.setRequiredAmount(amount);
        loan.setLendedAmount(BigDecimal.ZERO);
        loan.setInterest(interest);
        loanRepository.save(loan);
        return loan.getId();
    }

    public int createInvesment(String investor, BigDecimal amount) {
        Investment investment = new Investment();
        investment.setInvestor(investor);
        investment.setInitialAmount(amount);
        investment.setRemainingAmount(amount);
        investmentRepository.save(investment);
        return investment.getId();
    }

    public void addInvestmentToLoan(int investmentId, int loanId, BigDecimal amount, Date startingDate) throws UnexistentInvestment,UnexistentLoan,InsuficientInvestorFunds,ExcesiveLendingAmount {
        Investment investment = investmentRepository.findOne(investmentId);
        if (investment == null) {throw new UnexistentInvestment(investmentId);}
        Loan loan = loanRepository.findOne(loanId);
        if (loan == null) {throw new  UnexistentLoan(loanId);}
        if (investment.getRemainingAmount().doubleValue() < amount.doubleValue()) {
            throw new InsuficientInvestorFunds(investment,loan,amount);
        } else
        if (loan.getRequiredAmount().doubleValue() < amount.doubleValue()) {
            throw new ExcesiveLendingAmount(investment,loan,amount);
        } else {
            Lending lending = new Lending();
            lending.setInvestment(investment);
            lending.setLoan(loan);
            lending.setAmount(amount);
            lending.setStartingDate(startingDate);
            lending.setLendingPrimaryKey(new Lending.LendingPrimaryKey(loan.getId(),investment.getId()));
            investment.setRemainingAmount(investment.getRemainingAmount().subtract(amount));
            loan.setLendedAmount(loan.getLendedAmount().add(amount));
            loan.setRequiredAmount(loan.getRequiredAmount().subtract(amount));
            lendingRepository.save(lending);
            loanRepository.save(loan);
            investmentRepository.save(investment);
        }
    }

    public void deleteLoan(int loanId) throws UnexistentLoan, LoanWithInvestments{
        Loan loan = loanRepository.findOne(loanId);
        if (loan == null) {throw new UnexistentLoan(loanId);}
        if (loan.getLendings() == null || loan.getLendings().isEmpty()) {
            loanRepository.delete(loan);
        } else {
            throw new LoanWithInvestments(loan);
        }
    }

    public void deleteInvestment(int investmentId) throws UnexistentInvestment,InvestmentWithLoans {
        Investment investment = investmentRepository.findOne(investmentId);
        if (investment == null) {throw new UnexistentInvestment(investmentId);}
        if (investment.getLendings() == null || investment.getLendings().isEmpty()) {
            investmentRepository.delete(investment);
        } else {
            throw new InvestmentWithLoans(investment);
        }
    }

    public void makePayment(int loanId, int investmentId, BigDecimal amount) throws UnexistentInvestment, UnexistentLoan, InvestmentNotMadeToLoan, DebtAmountLessThanPayment {
        Investment investment = investmentRepository.findOne(investmentId);
        if (investment == null) {throw new UnexistentInvestment(investmentId);}
        Loan loan = loanRepository.findOne(loanId);
        if (loan == null) {throw new  UnexistentLoan(loanId);}
        Lending lending = lendingRepository.findOne(new Lending.LendingPrimaryKey(loan.getId(), investment.getId()));
        if (lending == null) {
            throw new InvestmentNotMadeToLoan(investment,loan);
        } else
        if (lending.getAmount().doubleValue() < amount.doubleValue()) {
            throw new DebtAmountLessThanPayment(investment, loan, amount);
        } else {
            investment.setRemainingAmount(investment.getRemainingAmount().add(amount));
            loan.setLendedAmount(loan.getLendedAmount().subtract(amount));
            lending.setAmount(lending.getAmount().subtract(amount));
            if (lending.getAmount().doubleValue() == 0.0d) {
                // The lending has beebn fully paid
                lendingRepository.delete(lending);
            } else {
                lendingRepository.save(lending);
            }
            loanRepository.save(loan);
            investmentRepository.save(investment);
        }
    }

    public Loan  getLoan(int loanId) {
        return loanRepository.findOne(loanId);
    }

    public List<Investment> getInvestmentsForLoan(int loanId) throws UnexistentLoan {
        Loan loan = loanRepository.findOne(loanId);
        if (loan == null) {
            throw new  UnexistentLoan(loanId);
        } else
        if (loan.getLendings() != null) {
            List<Investment> investments = new ArrayList<Investment>();
            for (Lending lending : loan.getLendings()) {
                investments.add(lending.getInvestment());
            }
            return investments;
        }
        return null;
    }

    private long calcualteNumDays(Date fromDate, Date toDate) {
        long delta = toDate.getTime() - fromDate.getTime();
        return TimeUnit.DAYS.convert(delta,TimeUnit.MILLISECONDS);
    }

    public String getInterestReport(Date fromDate, Date toDate) {
        String interestReport  = "InvestmentId   Investor   Loan Id   Debtor   Interest\n";
               interestReport += "------------   --------   -------   ------   --------\n";
        // loops ove all investments and Lendings
        for (Investment investment : investmentRepository.findAll()) {
            for (Lending lending :  investment.getLendings()) {
                long numDays = 0;
                if (lending.getStartingDate().compareTo(fromDate) <= 0) {
                    numDays = calcualteNumDays(fromDate, toDate);
                } else {
                    numDays = calcualteNumDays(fromDate, toDate);
                }
                double interestAmount = 0;
                double interestRate = 0;
                if (numDays > 0) {
                    interestRate = lending.getLoan().getInterest().doubleValue() * numDays / 36500d;
                }
                interestAmount = lending.getAmount().doubleValue() * interestRate;
                interestReport += investment.getId() + "   " + investment.getInvestor() + "  " + lending.getLoan().getId() + "   " +
                                  lending.getLoan().getDebtor() + "   " + interestAmount;
            }
        }
        return interestReport;
    }
}
