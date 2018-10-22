package com.rbkmoney.test_dev_java.consumers.helpers;

import com.rbkmoney.test_dev_java.common.Transaction;

/** Фасад для работы с существующими в хранилище транзакциями */
public interface TransactionsFacade {

    /**
     * Получение транзакции из источника по ИД
     *
     * @param id идентиикатор транзакции
     * @return в случае успешного поиска возвращает данные транзакции - иначе null
     */
    Transaction getTransaction(Integer id) throws Exception;

}
