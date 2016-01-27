package com.abc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MaxiSavingsAccount extends Account {

    //TODO read the above from a properties file, for now, this is set as a constant
    private static final BigDecimal INTEREST_RATE_BELOW_THOUSAND = new BigDecimal(0.002);
    private static final BigDecimal INTEREST_RATE_ABOVE_ONE_THOUSAND = new BigDecimal(0.005);
    private static final BigDecimal INTEREST_RATE_ABOVE_TWO_THOUSAND = new BigDecimal(0.01);
    private static final BigDecimal ONE_THOUSAND = new BigDecimal(1000);
    private static final BigDecimal TWO_THOUSAND = new BigDecimal(2000);
    private static final Long NUM_OF_DAYS_IN_PAST = 10L;

    private static final BigDecimal INTEREST_RATE_IF_WITHDRAWAL_WAS_MADE_WITHIN_SPECIFIED_DAY_LIMIT = new BigDecimal(0.001);
    private static final BigDecimal INTEREST_RATE_IF_NO_WITHDRAWAL_WAS_MADE_WITHIN_SPECIFIED_DAY_LIMIT = new BigDecimal(0.05);

    //Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%

    public MaxiSavingsAccount() {
        this.setAccountType(AccountType.MAXI_SAVINGS);
    }


    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        BigDecimal amountWithInterest = BigDecimal.ZERO;
        Transaction lastRecordedTransaction = getLastTransaction();

        if (null != lastRecordedTransaction) {
            Date currentDate = DateProvider.getInstance().now();
            if (NUM_OF_DAYS_IN_PAST.compareTo(numberOfDaysBetweenTwoDates(currentDate, lastRecordedTransaction.getTransactionDate())) > 0) {
                // rate is 5%
                amountWithInterest = amount.multiply(INTEREST_RATE_IF_NO_WITHDRAWAL_WAS_MADE_WITHIN_SPECIFIED_DAY_LIMIT);
            } else {
                // rate is 0.1%
                amountWithInterest = amount.multiply(INTEREST_RATE_IF_WITHDRAWAL_WAS_MADE_WITHIN_SPECIFIED_DAY_LIMIT);
            }

        }
        // Maxi-Savings accounts have a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%
       /* if (ONE_THOUSAND.compareTo(amount) >= 0) {
            amountWithInterest = amount.multiply(INTEREST_RATE_BELOW_THOUSAND);
        } else if (ONE_THOUSAND.compareTo(amount) < 0 && TWO_THOUSAND.compareTo(amount) >= 0) {
            amountWithInterest = computeInterestForAmtBetweenOneAndTwoThousand(amount);
        } else {
            amountWithInterest = computeInterestForAmtGreaterThanTwoThousand(TWO_THOUSAND);
            amount = amount.subtract(TWO_THOUSAND);
            amountWithInterest = amountWithInterest.add(amount.multiply(INTEREST_RATE_ABOVE_TWO_THOUSAND));
        }*/
        return amountWithInterest.divide(NUM_OF_DAYS_PER_YEAR, ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    private long numberOfDaysBetweenTwoDates(Date date1, Date date2) {
        return Math.abs((date1.getTime()-date2.getTime())/CONSTANT);
    }

    private BigDecimal computeInterestForAmtGreaterThanTwoThousand(BigDecimal amount) {
        BigDecimal amountWithInterest = computeInterestForAmtBetweenOneAndTwoThousand(amount);
        BigDecimal modifiedAmount = amount.subtract(TWO_THOUSAND);
        amountWithInterest = amountWithInterest.add(modifiedAmount.multiply(INTEREST_RATE_ABOVE_TWO_THOUSAND));
        return amountWithInterest;
    }

    private BigDecimal computeInterestForAmtBetweenOneAndTwoThousand(BigDecimal amount) {
        BigDecimal amountWithInterest = ONE_THOUSAND.multiply(INTEREST_RATE_BELOW_THOUSAND);
        BigDecimal modifiedAmount = amount.subtract(ONE_THOUSAND);
        amountWithInterest = amountWithInterest.add(modifiedAmount.multiply(INTEREST_RATE_ABOVE_ONE_THOUSAND));
        return amountWithInterest;
    }

    // leave this here for now, can always refactor to parent class if need be.
    public Transaction getLastTransaction() {
        List<Transaction> transactions = getTransactions();
        if (transactions.size() > 0) {
            Collections.sort(transactions, new TransactionDateComparator());
            return transactions.get(transactions.size()-1);
        } else {
            return null;
        }

    }
}
