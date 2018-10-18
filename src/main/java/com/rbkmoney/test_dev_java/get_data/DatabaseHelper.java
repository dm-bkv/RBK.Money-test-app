package com.rbkmoney.test_dev_java.get_data;

import java.math.BigInteger;

public class DatabaseHelper implements TransactionsFacade {


    @Override
    public Transaction getTransaction(BigInteger id) {
        TransactionsDAO dao = new TransactionsDAO();

        return dao.getTransaction(id);
    }


}
