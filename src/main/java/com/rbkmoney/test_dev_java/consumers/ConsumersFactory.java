package com.rbkmoney.test_dev_java.consumers;

import com.rbkmoney.test_dev_java.AppProperties;
import com.rbkmoney.test_dev_java.commands.Command;
import com.rbkmoney.test_dev_java.commands.CommandsFactory;
import com.rbkmoney.test_dev_java.common.Transaction;
import com.rbkmoney.test_dev_java.common.TransportQueue;
import com.rbkmoney.test_dev_java.consumers.helpers.TransactionsFacade;

import java.util.List;
import java.util.concurrent.ExecutorService;

/** Фабрика потребителей */
public class ConsumersFactory {

    /** Фасад для работы с транзакциями из хранилища */
    private final TransactionsFacade transactionFacade;
    /** Очередь транзакций на обработку */
    private final TransportQueue<Transaction> transportQueue;
    /** Результирующий список транзакций */
    private final List<Transaction> transactionList;
    /** Команда для обработки результирующих данных */
    private final Command command;
    /** Пул потоков */
    private final ExecutorService service;
    /** Настройки приложения */
    private final AppProperties appProperties;

    public ConsumersFactory(TransactionsFacade transactionFacade,
                            TransportQueue transportQueue,
                            List<Transaction> transactionList,
                            CommandsFactory commandsFactory,
                            ExecutorService service,
                            AppProperties appProperties) throws Exception {
        this.transactionFacade = transactionFacade;
        this.transportQueue = transportQueue;
        this.transactionList = transactionList;
        this.command = commandsFactory.getCommand();
        this.service = service;
        this.appProperties = appProperties;
    }

    public Consumer getConsumer() throws Exception {
        switch (appProperties.getConsumerActionType()) {
            case GET_INVALID_TRANSACTIONS:
                return new InvalidTransactionsConsumer(transactionFacade, transportQueue, transactionList, command, service);

            case GET_VALID_TRANSACTIONS:
                return new ValidTransactionsConsumers();

            default:
                throw new Exception("Для команды " + appProperties.getConsumerActionType().name() + " не реализован потребитель");

        }
    }


}
