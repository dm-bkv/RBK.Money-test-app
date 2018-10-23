package com.rbkmoney.test_dev_java.producers;

import com.rbkmoney.test_dev_java.AppProperties;
import com.rbkmoney.test_dev_java.common.Transaction;
import com.rbkmoney.test_dev_java.common.TransportQueue;

/** Фабрика поставщиков */
public class ProducersFactory {

    /** Очередь транзакций */
    private final TransportQueue<Transaction> transportQueue;
    /** Настройки приложения */
    private final AppProperties props;

    public ProducersFactory(TransportQueue<Transaction> transportQueue, AppProperties props) {
        this.transportQueue = transportQueue;
        this.props = props;
    }

    public Producer getProducer() throws Exception {
        switch (props.getProducerSourceDataType()) {
            case CSV:
                return new TransactionsFromCsvProducer(transportQueue, props);
            case JSON:
                return new TransactionsFromJsonProducer();
            default:
                throw new Exception("Формат входных данных " + props.getProducerSourceDataType().name() + " не поддерживается");
        }
    }
}
