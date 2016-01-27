package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer extends Person {

    public static final String NEW_LINE_CHAR = "\n";
    private List<Account> accounts;

    public Customer(String name) {
        super(name);
        this.accounts = new ArrayList<>();
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts)
            total = total.add(a.interestEarned());
        return total;
    }

    public String getStatement() {
        StringBuilder stringBuilder = new StringBuilder("Statement for ");
        stringBuilder.append(getName()).append(NEW_LINE_CHAR);
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts) {
            stringBuilder.append(NEW_LINE_CHAR).append(statementForAccount(a)).append(NEW_LINE_CHAR);
            total = total.add(a.sumTransactions());
        }
        stringBuilder.append("\nTotal In All Accounts ").append(toDollars(total));
        return stringBuilder.toString();
    }

    private String statementForAccount(Account a) {
        StringBuilder stringBuilder = new StringBuilder("");

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                stringBuilder.append("Checking Account\n");
                break;
            case SAVINGS:
                stringBuilder.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                stringBuilder.append("Maxi Savings Account\n");
                break;
            default:
                throw new RuntimeException("Unknown Account type encountered!");
        }

        //Now total up all the transactions
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : a.getTransactions()) {
            stringBuilder.append("  ").append((t.amount.compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit"))
                    .append(" ").append(toDollars(t.amount)).append(NEW_LINE_CHAR);
            total = total.add(t.amount);
        }
        stringBuilder.append("Total ").append(toDollars(total));
        return stringBuilder.toString();
    }

    private String toDollars(BigDecimal d){
        return String.format("$%,.2f", abs(d.doubleValue()));
    }

    public void transferBetweenAccounts(Account fromAccount, Account toAccount, BigDecimal amount) {
        if(fromAccount.sumTransactions().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient funds available to transfer between accounts");
        }
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
}
