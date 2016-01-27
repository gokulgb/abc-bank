package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account {

    //TODO read the above from a properties file, for now, this is set as a constant
    private BigDecimal rateOfInterest = new BigDecimal(0.001);

    public CheckingAccount() {
        setAccountType(AccountType.CHECKING);
    }

    public BigDecimal interestEarned() {
        return sumTransactions().multiply(rateOfInterest).divide(NUM_OF_DAYS_PER_YEAR, ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
    }
}
