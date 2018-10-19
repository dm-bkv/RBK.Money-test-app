package com.rbkmoney.test_dev_java.get_data;

import java.util.concurrent.ConcurrentLinkedQueue;

/** Класс, необходимый для реализации механизма конвеера */
public class TransportQueue {

    /** Наименование очереди */
    private final String transportQueueName;
    /** Очередь для заполнения */
    private final ConcurrentLinkedQueue<Transaction> queue = new ConcurrentLinkedQueue<>();
    /** Признак заполнения очереди в данный момент */
    private boolean isFilling = false;

    public TransportQueue(String transportQueueName) {
        this.transportQueueName = transportQueueName;
    }

    public ConcurrentLinkedQueue<Transaction> getQueue() {
        return queue;
    }

    public boolean isFilling() {
        return isFilling;
    }

    public void startFilling() {
        isFilling = true;
    }

    public void stopFilling() {
        isFilling = false;
    }

    public String getName() {
        return transportQueueName;
    }
}
