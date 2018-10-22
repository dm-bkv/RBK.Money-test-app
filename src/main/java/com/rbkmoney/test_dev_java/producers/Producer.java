package com.rbkmoney.test_dev_java.producers;

/** Интерфейс поставщика данных */
public interface Producer {

    /** Метод выполняет поставку данных для дальнейшей обработки */
    void produce();

    /** Наименование поставщика данных */
    String getProducerName();
}
