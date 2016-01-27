package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(new BigDecimal(100.0));
        savingsAccount.deposit(new BigDecimal(4000.0));
        savingsAccount.withdraw(new BigDecimal(200.0));

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test(expected = RuntimeException.class)
    public void testTransferBetweenTwoAccountsForACustomerWhoHasInsufficientFunds(){
        Account a1 = new SavingsAccount();
        Account a2 = new CheckingAccount();
        a1.deposit(BigDecimal.ONE);
        Customer oscar = new Customer("Harry")
                .openAccount(a1);
        oscar.openAccount(a2);
        oscar.transferBetweenAccounts(a1, a2, BigDecimal.TEN);
    }

    @Test
    public void testTransferBetweenTwoAccountsForACustomerWithSufficientFunds(){
        Account a1 = new SavingsAccount();
        Account a2 = new CheckingAccount();
        a1.deposit(BigDecimal.TEN);
        Customer oscar = new Customer("Harry")
                .openAccount(a1);
        oscar.openAccount(a2);
        oscar.transferBetweenAccounts(a1, a2, BigDecimal.ONE);
        assertEquals("Check that account2 has balance of $1 post transfer", BigDecimal.ONE, a2.sumTransactions());
        assertEquals("Check that account1 has $9 remaining post transfer", new BigDecimal(9), a1.sumTransactions());
    }
}
