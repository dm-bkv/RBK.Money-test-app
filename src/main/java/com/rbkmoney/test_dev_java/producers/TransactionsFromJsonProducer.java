package com.rbkmoney.test_dev_java.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionsFromJsonProducer implements Producer {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsFromJsonProducer.class);

    @Override
    public void produce() {
        LOG.error("Получение списка транзакций из JSON сообщения не реализовано");
    }

    @Override
    public String getProducerName() {
        return "TransactionsFromJsonProducer";
    }
}
