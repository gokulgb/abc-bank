package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//FIXME Ideally a nice OO design would be as follows:- Bank has a number of branches, each branch in turn has a manager
// and a number of customers, each customer in turn has a number of accounts.
public class Bank {

    private BankBranchInfo bankBranchInfo;
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public BankBranchInfo getBankBranchInfo() {
        return bankBranchInfo;
    }

    public void setBankBranchInfo(BankBranchInfo bankBranchInfo) {
        this.bankBranchInfo = bankBranchInfo;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public BigDecimal totalInterestPaid() {
        BigDecimal total = BigDecimal.ZERO;
        for(Customer c: customers)
            total = total.add(c.totalInterestEarned());
        return total;
    }

    //TODO What does this even mean? First based on what? Order of account opening? Order by customer name?
    public String getFirstCustomer() {
        if (null != customers && !customers.isEmpty()) {
            return customers.get(0).getName();
        } else {
            throw new RuntimeException("The bank has no customers!");
        }

    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
