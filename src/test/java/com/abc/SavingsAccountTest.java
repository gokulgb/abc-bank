package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class SavingsAccountTest {

    private SavingsAccount testObj;

    @Before
    public void setUp() {
        testObj = new SavingsAccount();
    }

    @Test
    public void testInterestEarnedWhenTransactionsAreNull() throws Exception {
        BigDecimal expected = new BigDecimal(0.00).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("When Transactions are null, then expect 0 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWhenTransactionsAreEmpty() throws Exception {
        testObj.getTransactions().clear();
        BigDecimal expected = new BigDecimal(0.00).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("When Transactions are empty, then expect 0 interest to be earned", expected, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithOneTransactionLessThanOneThousand() throws Exception {
        BigDecimal amount1 = new BigDecimal(20);
        Transaction tx1 = new Transaction(amount1);
        testObj.getTransactions().add(tx1);
        BigDecimal expectedInterest = new BigDecimal(0.00005).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00005 interest to be earned", expectedInterest, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithTwoTransactionsLessThanOneThousand() throws Exception {
        BigDecimal amount1 = new BigDecimal(20);
        BigDecimal amount2 = new BigDecimal(30);
        Transaction tx1 = new Transaction(amount1);
        Transaction tx2 = new Transaction(amount2);
        testObj.getTransactions().add(tx1);
        testObj.getTransactions().add(tx2);
        BigDecimal expectedInterest = new BigDecimal(0.00014).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00014 interest to be earned", expectedInterest, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithOneTransactionMoreThanOneThousand() throws Exception {
        BigDecimal amount1 = new BigDecimal(1200);
        Transaction tx1 = new Transaction(amount1);
        testObj.getTransactions().add(tx1);
        BigDecimal expectedInterest = new BigDecimal(0.00384).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00384 interest to be earned", expectedInterest, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithOneTransactionOfExactlyOneThousand() throws Exception {
        BigDecimal amount = new BigDecimal(1000);
        Transaction tx1 = new Transaction(amount);
        testObj.getTransactions().add(tx1);
        BigDecimal expectedInterest = new BigDecimal(0.00274).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00274 interest to be earned", expectedInterest, testObj.interestEarned());
    }

    @Test
    public void testInterestEarnedWithOneTransactionOfOneThousandAndOne() throws Exception {
        BigDecimal amount = new BigDecimal(1001);
        Transaction tx1 = new Transaction(amount);
        testObj.getTransactions().add(tx1);
        BigDecimal expectedInterest = new BigDecimal(0.00275).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("With one transaction, expect 0.00275 interest to be earned", expectedInterest, testObj.interestEarned());
    }
}