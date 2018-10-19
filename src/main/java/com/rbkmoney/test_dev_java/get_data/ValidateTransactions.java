package com.rbkmoney.test_dev_java.get_data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ValidateTransactions {

    /** Логгер */
    public static final Logger LOG = LoggerFactory.getLogger(ValidateTransactions.class);
    /** Фасад для работы с существующими в хранилище транзакциями */
    private TransactionsFacade transactionFacade;

    private ConcurrentLinkedQueue<Transaction> transactionQueue;

    private static final int THREAD_POOL_SIZE = 20;

    public ValidateTransactions(TransactionsFacade transactionFacade,
                                ConcurrentLinkedQueue<Transaction> transactionQueue) {
        this.transactionFacade = transactionFacade;
        this.transactionQueue = transactionQueue;
    }

    public List<Transaction> getInvalidTransactions() {
        LOG.info("Получение списка невалидных транзакций");
        List<Transaction> invalidTransactions = new ArrayList<>();
        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<Transaction>> invalidTransactionsFuture = new ArrayList<>();
        while(transactionQueue.size() > 0) {
            for (int i = 0; i < THREAD_POOL_SIZE && transactionQueue.peek() != null; i++) {
                Future<Transaction> invalidTransactionFuture = service.submit(() -> getInvalidTransaction(transactionQueue.poll()));
                invalidTransactionsFuture.add(invalidTransactionFuture);
            }
            for (Future<Transaction> transactionFuture : invalidTransactionsFuture) {
                try {
                    Transaction invalidTransaction = transactionFuture.get();
                    if (invalidTransaction != null) {
                        invalidTransactions.add(invalidTransaction);
                    }
                } catch (Exception e) {
                    LOG.error("Во сремя выполнения процесса поиска невалидный транзакций произошла ошибка: {}", e);
                }
            }
        }
        service.shutdown();
        return invalidTransactions;
    }

    /**
     * Валидация транзакции с существующими в системе
     *
     * @param transaction транзакция для проверки
     * @return В случае, если транзакии не существует в источнике или данные по сумме в транзакциях различаются
     *         будет возвращена сама транзакция с комментарием о проблеме. Иначе - null
     */
    public Transaction getInvalidTransaction(Transaction transaction) {
        LOG.debug("Валидация транзакции {}", transaction);
        try {
            Transaction sourceTransaction = transactionFacade.getTransaction(transaction.getId());
            if (sourceTransaction == null) {
                transaction.setComment("Транзакция не найдена");
                return transaction;
            } else if (transaction.getAmount() != sourceTransaction.getAmount()) {
                transaction.setComment("Разница по сумме транзакции (текущая транзакция " + transaction.getAmount() +
                        "; транзакция в источнике " + sourceTransaction.getAmount() +") ");
                return transaction;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOG.error("Во время валидации транзакции произошла ошибка: {}", e);
            return null;
        }
    }

}

