package com.rbkmoney.test_dev_java.consumers.helpers;

import com.rbkmoney.test_dev_java.common.DataSource;
import com.rbkmoney.test_dev_java.common.Transaction;
import com.rbkmoney.test_dev_java.consumers.DAO.TransactionsJdbcDAO;

/** Класс, отвечающий за бизнес логику при взаимодействии с сущностью "Транзакции" БД */
//TODO: с наименованием трабл. Это по сути хэлпер, но это имплементация фасада
public class TransactionsJdbcHelper implements TransactionsFacade {

    /** Источник данных */
    private final DataSource dataSource;

    public TransactionsJdbcHelper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Transaction getTransaction(Integer id) throws Exception {
        return new TransactionsJdbcDAO(dataSource).getTransaction(id);
    }
}
