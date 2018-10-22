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

    private TransactionsFacade transactionFacade;
    private TransportQueue<Transaction> transportQueue;
    private List<Transaction> invalidTransactions;
    private Command command;
    private ExecutorService service;
    private AppProperties appProperties;

    public ConsumersFactory(TransactionsFacade transactionFacade,
                            TransportQueue transportQueue,
                            List<Transaction> invalidTransactions,
                            CommandsFactory commandsFactory,
                            ExecutorService service,
                            AppProperties appProperties) throws Exception {
        this.transactionFacade = transactionFacade;
        this.transportQueue = transportQueue;
        this.invalidTransactions = invalidTransactions;
        this.command = commandsFactory.getCommand();
        this.service = service;
        this.appProperties = appProperties;
    }

    public Consumer getConsumer() throws Exception {
        switch (appProperties.getConsumerActionType()) {
            case GET_INVALID_TRANSACTIONS:
                return new InvalidTransactionsConsumer(transactionFacade, transportQueue, invalidTransactions, command, service);

            case GET_VALID_TRANSACTIONS:
                return new ValidTransactionsConsumers();

            default:
                throw new Exception("Для команды " + appProperties.getConsumerActionType().name() + " не реализован потребитель");

        }
    }


}
