package com.rbkmoney.test_dev_java.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidTransactionsConsumers implements Consumer {

    /** Логгер */
    private static final Logger LOG = LoggerFactory.getLogger(ValidTransactionsConsumers.class);

    @Override
    public void consume() {
        LOG.error("Потребитель валидных транзакций не реализован");
    }

    @Override
    public String getConsumerName() {
        return "ValidTransactionsConsumer";
    }
}
