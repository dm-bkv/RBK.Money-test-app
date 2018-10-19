package com.rbkmoney.test_dev_java.get_data;

import java.sql.SQLException;

/** Абстрактный класс для описания списка */
//TODO: спорный момент, т.к. в различных ситуациях может работать по-разному. Надо подумать
public abstract class TransactionsDAO extends DAO {

    public TransactionsDAO(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Получение транзакции из источника по ИД
     *
     * @param id идентиикатор транзакции
     * @return в случае успешного поиска возвращает данные по транзакции4 иначе null
     */
    public abstract Transaction getTransaction(Integer id) throws SQLException;
}
