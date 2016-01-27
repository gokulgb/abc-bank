package com.abc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private Bank bank;

    @Before
    public void setUp() {
        bank = new Bank();
    }

    @After
    public void tearDown() {
        bank = null;
    }

    @Test
    public void customerSummary() {
        Customer john = new Customer("John");
        john.openAccount(new  CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(new BigDecimal(100.0));
        BigDecimal expected = new BigDecimal(0.00027).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);

        assertEquals(expected, bank.totalInterestPaid());
    }

    @Test
    public void savings_account() {
        Account checkingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(new BigDecimal(1500.0));
        BigDecimal expected = new BigDecimal(0.00548).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);

        assertEquals(expected, bank.totalInterestPaid());
    }

    @Test
    public void maxi_savings_account() {
        Account checkingAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(new BigDecimal(3000.0));
        BigDecimal expected = new BigDecimal(0.41096).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);

        assertEquals(expected, bank.totalInterestPaid());
    }

    @Test(expected = RuntimeException.class)
    public void getFirstCustomerWhenCustomerListIsNull() {
        assertEquals("Error", bank.getFirstCustomer());
    }

    @Test(expected = RuntimeException.class)
    public void getFirstCustomerWhenCustomerListIsEmpty() {
        assertEquals("Error", bank.getFirstCustomer());
    }

    @Test
    public void getCustomerSummaryWithoutAnyBankCustomers() {
        String expected = "Customer Summary";
        String customerSummary = bank.customerSummary();
        assertEquals("Check that the summaries match", expected, customerSummary);
    }

    @Test
    public void getCustomerSummaryWhenBankHasOneCustomerWithoutAnyAccounts() {
        Customer c1 = new Customer("John");
        String expected = "Customer Summary\n - John (0 accounts)";
        bank.addCustomer(c1);
        String customerSummary = bank.customerSummary();
        assertEquals("Check that the summaries match", expected, customerSummary);
    }

    @Test
    public void getCustomerSummaryWhenBankHasOneCustomerWithOneAccount() {
        Customer c1 = new Customer("John");
        Account a1 = new CheckingAccount();
        c1.openAccount(a1);
        String expected = "Customer Summary\n - John (1 account)";
        bank.addCustomer(c1);
        String customerSummary = bank.customerSummary();
        assertEquals("Check that the summaries match", expected, customerSummary);
    }

    @Test
    public void getCustomerSummaryWhenBankHasTwoCustomersWithDifferentAccounts() {
        Customer c1 = new Customer("John");
        Customer c2 = new Customer("Doe");

        Account a1 = new CheckingAccount();
        Account a2 = new SavingsAccount();
        Account a3 = new MaxiSavingsAccount();

        c1.openAccount(a1);
        c2.openAccount(a2);
        c2.openAccount(a3);

        bank.addCustomer(c1);
        bank.addCustomer(c2);

        String expected = "Customer Summary\n - John (1 account)\n - Doe (2 accounts)";
        String customerSummary = bank.customerSummary();
        assertEquals("Check that the summaries match", expected, customerSummary);
    }

    @Test
    public void getTotalInterestPaidWhenBankHasNoCustomers() {
        assertEquals("Check that no interest has been paid when bank has no customers", BigDecimal.ZERO, bank.totalInterestPaid());
    }

    @Test
    public void getTotalInterestPaidWhenBankHasOneCustomerWithoutAnyAccounts() {
        //TODO weird scenario to check for! Get clarification from business!
        Customer c1 = new Customer("John");
        bank.addCustomer(c1);
        assertEquals("Check that no interest has been paid when bank has no customers", BigDecimal.ZERO, bank.totalInterestPaid());
    }

    @Test
    public void getTotalInterestPaidWhenBankHasOneCustomerWhoHasTwoAccounts() {
        Customer c1 = new Customer("John");
        Account a1 = new CheckingAccount();
        Account a2 = new SavingsAccount();
        a1.deposit(BigDecimal.ONE);
        a2.deposit(BigDecimal.TEN);
        c1.openAccount(a1);
        c1.openAccount(a2);
        bank.addCustomer(c1);
        BigDecimal expected = new BigDecimal(0.00003).setScale(Account.ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP);
        assertEquals("Check that interest has been paid", expected, bank.totalInterestPaid());
    }
}
