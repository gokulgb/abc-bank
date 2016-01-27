package com.abc;

public class BankBranchInfo {

    private Manager manager;
    private String bankName;
    private String bankAddress; //TODO change these to suitable types in the future, keep it as simple strings for now.

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
}
