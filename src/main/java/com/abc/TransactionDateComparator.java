package com.abc;

import java.util.Comparator;
import java.util.Date;

public class TransactionDateComparator implements Comparator<Transaction> {

    @Override
    public int compare(Transaction tx1, Transaction tx2) {
        return tx1.getTransactionDate().compareTo(tx2.getTransactionDate());
    }
}
