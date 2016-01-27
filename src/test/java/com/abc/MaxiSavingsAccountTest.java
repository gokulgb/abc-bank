package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class MaxiSavingsAccountTest {

    private MaxiSavingsAccount testObj;

    @Before
    public void setup() {
        testObj = new MaxiSavingsAccount();
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
    public void testInterestEarnedWithOneTransactionMadeMoreThanTenDaysAgo() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(20));
        Date txDate = DateProvider.getInstance().now();
        txDate.setYear(txDate.getYear() - 1);
        tx1.setTransactionDate(txDate);
        testObj.getTransactions().add(tx1);
        BigDecimal expected = new BigDecimal(0.00005).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00005 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithOneTransactionMadeLessThanTenDaysAgo() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(20));
        Date txDate = DateProvider.getInstance().now();
        txDate.setDate(txDate.getDate() - 5);
        tx1.setTransactionDate(txDate);
        testObj.getTransactions().add(tx1);
        BigDecimal expected = new BigDecimal(0.00274).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00274 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithTwoTransactions() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(20));
        Transaction tx2 = new Transaction(new BigDecimal(30));
        testObj.getTransactions().add(tx1);
        testObj.getTransactions().add(tx2);
        BigDecimal expected = new BigDecimal(0.00685).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00685 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithOneTransactionBetweenOneAndTwoThousand() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(1200));
        testObj.getTransactions().add(tx1);
        BigDecimal expected = new BigDecimal(0.16438).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.16438 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithTwoTransactionsBetweenOneAndTwoThousand() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(1200));
        Transaction tx2 = new Transaction(new BigDecimal(300));
        testObj.getTransactions().add(tx1);
        testObj.getTransactions().add(tx2);
        BigDecimal expected = new BigDecimal(0.20548).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.20548 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithOneTransactionAboveTwoThousand() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(2200));
        testObj.getTransactions().add(tx1);
        BigDecimal expected = new BigDecimal(0.30137).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.30137 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithTwoTransactionsAboveThousand() throws Exception {
        Transaction tx1 = new Transaction(new BigDecimal(1200));
        Transaction tx2 = new Transaction(new BigDecimal(1300));
        testObj.getTransactions().add(tx1);
        testObj.getTransactions().add(tx2);
        BigDecimal expected = new BigDecimal(0.34247).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.34247 interest to be earned", expected, testObj.interestEarned());
    }

}