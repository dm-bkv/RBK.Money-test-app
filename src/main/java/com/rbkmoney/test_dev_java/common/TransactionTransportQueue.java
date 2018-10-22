package com.rbkmoney.test_dev_java.common;

/** Класс, реализующий очередь для транзакций */
public class TransactionTransportQueue extends TransportQueue<Transaction> {

    public TransactionTransportQueue(String transportQueueName) {
        super(transportQueueName);
    }

}
