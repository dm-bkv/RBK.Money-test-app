package com.rbkmoney.test_dev_java.consumers.DAO;

import com.rbkmoney.test_dev_java.common.DAO;
import com.rbkmoney.test_dev_java.common.DataSource;
import com.rbkmoney.test_dev_java.common.Transaction;

import java.sql.SQLException;

/**
 * Абстрактный класс для описания списка операций над транзакциями
 *
 * Примечание: предполагаю, что может быть несколько вариантов получения данных, поэтому ввел данную абстакцию
 */
public abstract class TransactionsDAO extends DAO {

    public TransactionsDAO(DataSource dataSource) throws Exception {
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
