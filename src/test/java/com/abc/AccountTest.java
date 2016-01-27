package com.abc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {

    private Account savingsAccount;
    private Account baseAccount;
    private Account checkingAccount;
    private Account maxiSavingsAccount;

    @Before
    public void setUp() throws Exception {
        savingsAccount = new SavingsAccount();
        baseAccount = new Account();
        checkingAccount = new CheckingAccount();
        maxiSavingsAccount = new MaxiSavingsAccount();
    }

    @After
    public void tearDown() throws Exception {
        savingsAccount = null;
        baseAccount = null;
        checkingAccount = null;
        maxiSavingsAccount = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositIfAmountIsNull() throws Exception {
        baseAccount.deposit(null);
        assertEquals("Expect to see 0 transactions as the amount deposited is invalid", 0, baseAccount.getTransactions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositIfAmountIsZero() throws Exception {
        baseAccount.deposit(BigDecimal.ZERO);
        assertEquals("Expect to see 0 transactions as the amount deposited is invalid", 0, baseAccount.getTransactions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositIfAmountIsLessThanZero() throws Exception {
        baseAccount.deposit(new BigDecimal(-5));
        assertEquals("Expect to see 0 transactions as the amount deposited is invalid", 0, baseAccount.getTransactions().size());
    }

    @Test
    public void testDepositIfAmountIsAValidAmount() throws Exception {
        baseAccount.deposit(BigDecimal.TEN);
        assertEquals("Expect to see 1 transaction as the amount deposited is invalid", 1, baseAccount.getTransactions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawIfAmountIsNull() throws Exception {
        BigDecimal beforeAmt = baseAccount.sumTransactions();
        baseAccount.withdraw(null);
        BigDecimal afterAmt = baseAccount.sumTransactions();
        assertEquals("Check that no withdrawal was made as the amount specified was invalid", beforeAmt, afterAmt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawIfAmountIsZero() throws Exception {
        BigDecimal beforeAmt = baseAccount.sumTransactions();
        baseAccount.withdraw(BigDecimal.ZERO);
        BigDecimal afterAmt = baseAccount.sumTransactions();
        assertEquals("Check that no withdrawal was made as the amount specified was invalid", beforeAmt, afterAmt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawIfAmountIsLessThanZero() throws Exception {
        BigDecimal beforeAmt = baseAccount.sumTransactions();
        baseAccount.withdraw(new BigDecimal(-5));
        BigDecimal afterAmt = baseAccount.sumTransactions();
        assertEquals("Check that no withdrawal was made as the amount specified was invalid", beforeAmt, afterAmt);
    }

    @Test(expected = RuntimeException.class)
    public void testWithdrawIfInsufficientFundsAreAvailable() throws Exception {
        BigDecimal beforeAmt = baseAccount.sumTransactions();
        baseAccount.withdraw(BigDecimal.TEN);
        BigDecimal afterAmt = baseAccount.sumTransactions();
        assertEquals("Check that an exception is thrown due to insufficient funds", beforeAmt, afterAmt);
    }

    @Test(expected = RuntimeException.class)
    public void testWithdrawIfDepositIsLesserThanRequestedWithdrawalAmount() throws Exception {
        BigDecimal beforeAmt = baseAccount.sumTransactions();
        baseAccount.deposit(BigDecimal.ONE);
        baseAccount.withdraw(BigDecimal.TEN);
        BigDecimal afterAmt = baseAccount.sumTransactions();
        assertEquals("Check that an exception is thrown due to insufficient funds", beforeAmt, afterAmt);
    }

    @Test
    public void testInterestEarned() throws Exception {

    }

    @Test
    public void testSumTransactions() throws Exception {
        baseAccount.deposit(BigDecimal.TEN);
        baseAccount.deposit(BigDecimal.TEN);
        BigDecimal expectedAmount = new BigDecimal(20);
        assertEquals("Check that 20 is returned as the sum of transactions", expectedAmount, baseAccount.sumTransactions());
    }

    @Test
    public void testSumTransactionsIfDepositAndWithdrawalAreOfSameAmount() throws Exception {
        baseAccount.deposit(BigDecimal.TEN);
        baseAccount.withdraw(BigDecimal.TEN);
        assertEquals("Check that the sum of transactions is zero", BigDecimal.ZERO, baseAccount.sumTransactions());
    }

    @Test
    public void testSumTransactionsIfDepositIsGreaterThanWithdrawl() throws Exception {
        baseAccount.deposit(BigDecimal.TEN);
        baseAccount.withdraw(new BigDecimal(5));
        BigDecimal expectedAmount = new BigDecimal(5);
        assertEquals("Check that 5 is returned as the sum of transactions", expectedAmount, baseAccount.sumTransactions());
    }

    @Test
    public void testGetAccountTypeForCheckingAccount() throws Exception {
        assertEquals(AccountType.CHECKING, checkingAccount.getAccountType());
    }

    @Test
    public void testGetAccountTypeForSavingsAccount() throws Exception {
        assertEquals(AccountType.SAVINGS, savingsAccount.getAccountType());
    }

    @Test
    public void testGetAccountTypeForMaxiSavingsAccount() throws Exception {
        assertEquals(AccountType.MAXI_SAVINGS, maxiSavingsAccount.getAccountType());
    }

    @Test
    public void testGetAccountType() throws Exception {
        assertNull("Base Account has no account type associated with it, so expect null to be returned.", baseAccount.getAccountType());
    }

    @Test
    public void testGetTransactions() throws Exception {
        int numOfTransactionsBeforeDeposit = checkingAccount.getTransactions().size();
        checkingAccount.deposit(BigDecimal.TEN);
        int numOfTransactionsAfterDeposit = checkingAccount.getTransactions().size();
        assertEquals("Check that the number of transactions have gone up by 1", 1, (numOfTransactionsAfterDeposit - numOfTransactionsBeforeDeposit));
    }
}