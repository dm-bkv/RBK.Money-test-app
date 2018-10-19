package com.rbkmoney.test_dev_java.get_data;

import java.sql.SQLException;

/** Класс, отвечающий за бизнес логику при взаимодействии с сущностью "Транзакции" БД */
public class TransactionsDbHelper implements TransactionsFacade {

    /** Источник данных */
    private final DataSource dataSource;

    public TransactionsDbHelper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Transaction getTransaction(Integer id) throws SQLException {
        return new TransactionsJdbcDAO(dataSource).getTransaction(id);
    }
}
