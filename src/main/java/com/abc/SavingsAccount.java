package com.abc;

import java.math.BigDecimal;

public class SavingsAccount extends Account {

    //TODO eventually read this from a properties file

    private static final BigDecimal INTEREST_RATE_BELOW_THOUSAND = new BigDecimal(0.001);
    private static final BigDecimal INTEREST_RATE_ABOVE_THOUSAND = new BigDecimal(0.002);
    private static final BigDecimal ONE_THOUSAND = new BigDecimal(1000);

    public SavingsAccount() {
        this.setAccountType(AccountType.SAVINGS);
    }

    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        BigDecimal interestEarned = BigDecimal.ZERO;
        if (ONE_THOUSAND.compareTo(amount) >= 0) {
            interestEarned = amount.multiply(INTEREST_RATE_BELOW_THOUSAND);
        } else {
            amount = amount.subtract(ONE_THOUSAND);
            amount = amount.multiply(INTEREST_RATE_ABOVE_THOUSAND);
            interestEarned = ONE_THOUSAND.multiply(INTEREST_RATE_BELOW_THOUSAND);
            interestEarned = interestEarned.add(amount);
        }
        return interestEarned.divide(NUM_OF_DAYS_PER_YEAR, ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
    }
}
