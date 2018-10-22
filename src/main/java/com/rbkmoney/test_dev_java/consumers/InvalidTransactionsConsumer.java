package com.rbkmoney.test_dev_java.consumers;

import com.rbkmoney.test_dev_java.commands.Command;
import com.rbkmoney.test_dev_java.common.Transaction;
import com.rbkmoney.test_dev_java.common.TransportQueue;
import com.rbkmoney.test_dev_java.consumers.helpers.TransactionsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *  Класс-потребитель, отвечающий за обработку входного списка транзакций, получение из него невалидных и
 *  обработку результирующего списка
 */
public class InvalidTransactionsConsumer implements Consumer {

    /** Логгер */
    private static final Logger LOG = LoggerFactory.getLogger(InvalidTransactionsConsumer.class);
    /** Фасад для работы с существующими в хранилище транзакциями */
    private TransactionsFacade transactionFacade;
    /** Очередь с транзакциями для обработки */
    private TransportQueue transportQueue;
    /** Список невалидных транзакций*/
    private List<Transaction> invalidTransactions;
    /** Команда для обработки результата */
    private Command command;
    /** Пул потоков */
    private ExecutorService service;

    /** Размер пула параллельно обрабатываемых транзакций */
    private static final int THREAD_POOL_SIZE = 20;
    /** Время ожидания до след. попытки записать данные в очередь в случае, когда она переполнена */
    private static final int QUEUE_TIMEOUT = 1000;

    public InvalidTransactionsConsumer(TransactionsFacade transactionFacade,
                                       TransportQueue transportQueue,
                                       List<Transaction> invalidTransactions,
                                       Command command,
                                       ExecutorService service) {
        this.transactionFacade = transactionFacade;
        this.transportQueue = transportQueue;
        this.invalidTransactions = invalidTransactions;
        this.command = command;
        this.service = service;
    }

    @Override
    public void consume()  {
        getInvalidTransactions(invalidTransactions);
        try {
            command.execute();
        } catch (Exception e) {
            LOG.error("Произошла ошбибка во время выполнения обработки полученных данных: {}", e);
        }
    }

    /**
     * Получение списка невалидных транзакций
     *
     * @param invalidTransactions исходный список невалидных транзакций
     */
    public void getInvalidTransactions(List<Transaction> invalidTransactions) {
        LOG.info("Получение списка невалидных транзакций");
        BlockingQueue<Transaction> transactionQueue = transportQueue.getQueue();

        while(transportQueue.isFilling() || transactionQueue.size() > 0) {

            List<Future<Transaction>> invalidTransactionsFuture = new ArrayList<>();
            for (int i = 0; i < THREAD_POOL_SIZE && transactionQueue.peek() != null; i++) {
                try {
                    Transaction transaction = transactionQueue.poll(QUEUE_TIMEOUT, TimeUnit.MILLISECONDS);
                    if (transaction == null) {
                        continue;
                    }
                    String currentThreadName = Thread.currentThread().getName();
                    invalidTransactionsFuture.add(service.submit(() -> getInvalidTransaction(transaction, currentThreadName)));
                } catch (InterruptedException e) {
                    LOG.warn("Во время выполнения потока поиска транзакции в хранилище произошла ошибка: {}", e);
                }
            }
            for (Future<Transaction> transactionFuture : invalidTransactionsFuture) {
                try {
                    Transaction invalidTransaction = transactionFuture.get();
                    if (invalidTransaction != null && invalidTransaction.isNotEmpty()) {
                        invalidTransactions.add(invalidTransaction);
                    }
                } catch (Exception e) {
                    LOG.error("Во время выполнения процесса поиска невалидный транзакций произошла ошибка: {}", e);
                }
            }
        }
    }

    /**
     * Валидация транзакции с существующими в системе
     *
     * @param transaction транзакция для поиска
     * @param currentThreadName наименование робительского потока
     * @return В случае, если транзакии не существует в источнике или данные по сумме в транзакциях различаются
     *         будет возвращена сама транзакция с комментарием о проблеме. Иначе - null
     */
    private Transaction getInvalidTransaction(Transaction transaction, String currentThreadName) {
        Thread.currentThread().setName(currentThreadName + "|transaction-" + transaction.getId());
        if (transaction == null) {
            LOG.warn("Не удалось получить транзакцию из очереди. Превышено время ожидания");
            return new Transaction();
        }
        LOG.debug("Валидация транзакции {}", transaction);
        try {
            Transaction sourceTransaction = transactionFacade.getTransaction(transaction.getId());
            if (sourceTransaction == null) {
                transaction.setComment("Транзакция не найдена");
                return transaction;
            } else if (!transaction.getAmount().equals(sourceTransaction.getAmount())) {
                transaction.setComment("Разница по сумме транзакции (текущая транзакция " + transaction.getAmount() +
                        "; транзакция в источнике " + sourceTransaction.getAmount() +") ");
                return transaction;
            } else {
                return new Transaction();
            }
        } catch (SQLException e) {
            LOG.error("Во время валидации транзакции произошла ошибка при взаимодействии с БД: {}", e);
            return new Transaction();
        } catch (Exception e) {
            LOG.error("Во время валидации транзакции произошла ошибка: {}", e);
            return new Transaction();
        }
    }

    @Override
    public String getConsumerName() {
        return "InvalidTransactionsConsumer";
    }
}
