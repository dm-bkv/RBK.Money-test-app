package com.rbkmoney.test_dev_java.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/** Класс, необходимый для реализации механизма producer-consumer */
public class TransportQueue<T> {

    /** Наименование очереди */
    private String transportQueueName;
    /** Очередь для заполнения */
    private final LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();
    /** Признак заполнения очереди в данный момент */
    private AtomicBoolean isFilling = new AtomicBoolean(false);

    public TransportQueue(String transportQueueName) {
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd-hh:mm:ss");
        this.transportQueueName = transportQueueName + "-" + formatForDateNow.format(date);
    }

    public LinkedBlockingQueue<T> getQueue() {
        return queue;
    }

    public boolean isFilling() {
        return isFilling.get();
    }

    public void startFilling() {
        isFilling.getAndSet(true);
    }

    public void stopFilling() {
        isFilling.getAndSet(false);
    }

    public String getName() {
        return transportQueueName;
    }

    public void setName(String transportQueueName) {
        this.transportQueueName = transportQueueName;
    }
}
