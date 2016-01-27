package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CheckingAccountTest {

    private CheckingAccount testObj;

    @Before
    public void setup() {
        testObj = new CheckingAccount();
    }

    @Test
    public void testInterestEarnedWhenTransactionsAreNull() throws Exception {
        BigDecimal expected = BigDecimal.ZERO.setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("When Transactions are null, then expect 0 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWhenTransactionsAreEmpty() throws Exception {
        testObj.getTransactions().clear();
        BigDecimal expected = BigDecimal.ZERO.setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("When Transactions are empty, then expect 0 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithOneTransaction() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(20));
        testObj.getTransactions().add(tx1);
        BigDecimal expected = new BigDecimal(0.00005).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00005 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithTwoTransactions() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(20));
        Transaction tx2 = new Transaction(new BigDecimal(30));
        testObj.getTransactions().add(tx1);
        testObj.getTransactions().add(tx2);
        BigDecimal expected = new BigDecimal(0.00014).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00014 interest to be earned", expected, testObj.interestEarned());
    }
}