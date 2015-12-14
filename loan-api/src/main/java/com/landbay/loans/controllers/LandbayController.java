package com.landbay.loans.controllers;

import com.landbay.loans.model.Investment;
import com.landbay.loans.model.Loan;
import com.landbay.loans.services.LandbayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class LandbayController {
    final static Logger logger = Logger.getLogger(LandbayController.class.getName());
    private SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    LandbayService landbayService;

    @RequestMapping(value="/test", method= RequestMethod.GET)
    public @ResponseBody String test() {
        System.out.println("TEST OK");
        return "Test OK";
    }

    @RequestMapping(value="/create-loan/{debtor}/{amount}/{interest}", method= RequestMethod.GET)
    public @ResponseBody int createLoan(@PathVariable String debtor, @PathVariable BigDecimal amount, @PathVariable BigDecimal interest) {
        return landbayService.createLoan(debtor,amount,interest);
    }

    @RequestMapping(value="/create-investment/{investor}/{amount}", method= RequestMethod.GET)
    public @ResponseBody int createInvestment(@PathVariable String investor, @PathVariable BigDecimal amount) {
        return landbayService.createInvesment(investor,amount);
    }

    @RequestMapping(value="/add-lending/{loanId}/{investmentId}/{amount}/{effectiveDate}", method= RequestMethod.GET)
    public @ResponseBody void addLending(@PathVariable int loanId, @PathVariable int investmentId, @PathVariable BigDecimal amount, @PathVariable String effectiveDate) {
        try {
            landbayService.addInvestmentToLoan(loanId,investmentId, amount, sdf.parse(effectiveDate));
        } catch(Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }

    }

    @RequestMapping(value="/pay/{loanId}/{investmentId}/amount", method= RequestMethod.GET)
    public @ResponseBody void makePayment(@PathVariable int loanId,@PathVariable int investmentId, @PathVariable BigDecimal amount) {
        try {
            landbayService.makePayment(loanId,investmentId, amount);
        } catch(Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
    }

    @RequestMapping(value="/loan/{loanId}", method= RequestMethod.GET)
    public @ResponseBody Loan getLoan(@PathVariable int loanId) {
        try {
            Loan loan = landbayService.getLoan(loanId);
            if (loan != null) {
                logger.info(loan.toString());
            }
            return loan;
        } catch(Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value="/investments/{loanId}", method= RequestMethod.GET)
    public @ResponseBody List<Investment> getInvestmentsForLoan(@PathVariable int loanId) {
        try {
            return landbayService.getInvestmentsForLoan(loanId);
        } catch(Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value="/interests-report/{startDate}/{endDate}", method= RequestMethod.GET)
    public @ResponseBody String interestReport(@PathVariable String startDate,@PathVariable String endDate) {
        try {
            return landbayService.getInterestReport(sdf.parse(startDate),sdf.parse(endDate));
        } catch(Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
        return null;
    }


}
