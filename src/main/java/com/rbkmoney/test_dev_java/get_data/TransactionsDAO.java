package com.rbkmoney.test_dev_java.get_data;

import java.math.BigInteger;

public class TransactionsDAO {

    public TransactionsDAO() {

    }

    private static final String GET_TRANSACTION = "SELECT id, amount FROM rbk.transactions WHERE id = ? ";

    public Transaction getTransaction(BigInteger id) {
        Transaction tran = new Transaction();

        return tran;
    }




}
