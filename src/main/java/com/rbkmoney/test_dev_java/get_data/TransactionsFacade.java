package com.rbkmoney.test_dev_java.get_data;

import java.sql.SQLException;

/** Фасад для работы с существующими в хранилище транзакциями */
public interface TransactionsFacade {

    /**
     * Получение транзакции из источника по ИД
     *
     * @param id идентиикатор транзакции
     * @return в случае успешного поиска возвращает данные транзакции - иначе null
     */
    Transaction getTransaction(Integer id) throws SQLException;

}
