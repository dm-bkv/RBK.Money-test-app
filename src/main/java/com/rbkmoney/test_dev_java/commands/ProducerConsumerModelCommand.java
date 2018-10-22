package com.rbkmoney.test_dev_java.commands;

import com.rbkmoney.test_dev_java.consumers.Consumer;
import com.rbkmoney.test_dev_java.consumers.ConsumersFactory;
import com.rbkmoney.test_dev_java.producers.Producer;
import com.rbkmoney.test_dev_java.producers.ProducersFactory;

import java.util.concurrent.*;

/** Класс, который реализует паттерн поставщик-потребитель */
public class ProducerConsumerModelCommand implements Command {

    /** Поставщик данных */
    private final Producer producer;
    /** Потребитель данных */
    private final Consumer consumer;
    /** Пул потоков */
    private final ExecutorService service;

    public ProducerConsumerModelCommand(ProducersFactory producersFactory,
                                        ConsumersFactory consumersFactory,
                                        ExecutorService service) throws Exception {
        this.producer = producersFactory.getProducer();
        this.consumer = consumersFactory.getConsumer();
        this.service = service;
    }

    @Override
    public void execute() throws Exception {

        Future<?> futureProducer = service.submit(() -> {
            Thread.currentThread().setName(producer.getProducerName() + "-" + Thread.currentThread().getId());
            producer.produce();
        });

        Future<?> futureConsumer = service.submit(() -> {
            Thread.currentThread().setName(consumer.getConsumerName() + "-" + Thread.currentThread().getId());
            consumer.consume();
        });

        futureProducer.get();
        futureConsumer.get();

    }
}
