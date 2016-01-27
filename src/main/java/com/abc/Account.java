package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {

    private AccountType accountType;
    private List<Transaction> transactions;
    private Long accountNumber;
    public static final BigDecimal NUM_OF_DAYS_PER_YEAR= new BigDecimal("365");
    public static final Integer ROUNDING_SCALE =5;
    public static final Integer CONSTANT = 1000 * 60 * 60 * 24;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
        Random random = new Random(0L);
        setAccountNumber(random.nextLong());
    }

    public void deposit(BigDecimal amount) {
        if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(BigDecimal amount) {
        if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            BigDecimal currAccountBalance = sumTransactions();
            if (currAccountBalance.compareTo(amount) >= 0) {
                transactions.add(new Transaction(amount.negate()));
            } else {
                throw new RuntimeException("Unable to withdraw from account due to insufficient funds!");
            }
        }
    }

    public BigDecimal interestEarned() {
        throw new RuntimeException("Unable to work out interest earned as ambiguous account type specified!");
    }

    public BigDecimal sumTransactions() {
       return checkIfTransactionsExist();
    }

    private BigDecimal checkIfTransactionsExist() {
        BigDecimal amount = BigDecimal.ZERO;
        for (Transaction t: transactions) {
            amount = amount.add(t.amount);
        }
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
