package com.rbkmoney.test_dev_java.producers;

import com.rbkmoney.test_dev_java.AppProperties;
import com.rbkmoney.test_dev_java.common.Transaction;
import com.rbkmoney.test_dev_java.common.TransportQueue;

/** Фабрика поставщиков */
public class ProducersFactory {

    private TransportQueue<Transaction> transportQueue;

    private AppProperties props;

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
